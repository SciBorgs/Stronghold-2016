package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.ImageBase;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Image extends Subsystem {
	private USBCamera camera;
	private ImageBase targetImage;
	private HSLImage hslTarget;
	private BinaryImage thresholdImage;
	private ColorImage maskedGreenImage;
	private int index = 0;
	private static final double TARGET_W_M = 0,
								TARGET_H_M = 0, 
								FOV_VERT_ANGLE = 0,
								FOV_HORZ_ANGLE = 0,
								FOV_H_PIXEL = 1280,
								FOV_W_PIXEL = 720,
								FOV_H_M = 1.5,
								FOV_W_M = 0;
	private boolean isImageStored;
	
	public Image() {
		camera = Hardware.INSTANCE.camera;
		camera.startCapture();
	}
	
	public void takePicture() {
		camera.getImage(targetImage.image);	
	}
	
	//TEST THESE PARAMETER 
	public boolean prepareImage(int hue_low, 
								 int hue_high, 
								 int saturation_low, 
								 int saturation_high, 
								 int luminance_low, 
								 int luminance_high) throws NIVisionException {
		//Your tested threshold values (and intensity) 
		if (isImageStored) {
			hslTarget =  (HSLImage) targetImage;
			thresholdImage = hslTarget.thresholdHSL(hue_low,hue_high,saturation_low,saturation_high,
														luminance_low, luminance_high);
			//check particles
			//WHAT IS THIS BULLSHIT ParticleAnalysisReport threshold_image_report = new ParticleAnalysisReport(threshold_image, 0);
			//stolen property
			if(NIVision.imaqMeasureParticle(thresholdImage.image, index, 0, NIVision.MeasurementType.MT_AREA) > 0) {	
				return true;
			} else {
				//Do something else
				//Use your imagination
				//Try to get an image???
				return false;
			}
		} else {
			return false;
		}
	}
	
	/*
	public boolean checkPictureColor(int redLow, int redHigh, int greenLow, int greenHigh, int blueLow, int blueHigh) throws NIVisionException{
		hsl_target =  (HSLImage) target_image;
		BinaryImage test = hsl_target.thresholdRGB(redLow, redHigh, greenLow, greenHigh, blueLow, blueHigh);
		if (is_image_stored) {	
				return NIVision.imaqMeasureParticle(test.image, index, 0, NIVision.MeasurementType.MT_AREA) > 0;
		} else {
			return false;
		}
	}
	*/
	
	//return (x_distance, x_angle) & (y_distance, y_angle)
	public TargetVector getTargetVector() throws NIVisionException{
		
		//Assumes that only one object is present in image
		double targetWidthPixels = (int) NIVision.imaqMeasureParticle(thresholdImage.image, index, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
		double targetHeightPixels = (int) NIVision.imaqMeasureParticle(thresholdImage.image, index, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
		double targetPositionX =  (int) NIVision.imaqMeasureParticle(thresholdImage.image, index, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
		double targetPositionY = (int) NIVision.imaqMeasureParticle(thresholdImage.image, index, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
		
		//OR 
		//Use your imagination and allow more objects :P
		//double aiming_x = (target_position_x - IMAGE_RESOLUTION_X/2)/(IMAGE_RESOLUTION_X/2);
		//double aiming_y = (target_position_y - IMAGE_RESOLUTION_Y/2)/(IMAGE_RESOLUTION_Y/2);
		//Get theta (on x-axis) and phi (on y-axis)
		double theta =  (targetImage.getWidth() - targetPositionX)/(targetImage.getWidth())*FOV_HORZ_ANGLE;
		double phi =  (targetImage.getHeight() - targetPositionY)/(targetImage.getHeight())*FOV_VERT_ANGLE;
		
		//formula for distance
		double distanceX = TARGET_W_M*FOV_W_PIXEL/(2*targetWidthPixels*Math.tan(theta)); 
		double distanceY = TARGET_H_M*FOV_H_PIXEL/(2*targetHeightPixels*Math.tan(phi));
		
		//IGNORE dist_y and phi for limbot
		TargetVector targetVector = new TargetVector();
		targetVector.xDistance = distanceX;
		targetVector.theta = theta;
		targetVector.yDistance = distanceY;
		targetVector.phi = phi;
		return targetVector;
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	public final class TargetVector {
		public double xDistance;
		public double theta;
		public double yDistance;
		public double phi;
	}
	
}



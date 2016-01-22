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
	private ImageBase target_image;
	private HSLImage hsl_target;
	private BinaryImage threshold_image;
	private ColorImage masked_green_image;
	private int index = 0;
	private static final double TARGET_W_M = 0,
								TARGET_H_M = 0, 
								FOV_VERT_ANGLE = 0,
								FOV_HORZ_ANGLE = 0,
								FOV_H_PIXEL = 1280,
								FOV_W_PIXEL = 720,
								FOV_H_M = 1.5,
								FOV_W_M = 0;
	private boolean is_image_stored;
	
	public Image() {
		camera = Hardware.INSTANCE.camera;
		camera.startCapture();
	}
	
	public void takePicture() {
		camera.getImage(target_image.image);	
	}
	
	//TEST THESE PARAMETER 
	public boolean prepareImage(int hue_low, 
								 int hue_high, 
								 int saturation_low, 
								 int saturation_high, 
								 int luminance_low, 
								 int luminance_high) throws NIVisionException {
		//Your tested threshold values (and intensity) 
		if (is_image_stored) {
			hsl_target =  (HSLImage) target_image;
			threshold_image = hsl_target.thresholdHSL(hue_low,hue_high,saturation_low,saturation_high,
														luminance_low, luminance_high);
			//check particles
			//WHAT IS THIS BULLSHIT ParticleAnalysisReport threshold_image_report = new ParticleAnalysisReport(threshold_image, 0);
			//stolen property
			if(NIVision.imaqMeasureParticle(threshold_image.image, index, 0, NIVision.MeasurementType.MT_AREA) > 0) {	
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
		double target_width_pixels = (int) NIVision.imaqMeasureParticle(threshold_image.image, index, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
		double target_height_pixels = (int) NIVision.imaqMeasureParticle(threshold_image.image, index, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
		double target_position_x =  (int) NIVision.imaqMeasureParticle(threshold_image.image, index, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
		double target_position_y = (int) NIVision.imaqMeasureParticle(threshold_image.image, index, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
		
		//OR 
		//Use your imagination and allow more objects :P
		//double aiming_x = (target_position_x - IMAGE_RESOLUTION_X/2)/(IMAGE_RESOLUTION_X/2);
		//double aiming_y = (target_position_y - IMAGE_RESOLUTION_Y/2)/(IMAGE_RESOLUTION_Y/2);
		//Get theta (on x-axis) and phi (on y-axis)
		double theta =  (target_image.getWidth() - target_position_x)/(target_image.getWidth())*FOV_HORZ_ANGLE;
		double phi =  (target_image.getHeight() - target_position_y)/(target_image.getHeight())*FOV_VERT_ANGLE;
		
		//formula for distance
		double distance_x = TARGET_W_M*FOV_W_PIXEL/(2*target_width_pixels*Math.tan(theta)); 
		double distance_y = TARGET_H_M*FOV_H_PIXEL/(2*target_height_pixels*Math.tan(phi));
		
		//IGNORE dist_y and phi for limbot
		TargetVector target_vector = new TargetVector();
		target_vector.xDistance = distance_x;
		target_vector.theta = theta;
		target_vector.yDistance = distance_y;
		target_vector.phi = phi;
		return target_vector;
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



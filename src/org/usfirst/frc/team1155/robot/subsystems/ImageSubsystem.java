package org.usfirst.frc.team1155.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;

public class ImageSubsystem extends Subsystem {
	private Image targetImage, targetFrame;
	private BinaryImage thresholdImage;
	private int index = 0;
	private int session;
	private static final double TARGET_W_M = 0, TARGET_H_M = 0, FOV_VERT_ANGLE = 0, FOV_HORZ_ANGLE = 0,
			FOV_H_PIXEL = 1280, FOV_W_PIXEL = 720, FOV_H_M = 1.5, FOV_W_M = 0;
	private static final Range TAPE_HUE_RANGE = new Range(100, 175);
	private static final Range TAPE_SAT_RANGE = new Range(100, 200);
	private static final Range TAPE_LUM_RANGE = new Range(100, 255);
	
	public ImageSubsystem() {
		targetImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		targetFrame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);
		// serverCamera.startAutomaticCapture();
	}

	public void recordVideo() {
		NIVision.IMAQdxGrab(session, targetFrame, 1);
		CameraServer.getInstance().setImage(targetFrame);
	}

	public void takePicture() {
		NIVision.imaqColorThreshold(targetImage, targetFrame, 255, NIVision.ColorMode.HSL, TAPE_HUE_RANGE, TAPE_SAT_RANGE, TAPE_LUM_RANGE);
	}

	public boolean doesTargetExist() {
		if (NIVision.imaqMeasureParticle(targetImage, 255, 0, NIVision.MeasurementType.MT_AREA) > 0) {
				return true;
		} else {
			return false;
		}
	}

	/*
	 * public boolean checkPictureColor(int redLow, int redHigh, int greenLow,
	 * int greenHigh, int blueLow, int blueHigh) throws NIVisionException{
	 * hsl_target = (HSLImage) target_image; BinaryImage test =
	 * hsl_target.thresholdRGB(redLow, redHigh, greenLow, greenHigh, blueLow,
	 * blueHigh); if (is_image_stored) { return
	 * NIVision.imaqMeasureParticle(test.image, index, 0,
	 * NIVision.MeasurementType.MT_AREA) > 0; } else { return false; } }
	 */

	// return (x_distance, x_angle) & (y_distance, y_angle)
	public TargetVector getTargetVector() {

		// Assumes that only one object is present in image
		double targetWidthPixels =  NIVision.imaqMeasureParticle(targetImage, 255, 0,
				NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
		double targetHeightPixels =  NIVision.imaqMeasureParticle(targetImage, 255, 0,
				NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
		double targetPositionX =  NIVision.imaqMeasureParticle(targetImage, 255, 0,
				NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
		double targetPositionY =  NIVision.imaqMeasureParticle(targetImage, 255, 0,
				NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);

		// OR
		// Use your imagination and allow more objects :P
		// double aiming_x = (target_position_x -
		// IMAGE_RESOLUTION_X/2)/(IMAGE_RESOLUTION_X/2);
		// double aiming_y = (target_position_y -
		// IMAGE_RESOLUTION_Y/2)/(IMAGE_RESOLUTION_Y/2);
		// Get theta (on x-axis) and phi (on y-axis)

		double theta = (NIVision.imaqMeasureParticle(targetImage, 255, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH) - targetPositionX) / (NIVision.imaqMeasureParticle(targetImage, 255, index, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH)) * FOV_HORZ_ANGLE;
		double phi = (NIVision.imaqMeasureParticle(targetImage, 255, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT) - targetPositionY) / (NIVision.imaqMeasureParticle(targetImage, 255, index, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT)) * FOV_VERT_ANGLE;

		// formula for distance
		double distanceX = TARGET_W_M * FOV_W_PIXEL / (2 * targetWidthPixels * Math.tan(theta));
		double distanceY = TARGET_H_M * FOV_H_PIXEL / (2 * targetHeightPixels * Math.tan(phi));

		// IGNORE dist_y and phi for limbot
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

package org.usfirst.frc.team1155.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class ImageSubsystem extends Subsystem {
	private Image targetImage, targetFrame;
	private HSLImage hslTarget;
	private BinaryImage thresholdImage;
	private int index = 0;
	private int session;
	private static final double TARGET_W_M = 0, TARGET_H_M = 0, FOV_VERT_ANGLE = 0, FOV_HORZ_ANGLE = 0,
			FOV_H_PIXEL = 1280, FOV_W_PIXEL = 720, FOV_H_M = 1.5, FOV_W_M = 0;
	private static final Range TAPE_HUE_RANGE = new Range(79, 89);
	private static final Range TAPE_SAT_RANGE = new Range(64, 153);
	private static final Range TAPE_LUM_RANGE = new Range(40, 253);
	private boolean isImageStored;

	public ImageSubsystem() {
		try {
			hslTarget = new HSLImage();
		} catch (NIVisionException e) {
			e.printStackTrace();
		}

		targetImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		targetFrame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);
		// serverCamera.startAutomaticCapture();
	}

	public void recordVideo() {
		NIVision.IMAQdxGrab(session, targetFrame, 1);
		// NIVision.imaqColorThreshold(targetImage, targetFrame, 255,
		// NIVision.ColorMode.HSV, TAPE_HUE_RANGE, TAPE_SAT_RANGE,
		// TAPE_LUM_RANGE);
		CameraServer.getInstance().setImage(targetFrame);
	}

	public void takePicture() {
		NIVision.imaqColorThreshold(targetImage, targetFrame, 255, NIVision.ColorMode.HSV, TAPE_HUE_RANGE,
				TAPE_SAT_RANGE, TAPE_LUM_RANGE);
	}

	// TEST THESE PARAMETER
	public boolean prepareImage(int hue_low, int hue_high, int saturation_low, int saturation_high, int luminance_low,
			int luminance_high) throws NIVisionException {
		// Your tested threshold values (and intensity)
		if (isImageStored) {
			thresholdImage = hslTarget.thresholdHSL(hue_low, hue_high, saturation_low, saturation_high, luminance_low,
					luminance_high);
			// check particles
			// WHAT IS THIS BULLSHIT ParticleAnalysisReport
			// threshold_image_report = new
			// ParticleAnalysisReport(threshold_image, 0);
			// stolen property
			if (NIVision.imaqMeasureParticle(thresholdImage.image, index, 0, NIVision.MeasurementType.MT_AREA) > 0) {
				return true;
			} else {
				// Do something else
				// Use your imagination
				// Try to get an image???
				return false;
			}
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
	public TargetVector getTargetVector() throws NIVisionException {

		// Assumes that only one object is present in image
		double targetWidthPixels = (int) NIVision.imaqMeasureParticle(hslTarget.image, index, 0,
				NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
		double targetHeightPixels = (int) NIVision.imaqMeasureParticle(hslTarget.image, index, 0,
				NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
		double targetPositionX = (int) NIVision.imaqMeasureParticle(hslTarget.image, index, 0,
				NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
		double targetPositionY = (int) NIVision.imaqMeasureParticle(hslTarget.image, index, 0,
				NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);

		// OR
		// Use your imagination and allow more objects :P
		// double aiming_x = (target_position_x -
		// IMAGE_RESOLUTION_X/2)/(IMAGE_RESOLUTION_X/2);
		// double aiming_y = (target_position_y -
		// IMAGE_RESOLUTION_Y/2)/(IMAGE_RESOLUTION_Y/2);
		// Get theta (on x-axis) and phi (on y-axis)
		double theta = (hslTarget.getWidth() - targetPositionX) / (hslTarget.getWidth()) * FOV_HORZ_ANGLE;
		double phi = (hslTarget.getHeight() - targetPositionY) / (hslTarget.getHeight()) * FOV_VERT_ANGLE;

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

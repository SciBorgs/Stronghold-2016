package org.usfirst.frc.team1155.robot.subsystems;

import java.util.Comparator;
import java.util.Vector; 

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;

public class ImageSubsystem extends Subsystem {
	private Image targetImage, targetFrame;
	private int session;
	private int numParticles;
	private Vector<Report> particles;
	private static final double TARGET_W_M = .40, TARGET_H_M = .13, FOV_VERT_ANGLE = 0, FOV_HORZ_ANGLE = 0,
			FOV_H_PIXEL = 1280, FOV_W_PIXEL = 720;
	private static final Range TAPE_HUE_RANGE = new Range(100, 175);
	private static final Range TAPE_SAT_RANGE = new Range(100, 200);
	private static final Range TAPE_LUM_RANGE = new Range(100, 255);
	
	public ImageSubsystem() {
		targetImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		targetFrame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);
		particles = new Vector<Report>();
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
		numParticles = NIVision.imaqCountParticles(targetImage, 1);

		if (numParticles > 0) {
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
	public void analyzeImage() {
		for (int p = 0; p < numParticles; p++) {
			Report report = new Report();
			report.area = NIVision.imaqMeasureParticle(targetImage, p, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
			report.width = NIVision.imaqMeasureParticle(targetImage, p, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
			report.height = NIVision.imaqMeasureParticle(targetImage, p, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
			report.targetX = NIVision.imaqMeasureParticle(targetImage, p, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			report.targetY = NIVision.imaqMeasureParticle(targetImage, p, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			particles.add(report);
		}
		particles.sort(null);
	}
	
	public TargetVector getTargetVector() {
		// Assumes that only one object is present in image
		double targetWidthPixels = particles.get(0).width;
		System.out.println(targetWidthPixels);
		double targetHeightPixels =  particles.get(0).height;
		double targetPositionX = particles.get(0).targetX;
		double targetPositionY =  particles.get(0).targetY;

		// OR
		// Use your imagination and allow more objects :P
		// double aiming_x = (target_position_x -
		// IMAGE_RESOLUTION_X/2)/(IMAGE_RESOLUTION_X/2);
		// double aiming_y = (target_position_y -
		// IMAGE_RESOLUTION_Y/2)/(IMAGE_RESOLUTION_Y/2);
		// Get theta (on x-axis) and phi (on y-axis)

		double theta = (targetWidthPixels - targetPositionX) / (targetWidthPixels) * FOV_HORZ_ANGLE;
		double phi = (targetHeightPixels - targetPositionY) / (targetHeightPixels) * FOV_VERT_ANGLE;

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
	
	public class Report implements Comparator<Report>, Comparable<Report> {
		double area;
		double width;
		double height;
		double targetX;
		double targetY;
		
		@Override
		public int compareTo(Report o) {
			return (int)(o.area - this.area);
		}

		@Override
		public int compare(Report o1, Report o2) {
			return (int)(o1.area - o2.area);
		}
		
	}

}

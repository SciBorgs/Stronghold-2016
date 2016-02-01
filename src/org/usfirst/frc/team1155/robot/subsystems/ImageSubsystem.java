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
	private static final double TARGET_W_METER = .40, 
								TARGET_H_METER = .13, 
								FOV_VERT_ANGLE_RADIAN = 1.196, // 86.5 degrees
								FOV_HORZ_ANGLE_RADIAN = 1.196, // 86.5 degrees
								FOV_W_PIXEL = 1280, 
								FOV_H_PIXEL = 720;
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
		//CameraServer.getInstance().setImage(targetFrame);
	}

	public void takePicture() {
		NIVision.imaqColorThreshold(targetImage, targetFrame, 255, NIVision.ColorMode.HSL, TAPE_HUE_RANGE, TAPE_SAT_RANGE, TAPE_LUM_RANGE);
		NIVision.imaqFlatten(targetImage, NIVision.FlattenType.FLATTEN_IMAGE, NIVision.CompressionType.COMPRESSION_NONE, 100);
		CameraServer.getInstance().setImage(targetImage);
	}

	public boolean doesTargetExist() {
		numParticles = NIVision.imaqCountParticles(targetImage, 1);

		if (numParticles > 0) {
				return true;
		} else {
			return false;
		}
	}

	// return (x_distance, x_angle) & (y_distance, y_angle)
	public void analyzeImage() {
		particles.clear();
		for (int pixel = 0; pixel < numParticles; pixel++) {
			Report report = new Report();
			report.area = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
			report.width = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
			report.height = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
			report.targetX = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			report.targetY = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
			particles.add(report);
		}
		particles.sort(null);
	}
	
	public TargetVector getTargetVector() {
		// Assumes that only one object is present in image
		double targetWidthPixels = -(particles.get(0).width - FOV_W_PIXEL/2)/(FOV_W_PIXEL/2);
		double targetHeightPixels =  -(particles.get(0).height - FOV_H_PIXEL/2)/(FOV_H_PIXEL/2);
		double targetPositionX = -(particles.get(0).targetX - FOV_W_PIXEL/2)/(FOV_W_PIXEL/2);
		double targetPositionY =  -(particles.get(0).targetY - FOV_H_PIXEL/2)/(FOV_H_PIXEL/2);
		System.out.println("X: " + targetPositionX + " Width: " + targetWidthPixels);

		double theta = (targetWidthPixels - targetPositionX) / (targetWidthPixels) * FOV_HORZ_ANGLE_RADIAN;
		double phi = (targetHeightPixels - targetPositionY) / (targetHeightPixels) * FOV_VERT_ANGLE_RADIAN;
		
		// formula for distance
		double distanceX = TARGET_W_METER * FOV_W_PIXEL / (2 * targetWidthPixels * Math.tan(FOV_HORZ_ANGLE_RADIAN));
		double distanceY = TARGET_H_METER * FOV_H_PIXEL / (2 * targetHeightPixels * Math.tan(FOV_VERT_ANGLE_RADIAN));

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

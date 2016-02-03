package org.usfirst.frc.team1155.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ImageSubsystem extends Subsystem {
	private Image targetImage, targetFrame;
	
	private int session;
	private int numParticles;
	private int imaqError;
	
	private boolean isTape;
	
	private Vector<Report> particles;
	
	private Scores scores;
	
	private static NIVision.ParticleFilterCriteria2 criteria[];
	private static NIVision.ParticleFilterOptions2 filterOptions;
	
	private static final double TARGET_W_METER = .40, 
								TARGET_H_METER = .13, 
								FOV_VERT_ANGLE = 60, // 60 degrees
								FOV_HORZ_ANGLE = 60, // 60 degrees
								FOV_W_PIXEL = 640, 
								FOV_H_PIXEL = 480,
								AREA_MIN = .031,
								SCORE_MIN = 75;
	
	private static final double TAPE_WIDTH = 0.31, // Length
								TAPE_LENGTH = 0.1, // Meters
								TAPE_AREA = 0.031;
	
	private static final Range TAPE_HUE_RANGE = new Range(101, 100);
	private static final Range TAPE_SAT_RANGE = new Range(100, 230);
	private static final Range TAPE_VAL_RANGE = new Range(134, 255);
	
	public ImageSubsystem() {
		targetImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		targetFrame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		
		//criteria = new NIVision.ParticleFilterCriteria2[1];
		//criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MIN, 100, 0, 0);
		//criteria[0].lower = (float) AREA_MIN;
		
		//filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
		
		session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);
		
		particles = new Vector<Report>();
		
		scores = new Scores();
	}

	public void recordVideo() {
		NIVision.IMAQdxGrab(session, targetFrame, 1);
		//CameraServer.getInstance().setImage(targetFrame);
	}

	public void takePicture() {
		NIVision.imaqColorThreshold(targetImage, targetFrame, 255, NIVision.ColorMode.HSV, TAPE_HUE_RANGE, TAPE_SAT_RANGE, TAPE_VAL_RANGE);
		//NIVision.imaqFlatten(targetImage, NIVision.FlattenType.FLATTEN_IMAGE, NIVision.CompressionType.COMPRESSION_NONE, 100);
		//imaqError = NIVision.imaqParticleFilter4(targetImage, targetImage, criteria, filterOptions, null);		
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
			report.area = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_AREA);
			report.boundingRectBottom = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			report.boundingRectTop = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			report.boundingRectRight = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			report.boundingRectLeft = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			report.percentAreaToImageArea = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
			report.areaToConvexHullArea = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
			report.targetX = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			report.targetY = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
			particles.add(report);
		}
		particles.sort(null);
		scores.aspect = aspectScore(particles.elementAt(0));
		System.out.println("Score of aspect: " + scores.aspect);
		scores.area = areaScore(particles.elementAt(0));
		System.out.println("Score of area: " + scores.area);
		scores.convexHullArea = convexHullAreaScore(particles.elementAt(0));
		System.out.println("Score of Convex Hull Area: " + scores.convexHullArea);
		
//		System.out.println("Length of Rect Bottom: " + particles.get(0).boundingRectBottom);
//		System.out.println("Length of Rect Top: " + particles.get(0).boundingRectTop);
//		System.out.println("Length of Rect Right: " + particles.get(0).boundingRectRight);
//		System.out.println("Length of Rect Left: " + particles.get(0).boundingRectLeft);
//		System.out.println("Area of Area: " + particles.get(0).area);
		
		isTape = (scores.aspect > SCORE_MIN) && (scores.area > SCORE_MIN) && (scores.convexHullArea > SCORE_MIN);
	}
	

	static boolean CompareParticleSizes(Report r1, Report r2)
	{
		return r1.percentAreaToImageArea > r2.percentAreaToImageArea;
	}
	
	public boolean isTargetTape() {
		System.out.println(isTape);
		return isTape;
	}
	
	//Scoring methods below
	public double aspectScore(Report r) {
		return ratioToScore((3.1) * ((r.boundingRectBottom-r.boundingRectTop) / (r.boundingRectRight-r.boundingRectLeft)));
	}
	
	public double areaScore(Report r) {
		double boundingArea = (r.boundingRectBottom - r.boundingRectTop) * (r.boundingRectRight - r.boundingRectLeft); // Area 
		return ratioToScore(r.area/boundingArea);
	}
	
	private double convexHullAreaScore(Report r) {
		return ratioToScore((r.area/r.areaToConvexHullArea)*1.18);
	}
	
	public double ratioToScore(double r) {
		return (Math.max(0, Math.min(100*(1-Math.abs(1-r)), 100)));
	}
	
	public TargetVector getTargetVector() {
		// Assumes that only one object is present in image
		//double targetWidthPixels = -(particles.get(0).width - FOV_W_PIXEL/2)/(FOV_W_PIXEL/2);
		//double targetHeightPixels =  -(particles.get(0).height - FOV_H_PIXEL/2)/(FOV_H_PIXEL/2);
		double targetWidthPixels = particles.get(0).boundingRectBottom;
		double targetHeightPixels =  particles.get(0).boundingRectLeft;
		double targetPositionX = -(particles.get(0).targetX - FOV_W_PIXEL/2)/(FOV_W_PIXEL/2);
		double targetPositionY =  -(particles.get(0).targetY - FOV_H_PIXEL/2)/(FOV_H_PIXEL/2);

		double theta = (targetWidthPixels - targetPositionX) / (targetWidthPixels) * FOV_HORZ_ANGLE;
		double phi = (targetHeightPixels - targetPositionY) / (targetHeightPixels) * FOV_VERT_ANGLE;
		
		// formula for distance
		//double distanceX = TARGET_W_METER * FOV_W_PIXEL / (2 * targetWidthPixels * Math.tan(FOV_HORZ_ANGLE));
		//double distanceY = TARGET_H_METER * FOV_H_PIXEL / (2 * targetHeightPixels * Math.tan(FOV_VERT_ANGLE));

		double normalizedWidth, targetWidth, normalizedHeight, targetHeight;
		NIVision.GetImageSizeResult size;
		
		size = NIVision.imaqGetImageSize(targetImage);
		
		normalizedWidth = 2*(particles.get(0).boundingRectRight - particles.get(0).boundingRectLeft)/size.width;
		targetWidth = TARGET_W_METER;
		
		normalizedHeight = 2*(particles.get(0).boundingRectTop - particles.get(0).boundingRectBottom)/size.height;
		targetHeight = TARGET_H_METER;
		
		//targetWidth = meters;
		//normalizedWidth = no units
		//FOV_HORZ_ANGLE = radians
		//distanceX = 
		double distanceX = targetWidth/(normalizedWidth*12*Math.tan(FOV_HORZ_ANGLE*Math.PI/(180*2)));
		double distanceY = targetHeight/(normalizedHeight *12*Math.tan(FOV_VERT_ANGLE*Math.PI/(180*2)));
		
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
	
	private class Report implements Comparator<Report>, Comparable<Report> {
		double area;
		double percentAreaToImageArea;
		double boundingRectTop;
		double boundingRectBottom;
		double boundingRectLeft;
		double boundingRectRight;
		double areaToConvexHullArea;
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
	
	private class Scores {
		double convexHullArea;
		double area;
		double aspect;
	}

}

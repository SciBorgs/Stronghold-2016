package org.usfirst.frc.team1155.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ImageSubsystem extends Subsystem {
	private Image targetImage, targetFrame;
	
	private SmartDashboard dashboard;
	
	private int session; // Camera session (Arbitrary required variable)
	private int numParticles; // Total number of unfiltered particles
	private int imaqError; // Arbitrary required variable
	
	private boolean isTape; 
	
	// Report and Scores classes at very bottom
	private Vector<Report> particles; //An object that stores values of a specific unfiltered region. Used for analyzing
	
	private Scores scores; // The "score" of one analyzed region. From 0 - 100, 100 meaning that the region is very like the tape
	
	private static NIVision.ParticleFilterCriteria2 criteria[]; // Criteria for filtering options (Arbitrary required)
	private static NIVision.ParticleFilterOptions2 filterOptions; // Filter options for removing noise
	
	private static final double TARGET_W_METER = .40, // Longest side of tape
								TARGET_H_METER = .1, // Shortest side of tape
								TARGET_AREA = .04, // Area of tape.
								FOV_VERT_ANGLE = 60, // 60 degrees
								FOV_HORZ_ANGLE = 60, // 60 degrees
								FOV_W_PIXEL = 640, // Resolution of camera
								FOV_H_PIXEL = 480, // Resolution of camera
								SCORE_MIN = 75; // Lowest score that signifies what is being observed is the target tape
	
	// Used for trajectory calculation (If we use it)
	private static final double DRAG_CONSTANT = .209092519013,
								MASS_OF_BALL = .295,
								PERIOD = 13.8264152809,
								SHOT_VELOCITY = 7.8,
								SHOT_ANGLE = 50 * Math.PI/180;
								
	// Hue, Saturation and Color Value that corresponds to Green color
	private static final Range TAPE_HUE_RANGE = new Range(65, 45);
	private static final Range TAPE_SAT_RANGE = new Range(88, 120);
	private static final Range TAPE_VAL_RANGE = new Range(232, 255);
	
	public ImageSubsystem() {
		dashboard = Robot.dashboard;
		
		targetImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0); // Filtered recording
		targetFrame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0); // Unfiltered recording
		
		// Filtering things
		criteria = new NIVision.ParticleFilterCriteria2[1];
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, TARGET_AREA, 100, 0, 0);
		criteria[0].lower = (float) TARGET_AREA;
		
		filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
		
		// Starts camera recording
		session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);
		
		// A report of all the particles (pixels) in the image
		particles = new Vector<Report>();
		
		// A place to store all of the scores that are calculated
		scores = new Scores();
	}

	public void recordVideo() {
		NIVision.IMAQdxGrab(session, targetFrame, 1);
	}

	public void takePicture() {
		NIVision.imaqColorThreshold(targetImage, targetFrame, 255, NIVision.ColorMode.HSV, TAPE_HUE_RANGE, TAPE_SAT_RANGE, TAPE_VAL_RANGE);
		//NIVision.imaqFlatten(targetImage, NIVision.FlattenType.FLATTEN_IMAGE, NIVision.CompressionType.COMPRESSION_NONE, 100);
		imaqError = NIVision.imaqParticleFilter4(targetImage, targetImage, criteria, filterOptions, null);		
		//CameraServer.getInstance().setImage(targetImage);
	}
	
	public boolean doesTargetExist() {
		numParticles = NIVision.imaqCountParticles(targetImage, 1);

		if (numParticles > 0) {
				return true;
		} else {
			return false;
		}
	}

	// Max distance to shoot: 3.5m, Min distance to shoot: 2m
	public void analyzeImage() {
		particles.clear();
		
		/* Stores values from target
		 * 
		 * area is area of target
		 * boundingRect values are the sides of the target
		 * percentAreaToImageArea is how much of the image area does the target cover
		 * targetX is X distance from edge of image to center of target
		 * targetY is Y distance from edge of image to center of target
		 */
		for (int pixel = 0; pixel < numParticles; pixel++) {
			Report report = new Report();
			report.area = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_AREA);
			report.boundingRectBottom = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			report.boundingRectTop = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			report.boundingRectRight = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			report.boundingRectLeft = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			report.percentAreaToImageArea = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
			//report.areaToConvexHullArea = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
			report.targetX = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			report.targetY = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
			particles.add(report);
		}
		particles.sort(null);
		// Gets scores from above report
		scores.aspect = aspectScore(particles.elementAt(0));
		scores.area = areaScore(particles.elementAt(0));
		
		//If scores are above 75, the target is a tape
		isTape = (scores.aspect > SCORE_MIN) && (scores.area > SCORE_MIN);
	}
	

	static boolean CompareParticleSizes(Report r1, Report r2)
	{
		return r1.percentAreaToImageArea > r2.percentAreaToImageArea;
	}
	
	public boolean isTargetTape() {
		return isTape;
	}
	
	// Scores aspect ratio of target
	private double aspectScore(Report r) {
		return ratioToScore((TARGET_W_METER/TARGET_H_METER) * ((r.boundingRectBottom-r.boundingRectTop) / (r.boundingRectRight-r.boundingRectLeft)));
	}
	
	// Scores Area of target
	private double areaScore(Report r) {
		double boundingArea = (r.boundingRectBottom - r.boundingRectTop) * (r.boundingRectRight - r.boundingRectLeft); // Area 
		return ratioToScore(r.area/boundingArea);
	}
	
	// Converts above raw scores to a value between 0 - 100
	private double ratioToScore(double r) {
		return (Math.max(0, Math.min(100*(1-Math.abs(1-r)), 100)));
	}
	
	// Calculates vector from Camera to target
	public TargetVector getTargetVector() {
		double normalizedWidth, targetWidth, normalizedHeight, targetHeight;
		NIVision.GetImageSizeResult size;
		
		size = NIVision.imaqGetImageSize(targetFrame);
		
		// Normalized values are the unitless equilvalent of their corresponding values
		normalizedWidth = 2*(particles.get(0).boundingRectRight - particles.get(0).boundingRectLeft)/size.width;
		targetWidth = TARGET_W_METER;
		
		normalizedHeight = 2*(particles.get(0).boundingRectTop - particles.get(0).boundingRectBottom)/size.height;
		targetHeight = TARGET_H_METER;
		
		// Only distanceX used. It is the distance to the target
		double distanceX = targetWidth/(normalizedWidth*Math.tan(FOV_HORZ_ANGLE*Math.PI/(180*2)));
		double distanceY = targetHeight/(normalizedHeight*Math.tan(FOV_VERT_ANGLE*Math.PI/(180*2)));
		
		// Only theta used. It is the angle to turn so the camera is centered on the target
		double theta = (particles.get(0).targetX * (FOV_HORZ_ANGLE / 2)) / (FOV_W_PIXEL / 2) - (FOV_HORZ_ANGLE / 2);
		double phi = (particles.get(0).targetY * (FOV_VERT_ANGLE / 2)) / (FOV_H_PIXEL / 2) - (FOV_VERT_ANGLE / 2);
		
		dashboard.putNumber("Distance To Tape: ", distanceX);
		dashboard.putNumber("Angle To Turn To Tape", theta);
		
		// TargetVector class at bottom
		TargetVector targetVector = new TargetVector();
		targetVector.xDistance = distanceX;
		targetVector.theta = theta;
		targetVector.yDistance = distanceY;
		targetVector.phi = phi;
		return targetVector;
	}
	
	public void drawPredictedShot() {
		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(targetImage);
		double time;
		if(isTape) {
			time = -Math.log((1 - ((Robot.targetVector.xDistance)/ (-MASS_OF_BALL/DRAG_CONSTANT * SHOT_VELOCITY * Math.cos(SHOT_ANGLE))))) / (-DRAG_CONSTANT/MASS_OF_BALL);
		} else {
			time = 0;
		}
		//System.out.println("Time: " + time);
		double trajectoryX = -MASS_OF_BALL/DRAG_CONSTANT * SHOT_VELOCITY * Math.cos(SHOT_ANGLE) * (1-Math.pow(Math.E, -DRAG_CONSTANT/MASS_OF_BALL * time)) + 3.67;
		double trajectoryY = -PERIOD * time + MASS_OF_BALL/DRAG_CONSTANT * (SHOT_VELOCITY*Math.sin(SHOT_ANGLE) + PERIOD) * (1 - Math.pow(Math.E, -DRAG_CONSTANT/MASS_OF_BALL * time)); 
		int pixelX = (int) (((trajectoryX + (FOV_HORZ_ANGLE/2)) * (FOV_W_PIXEL / 2)) / (FOV_HORZ_ANGLE / 2));
		int pixelY = (int) (size.height - ((trajectoryY + (FOV_VERT_ANGLE/2)) * (FOV_H_PIXEL / 2)) / (FOV_VERT_ANGLE / 2));
		if (isTape) {
			NIVision.Rect rect = new NIVision.Rect(pixelY-50, pixelX-50, 100, 100);
			NIVision.imaqDrawShapeOnImage(targetFrame, targetFrame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);	
		}
		
	}
	
	public void displayImage() {
		CameraServer.getInstance().setImage(targetFrame);
	}
	
	@Override
	protected void initDefaultCommand() {

	}

	public final class TargetVector {
		public double xDistance = 0;
		public double theta = 0;
		public double yDistance = 0;
		public double phi = 0;
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
		double area;
		double aspect;
	}

}

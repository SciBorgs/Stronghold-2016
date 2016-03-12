package org.usfirst.frc.team1155.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team1155.robot.PortMap;
import org.usfirst.frc.team1155.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Try not to touch this <br> <br>
 * Subsystem <br>
 * Used for detecting a certain color and shape using image filtering and analyzing <br>
 */
public class ImageSubsystem extends Subsystem {
	/** Image is filtered. Frame is unfiltered */
	private Image targetImage, targetFrame; 
		
	/** Camera session */
	private int session; 
	/** Total number of filtered particles */
	private int numParticles;
	/** Integer error returned after filtering */
	private int imaqError; 
	
	private boolean isTape; 
	
	
	/**
	 * Report and Scores classes at very bottom
	 * <br>
	 * An object that stores values of a specific unfiltered region. Used for analyzing 
	 */
	private Vector<Report> particles; 
	
	/** The "score" of one analyzed region. From 0 - 100, 100 meaning that the region is basically the tape */
	private Scores scores; 
	
	/** Array of criteria for filtering options */ 
	private static NIVision.ParticleFilterCriteria2 criteria[]; 
	/** Filter options for removing noise */
	private static NIVision.ParticleFilterOptions2 filterOptions; 
	
	/**
	 * All values in DEGREES or METERS <br> <br>
	 * 
	 * <b> TARGET_W_METER </b> is the longest side of the retroreflective tape or shape <br>
	 * <b> TARGET_H_METER </b> is the shortest side of the retroreflective tape or shape <br>
	 * <b> TARGET_AREA is </b> the area of the retroreflective tape or shape <br>
	 * <b> FOV_VERT_ANGLE </b> is the VERTICAL Field of View of the camera <br>
	 * <b> FOV_HORZ_ANGLE </b> is the HORIZONTAL Field of View of the camera <br>
	 * <b> FOV_W_PIXEL </b> is the horizontal resolution size of the camera <br>
	 * <b> FOV_H_PIXEL </b> is the vertical resolution size of the camera <br>
	 * <b> SCORE_MIN </b> is the lowest score (Percentage) that signifies that what is being observed is the target tape
	 */
	private static final double TARGET_W_METER = 0.4826, 
								TARGET_H_METER = .3048, // Shortest side of tape
								TARGET_AREA = TARGET_W_METER * TARGET_H_METER, //  Full area of target.
								TAPE_AREA =  0.04902, // Area of tape. Added areas of tape slices.
								FOV_VERT_ANGLE = 60, // 60 degrees
								FOV_HORZ_ANGLE = 60, // 60 degrees
								FOV_W_PIXEL = 640, // Resolution of camera
								FOV_H_PIXEL = 480, // Resolution of camera
								SCORE_MIN = 50; // Lowest score that signifies what is being observed is the target tape
	
	/** 
	 * Physics Constants <br>
	 * Used for drawing target circle
	 */
	private static final double DRAG_CONSTANT = .209092519013,
								MASS_OF_BALL = .295,
								PERIOD = 13.8264152809,
								SHOT_VELOCITY = 7.8,
								SHOT_ANGLE = 50 * Math.PI/180;
								
	/**
	 * Hue, Saturation, Range values for green color <br>
	 * Green LED is used to shine off of the retroreflective tape
	 */
	private static Range tapeHueRange = new Range(110, 130);
	private static Range tapeSatRange = new Range(255, 255);
	private static Range tapeValRange = new Range(255, 255);
	

	public Servo cameraTilt;
	
	public ImageSubsystem() {		
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
		
		cameraTilt = new Servo(9);
	}

	/**
	 * Updates HSV ranges based on user input on SmartDashboard
	 */
	public void updateRanges() {
		tapeHueRange = new Range((int)SmartDashboard.getNumber("H Min"), (int)SmartDashboard.getNumber("H Max"));
		tapeSatRange = new Range((int)SmartDashboard.getNumber("S Min"), (int)SmartDashboard.getNumber("S Max"));
		tapeValRange = new Range((int)SmartDashboard.getNumber("V Min"), (int)SmartDashboard.getNumber("V Max"));
	}
	
	/**
	 * Begins a continous video stream <br>
	 * Stores unfiltered picture into <b> targetFrame </b>
	 */
	public void recordVideo() {
		NIVision.IMAQdxGrab(session, targetFrame, 1);
	}

	/**
	 * Must be called after {@code} recordVideo <br>
	 * Filters <b> targetFrame </b> and stores filtered picture into <b> targetImage </b>
	 * @see recordVideo()
	 */
	public void takePicture() {
		NIVision.imaqColorThreshold(targetImage, targetFrame, 255, NIVision.ColorMode.HSV, tapeHueRange, tapeSatRange, tapeValRange);
		//CameraServer.getInstance().setImage(targetImage);
		//NIVision.imaqFlatten(targetImage, NIVision.FlattenType.FLATTEN_IMAGE, NIVision.CompressionType.COMPRESSION_NONE, 100);
		imaqError = NIVision.imaqParticleFilter4(targetImage, targetImage, criteria, filterOptions, null);		
	}
	
	/**
	 * Called after takePicture() <br>
	 * Stores number of particles in <b> targetImage </b> into <b> numParticles </b> and checks if there are particles in the image
	 * @return True if <b> numParticles </b> > 0 
	 * @see takePicture()
	 */
	public boolean doesTargetExist() {
		numParticles = NIVision.imaqCountParticles(targetImage, 1);

		if (numParticles > 0) {
				return true;
		} else {
			return false;
		}
	}

	// Max distance to shoot: 3.5m, Min distance to shoot: 2m
	/**
	 * <ol>
	 * <li>Call if there are particles in the filtered image </li>
	 * <li>Stores information about the filtered image into <b> report </b> </li>
	 * <li>Stores reports into <b> particles </b> <br>
	 * <li>Takes the score of the largest group of particles in <b> particles </b> </li> 
	 * <li>And compares it with <b> scoreMin </b> to see if the target is tape </li>
	 * <li>Stores that value into <b> isTape </b> </li>
	 * </ol>
	 * 
	 * 
	 * @see doesTargetExist() <br>
	 * aspectScore(Report r) <br>
	 * areaScore(Report r) <br>
	 * Report <br>
	 * Scores 
	 */
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
	
	/**
	 * Method for comparing two reports
	 * @deprecated
	 * @param r1
	 * @param r2
	 * @return True if r1.percentAreaToImageArea is larger
	 * @see Report
	 */
	static boolean CompareParticleSizes(Report r1, Report r2)
	{
		return r1.percentAreaToImageArea > r2.percentAreaToImageArea;
	}
	
	/**
	 * Call after analyzeImage()
	 * @return True if group of particles analyzed looks like tape
	 * @see analyzeImage()
	 */
	public boolean isTargetTape() {
		return isTape;
	}
	
	// Scores aspect ratio of target
	/**
	 * Ratio of tapes (width of tape / height of tape) to the particles (width / height)
	 * @param r (Report) 
	 * @return aspects (size of shape) score (0-100)
	 * @see ratioToScore(double r)
	 */
	private double aspectScore(Report r) {
		return ratioToScore((TARGET_W_METER/TARGET_H_METER) * ((r.boundingRectBottom-r.boundingRectTop) / (r.boundingRectRight-r.boundingRectLeft)));
	}
	
	// Scores Area of target
	/**
	 * Ratio of particles area and particles width * height <br>
	 * Particles area may be less than or greater than particles width * height due to holes or extra particles on the sides
	 * @param r (Report)
	 * @return area score (0-100)
	 * @see ratioToScore(double r)
	 */
	private double areaScore(Report r) {
		double boundingArea = (r.boundingRectBottom - r.boundingRectTop) * (r.boundingRectRight - r.boundingRectLeft); // Area 
		return ratioToScore((TARGET_AREA / TAPE_AREA) * r.area/boundingArea);
	}
	
	// Converts above raw scores to a value between 0 - 100
	/**
	 * Converts raw scores to a final score
	 * 
	 * @param r (double)
	 * @return final score between 0 and 100 <br>
	 * if r < 1 and r > 0 , score = r * 100 <br>
	 * if r = 0 , score = 100 <br>
	 * else score = 0
	 */
	private double ratioToScore(double r) {
		return (Math.max(0, Math.min(100*(1-Math.abs(1-r)), 100)));
	}
	
	// Calculates vector from Camera to target
	/**
	 * Measures the distances and angles from the camera to the target
	 * @return The vector of the target relative to the robot
	 * @see TargetVector
	 */
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
		
		SmartDashboard.putNumber("Distance To Tape: ", distanceX);
		SmartDashboard.putNumber("Angle To Turn To Tape", theta);
		
		// TargetVector class at bottom
		TargetVector targetVector = new TargetVector();
		targetVector.xDistance = distanceX;
		targetVector.theta = theta;
		targetVector.yDistance = distanceY;
		targetVector.phi = phi;
		return targetVector;
	}
	
	/**
	 * Call after analyzeImage() and if isTape() returns true <br>
	 * Draws a circle for aiming when the tape is currently on screen <br>
	 * This is only used for teleoperated
	 * @see analyzeImage() <br>
	 * isTape()
	 */
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
	
	/**
	 * Call after recordVideo() <br>
	 * If in teleoperated and isTape() is true call after drawPredictedShot() <br>
	 * Displays raw image on dashboard
	 * @see recordVideo() <br>
	 * isTape()
	 */
	public void displayImage() {
		CameraServer.getInstance().setImage(targetFrame);
	}
	
	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * TargetVector class stores the distance and angles calculated from the camera to the target
	 * @see getTargetVector()
	 */
	public final class TargetVector {
		public double xDistance = 0;
		public double theta = 0;
		public double yDistance = 0;
		public double phi = 0;
	}
	
	/**
	 * Report class stores the information about the filtered particles in the image <br>
	 * Report class implements Comparator and Comparable and orders the group particles stored from largest to smallest <br>
	 * <ul>
	 * <li> <b> area </b> Area of group of particles </li>
	 * <li> <b> percentAreaToImageArea </b> How much of the camera FOV does the group of particles take up </li>
	 * <li> <b> boundingRectTop </b> Top most pixel of the group of particles </li>
	 * <li> <b> boundingRectBottom </b> Bottom most pixel of the group of particles </li>
	 * <li> <b> boundingRectLeft </b> Left most pixel of the group of particles </li>
	 * <li> <b> boundingRectRight </b> Right most pixel of the group of particles </li>
	 * <li> <b> targetX </b> Center particle of the width of the group of particles </li>
	 * <li> <b> targetY </b> Center particle of the height of the group of particles </li>
	 * </ul>
	 *
	 */
	private class Report implements Comparator<Report>, Comparable<Report> {
		double area;
		double percentAreaToImageArea;
		double boundingRectTop;
		double boundingRectBottom;
		double boundingRectLeft;
		double boundingRectRight;
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
	
	/**
	 * Scores class stores the area score and aspect score of the analyzed particles <br>
	 * <b>area</b> <br>
	 * <b>aspect</b>
	 * 
	 * @see analyzeImage()
	 */
	private class Scores {
		double area;
		double aspect;
	}
}

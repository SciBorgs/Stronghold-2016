package org.usfirst.frc.team1155.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
								FOV_VERT_ANGLE = 86.5, // 86.5 degrees
								FOV_HORZ_ANGLE = 86.5, // 86.5 degrees
								FOV_W_PIXEL = 640, 
								FOV_H_PIXEL = 480,
								AREA_MIN = .5,
								SCORE_MIN = 75;
	private static final Range TAPE_HUE_RANGE = new Range(80, 150);
	private static final Range TAPE_SAT_RANGE = new Range(30, 255);
	private static final Range TAPE_VAL_RANGE = new Range(50, 150);
	
	public ImageSubsystem() {
		targetImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		targetFrame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		
		criteria = new NIVision.ParticleFilterCriteria2[1];
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MIN, 100.0, 0, 0);
		criteria[0].lower = (float) AREA_MIN;
		
		filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
		
		session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);
		
		particles = new Vector<Report>();
		
		scores = new Scores();
		
		imaqError = NIVision.imaqParticleFilter4(targetImage, targetFrame, criteria, filterOptions, null);
	}

	public void recordVideo() {
		NIVision.IMAQdxGrab(session, targetFrame, 1);
		//CameraServer.getInstance().setImage(targetFrame);
	}

	public void takePicture() {
		NIVision.imaqColorThreshold(targetImage, targetFrame, 255, NIVision.ColorMode.HSV, TAPE_HUE_RANGE, TAPE_SAT_RANGE, TAPE_VAL_RANGE);
		//NIVision.imaqFlatten(targetImage, NIVision.FlattenType.FLATTEN_IMAGE, NIVision.CompressionType.COMPRESSION_NONE, 100);
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
			report.boundingRectBottom = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			report.boundingRectTop = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			report.boundingRectRight = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			report.boundingRectLeft = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			report.percentAreaToImageArea = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
			report.targetX = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			report.targetY = NIVision.imaqMeasureParticle(targetImage, pixel, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
			particles.add(report);
		}
		particles.sort(null);
		scores.aspect = aspectScore(particles.get(0));
		scores.area = areaScore(particles.get(0));
		
		isTape = scores.aspect > SCORE_MIN && scores.area > SCORE_MIN;
	}
	
	public boolean isTargetTape() {
		return isTape;
	}
	
	public double aspectScore(Report r) {
		return ratioToScore(((r.boundingRectRight-r.boundingRectLeft)/(r.boundingRectBottom-r.boundingRectTop)));
	}
	
	public double areaScore(Report r) {
		double boundingArea = (r.boundingRectBottom - r.boundingRectTop) * (r.boundingRectRight - r.boundingRectLeft);
		//Tape is 7" edge so 49" bounding rect. With 2" wide tape it covers 24" of the rect.
		return ratioToScore((49/24)*r.area/boundingArea);
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

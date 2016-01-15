package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.Ultrasonic;

public class UltrasonicSubsystem {
	public static Ultrasonic leftUltrasonic;

	public static Ultrasonic rightUltrasonic;
	
	//In feet
	private static final double CLOSEST_DISTANCE = 12;
	private static final double DISTANCE_BETWEEN_ULTRASONICS = 12;

	
	public UltrasonicSubsystem() {
		leftUltrasonic = Hardware.INSTANCE.leftUltrasonic;
		rightUltrasonic = Hardware.INSTANCE.rightUltrasonic;
		leftUltrasonic.setEnabled(true);
		rightUltrasonic.setEnabled(true);
	}
	
	public void pingUltrasonics() {
		leftUltrasonic.ping();
		rightUltrasonic.ping();
	}
	
	public boolean isPathObstructed() {
		if(leftUltrasonic.getRangeInches() <= CLOSEST_DISTANCE && rightUltrasonic.getRangeInches() <= CLOSEST_DISTANCE)
			return true;
		else 
			return false;
	}
	
	public double findClosestRange() {
		double range;
		if (leftUltrasonic.getRangeInches() < rightUltrasonic.getRangeInches()) {
    		range = leftUltrasonic.getRangeInches();
    	} else {
    		range = rightUltrasonic.getRangeInches();
    	}
		return range;
	}
	
	public double angleToAlignTo() {
		//negative is left, positive is right
		
		//find distance from both ultrasonics
		double leftDistance = leftUltrasonic.getRangeInches();
		double rightDistance = rightUltrasonic.getRangeInches();
		//get the angle
		if (rightDistance - leftDistance > 0){
			double e = Math.sqrt(Math.pow(DISTANCE_BETWEEN_ULTRASONICS, 2) + Math.pow((rightDistance-leftDistance) , 2));
			double f = Math.sqrt(Math.pow(DISTANCE_BETWEEN_ULTRASONICS, 2) + Math.pow(rightDistance, 2));
			return Math.acos(Math.pow(leftDistance, 2) - Math.pow(e, 2) - Math.pow(f, 2) + 2 * e * f);
		} else if (rightDistance - leftDistance < 0) {
			//needs to be implemented; 
		} else {
			return 0;
		}
		return 0;
	}
}

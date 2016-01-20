package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	private CANTalon frontRightTalon, backRightTalon, frontLeftTalon, backLeftTalon;
	
	private Gyro gyro;
	
	public static Ultrasonic leftUltrasonic, rightUltrasonic;
	private static final double CLOSEST_DISTANCE = 12;
	private static final double DISTANCE_BETWEEN_ULTRASONICS = 12;

	public Drive() {
		
		frontRightTalon = Hardware.INSTANCE.frontRightTalon;
		backRightTalon = Hardware.INSTANCE.backRightTalon;
		frontLeftTalon = Hardware.INSTANCE.frontLeftTalon;
		backLeftTalon = Hardware.INSTANCE.backLeftTalon;
		gyro = Hardware.INSTANCE.gyro;

		// Sets back right talons as followers to the front right talon
		backRightTalon.changeControlMode(CANTalon.ControlMode.Follower);
		backRightTalon.set(frontRightTalon.getDeviceID());

		// Sets back left talons as followers to the front left talon
		backLeftTalon.changeControlMode(CANTalon.ControlMode.Follower);
		backLeftTalon.set(frontLeftTalon.getDeviceID());
		
		leftUltrasonic = Hardware.INSTANCE.leftUltrasonic;
		rightUltrasonic = Hardware.INSTANCE.rightUltrasonic;
		leftUltrasonic.setEnabled(true);
		rightUltrasonic.setEnabled(true);
	}

	
	//DRIVING METHODS
	public void setSpeed(double speedLeft, double speedRight) {
		frontRightTalon.set(speedRight);
		frontLeftTalon.set(speedLeft);

	}

	public void stopMoving() {
		frontRightTalon.set(0);
		frontLeftTalon.set(0);
	}
	
	public void turnRobot(double angle){
		if (angle < 0){
			frontRightTalon.set(0.5);
			frontLeftTalon.set(-0.5);
		} else if (angle > 0) {
			frontRightTalon.set(-0.5);
			frontLeftTalon.set(0.5);
		}
	}
	
	//GYRO METHODS
	public double getAngle() {
		return gyro.getAngle();
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
	public void freeGyro() {
		//"Removes" gyro from use
		gyro.free();
	}
	
	public boolean canTurn(double angle, double originalAngle) {
		// Gets the current gyro value while turning
		double gyroAngle = gyro.getAngle();
		if ((gyroAngle - originalAngle) < angle) {
			return false;
		} else {
			return true;
		}
	}
	
	//ULTRASONIC METHODS
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
		//the angle from a right triangle made from the difference between the two lengths and distance between ultrasonics
		if (rightDistance > leftDistance){
			double b = Math.atan2(rightDistance - leftDistance, DISTANCE_BETWEEN_ULTRASONICS);
			//turning with right wheels = positive angle
			return 180-b;
		} else if (rightDistance < leftDistance){
			double a = Math.atan2(leftDistance - rightDistance, DISTANCE_BETWEEN_ULTRASONICS);
			//turning with left wheels = negative angle
			return -(180-a);
		} else {
			return 0;
		}
	}

	public void initDefaultCommand() {
		stopMoving();
	}

}

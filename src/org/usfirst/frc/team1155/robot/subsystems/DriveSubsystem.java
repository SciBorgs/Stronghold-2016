package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem extends Subsystem {
	private CANTalon frontRightTalon, backRightTalon, frontLeftTalon,
			backLeftTalon;

	private AnalogGyro gyro;

	private Ultrasonic leftUltrasonic, rightUltrasonic;

	private static final double CLOSEST_DISTANCE = 12;
	private static final double DISTANCE_BETWEEN_ULTRASONICS = 12;

	private static final double MAX_GYRO_BUFFER = 1;
	private static final double MIN_GYRO_BUFFER = -1;
	
	private SmartDashboard dashboard = Robot.dashboard;
	private static Timer timer = new Timer();

	public DriveSubsystem() {
		timer.reset();
		
		frontRightTalon = Hardware.INSTANCE.frontRightTalon;
		backRightTalon = Hardware.INSTANCE.backRightTalon;
		frontLeftTalon = Hardware.INSTANCE.frontLeftTalon;
		backLeftTalon = Hardware.INSTANCE.backLeftTalon;
		gyro = Hardware.INSTANCE.gyro;

		// Sets back right talons as followers to the front right talon
		backRightTalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		backRightTalon.set(frontRightTalon.getDeviceID());

		// Sets back left talons as followers to the front left talon
		backLeftTalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		backLeftTalon.set(frontLeftTalon.getDeviceID());

		leftUltrasonic = Hardware.INSTANCE.leftUltrasonic;
		rightUltrasonic = Hardware.INSTANCE.rightUltrasonic;
//		leftUltrasonic.setEnabled(true);
//		rightUltrasonic.setEnabled(true);
	}

	// DRIVING METHODS
	
	//For setting talon speed
	public void setSpeed(double speedLeft, double speedRight) {
		frontRightTalon.set(speedRight);
		frontLeftTalon.set(speedLeft);
	}

	//For stopping movement
	public void stopMoving() {
		frontRightTalon.set(0);
		frontLeftTalon.set(0);
	}

	//For turning robot
	public void turnRobot(double angle) {
		if (angle < 0) {
			//Turns robot left
			frontRightTalon.set(0.5);
			frontLeftTalon.set(-0.5);
		} else if (angle > 0) {
			//Turns robot right
			frontRightTalon.set(-0.5);
			frontLeftTalon.set(0.5);
		}
	}
	
	//Updates SmartDashboard with wheels speed
	public void updateDriveDashboard() {
		dashboard.putNumber("Left_Wheels_Speed", frontLeftTalon.get());
		dashboard.putNumber("Right_Wheels_Speed", frontRightTalon.get());
	}

	// GYRO METHODS
	
	//For getting gyro angle
	public double getAngle() {
		return gyro.getAngle();
	}

	//For resetting gyro
	public void resetGyro() {
		gyro.reset();
	}

	//For "removing" gyro from use
	public void freeGyro() {
		gyro.free();
	}

	//For finding out if the robot can turn
	public boolean canTurn(double angle, double originalAngle) {
		// Gets the current gyro value while turning
		double gyroAngle = gyro.getAngle();
		if ((gyroAngle - originalAngle) < angle) {
			return false;
		} else {
			return true;
		}
		
	}
	
	//Updates SmartDashboard with angle
	public void updateGyroDashboard() {
		dashboard.putNumber("Robot turning angle", gyro.getAngle());
	}

	// ULTRASONIC METHODS
	
	//For pinging ultrasonics
	public void pingUltrasonics() {
		leftUltrasonic.ping();
		rightUltrasonic.ping();
	}

	//For finding out if path is obstructed
	public boolean isPathObstructed() {
		if (leftUltrasonic.getRangeInches() <= CLOSEST_DISTANCE
				&& rightUltrasonic.getRangeInches() <= CLOSEST_DISTANCE){
			dashboard.putBoolean("Is path obstructed", true);
			return true;
		}else{
			dashboard.putBoolean("Is path obstructed", false);
			return false;
		}
	}

	//For finding closest range and setting it as the range
	public double findClosestRange() {
		double range;
		if (leftUltrasonic.getRangeInches() < rightUltrasonic.getRangeInches()) {
			range = leftUltrasonic.getRangeInches();
			dashboard.putString("Closest ultrasonic is", "left");
		} else {
			range = rightUltrasonic.getRangeInches();
			dashboard.putString("Closest ultrasonic is", "right");
		}
		return range;
	}

	//For finding the angle the robot needs to align to
	public double angleToAlignTo() {
		// Negative is right, Positive is left
		// Finds distance from both ultrasonics
		double leftDistance = leftUltrasonic.getRangeInches();
		double rightDistance = rightUltrasonic.getRangeInches();

		// Get the angle from a right triangle made from the difference between
		// the two lengths and distance between ultrasonics
		if (rightDistance > leftDistance) {
			double angleToMoveLeft = Math.atan2(rightDistance - leftDistance, DISTANCE_BETWEEN_ULTRASONICS);
			
			// Turning with right wheels = positive angle
			return 90 - angleToMoveLeft;

		} else if (rightDistance < leftDistance) {
			double angleToMoveRight = Math.atan2(leftDistance - rightDistance, DISTANCE_BETWEEN_ULTRASONICS);
			
			// Turning with left wheels = negative angle
			return -(90 - angleToMoveRight);
		} else {
			return 0;
		}
	}
	
	//Updates SmartDashboard with left and right ultrasonic ranges
	public void updateUltrasonicsDashboard() {
		dashboard.putNumber("Right ultrasonic range", rightUltrasonic.getRangeInches());
		dashboard.putNumber("Left ultrasonic range", leftUltrasonic.getRangeInches());
	}
	
	//Autonomous Methods
	
	//Start timer
	public void startTimer() {
		timer.start();
	}
	
	//Stop timer
	public void stopTimer() {
		timer.stop();
	}
	
	//Resets timer
	public void resetTimer() {
		timer.reset();
	}
	
	//Checks to see if the buffer has been passed
	public boolean timerBuffer(long period) {
		return timer.hasPeriodPassed(period);
	}
	
	//Checks if gyro is stable
	public boolean isGyroStable() {
		return (gyro.getAngle() <= MAX_GYRO_BUFFER && gyro.getAngle() >= MIN_GYRO_BUFFER);
	}
	
	//Finds distance driven
	public double getDistanceDriven() {
		return frontRightTalon.getEncVelocity() * timer.get();
	}
	
	

	public void initDefaultCommand() {
		stopMoving();
	}

}

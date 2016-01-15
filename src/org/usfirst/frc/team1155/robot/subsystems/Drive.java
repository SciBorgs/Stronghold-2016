package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	public Ultrasonic leftUltra, rightUltra;
	public Gyro gyro;
	public CANTalon frontRightTalon, midRightTalon, backRightTalon, frontLeftTalon, midLeftTalon, backLeftTalon;

	public Drive() {
		leftUltra = Hardware.INSTANCE.leftUltrasonic;
		rightUltra = Hardware.INSTANCE.rightUltrasonic;

		gyro = Hardware.INSTANCE.gyro;

		frontRightTalon = Hardware.INSTANCE.frontRightTalonRef;
		midRightTalon = Hardware.INSTANCE.midRightTalonRef;
		backRightTalon = Hardware.INSTANCE.backRightTalonRef;
		frontLeftTalon = Hardware.INSTANCE.frontLeftTalonRef;
		midLeftTalon = Hardware.INSTANCE.midLeftTalonRef;
		backLeftTalon = Hardware.INSTANCE.backLeftTalonRef;
		
		midRightTalon.changeControlMode(CANTalon.ControlMode.Follower);
		midRightTalon.set(frontRightTalon.getDeviceID());
		backRightTalon.changeControlMode(CANTalon.ControlMode.Follower);
		backRightTalon.set(frontRightTalon.getDeviceID());

		backLeftTalon.changeControlMode(CANTalon.ControlMode.Follower);
		midLeftTalon.set(frontLeftTalon.getDeviceID());
		midLeftTalon.changeControlMode(CANTalon.ControlMode.Follower);
		midLeftTalon.set(frontLeftTalon.getDeviceID());
	}

	public void initDefaultCommand() {
	}

	public void setSpeed(double speedLeft, double speedRight) {
		frontRightTalon.set(speedRight);
		midRightTalon.set(speedRight);
		backRightTalon.set(speedRight);
		frontLeftTalon.set(speedLeft);
		midLeftTalon.set(speedLeft);
		backLeftTalon.set(speedLeft);
		// NOTE: Do not give mid and back wheels a speed if they are in follower
		// mode.
	}

	public boolean turnAngle(double angle, double originalAngle,
			double gyroAngle) {
		gyroAngle = gyro.getAngle();// gets the current gyro value while turning
		if ((gyroAngle - originalAngle) < angle) {
			setSpeed(0.6, -0.6);// turn if hasnt finished turn
			return false;
		} else {
			setSpeed(0, 0);// stop if done
			return true;
		}

	}
}

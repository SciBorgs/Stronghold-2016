package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	private CANTalon frontRightTalon, midRightTalon, backRightTalon,
			frontLeftTalon, midLeftTalon, backLeftTalon;

	public Drive() {

		frontRightTalon = Hardware.INSTANCE.frontRightTalon;
		midRightTalon = Hardware.INSTANCE.midRightTalon;
		backRightTalon = Hardware.INSTANCE.backRightTalon;
		frontLeftTalon = Hardware.INSTANCE.frontLeftTalon;
		midLeftTalon = Hardware.INSTANCE.midLeftTalon;
		backLeftTalon = Hardware.INSTANCE.backLeftTalon;

		//Sets middle and back right talons as followers to the front right talon
		midRightTalon.changeControlMode(CANTalon.ControlMode.Follower);
		midRightTalon.set(frontRightTalon.getDeviceID());
		backRightTalon.changeControlMode(CANTalon.ControlMode.Follower);
		backRightTalon.set(frontRightTalon.getDeviceID());

		//Sets middle and back left talons as followers to the front left talon
		backLeftTalon.changeControlMode(CANTalon.ControlMode.Follower);
		backLeftTalon.set(frontLeftTalon.getDeviceID());
		midLeftTalon.changeControlMode(CANTalon.ControlMode.Follower);
		midLeftTalon.set(frontLeftTalon.getDeviceID());
	}

	public void setSpeed(double speedLeft, double speedRight) {
		frontLeftTalon.set(speedLeft);
		frontRightTalon.set(speedRight);
		
	}

	public void stopMoving() {
		frontRightTalon.set(0);
		frontLeftTalon.set(0);
	}

	public void initDefaultCommand() {
		stopMoving();
	}

}

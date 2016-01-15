package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	public CANTalon frontRightTalon, midRightTalon, backRightTalon, frontLeftTalon, midLeftTalon, backLeftTalon;

	public Drive() {

		frontRightTalon = Hardware.INSTANCE.frontRightTalon;
		midRightTalon = Hardware.INSTANCE.midRightTalon;
		backRightTalon = Hardware.INSTANCE.backRightTalon;
		frontLeftTalon = Hardware.INSTANCE.frontLeftTalon;
		midLeftTalon = Hardware.INSTANCE.midLeftTalon;
		backLeftTalon = Hardware.INSTANCE.backLeftTalon;
		
		midRightTalon.changeControlMode(CANTalon.ControlMode.Follower);
		midRightTalon.set(frontRightTalon.getDeviceID());
		backRightTalon.changeControlMode(CANTalon.ControlMode.Follower);
		backRightTalon.set(frontRightTalon.getDeviceID());

		backLeftTalon.changeControlMode(CANTalon.ControlMode.Follower);
		backLeftTalon.set(frontLeftTalon.getDeviceID());
		midLeftTalon.changeControlMode(CANTalon.ControlMode.Follower);
		midLeftTalon.set(frontLeftTalon.getDeviceID());
	}

	public void initDefaultCommand() {
	}

	public void setSpeed(double speedLeft, double speedRight) {
		frontRightTalon.set(speedRight);
		frontLeftTalon.set(speedLeft);
		// NOTE: Do not give mid and back wheels a speed if they are in follower
		// mode.
	}

	//Continues to turn until reaches angle parameter
	
}

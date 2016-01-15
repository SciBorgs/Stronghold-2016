package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroSubsystem extends Subsystem {
	private Gyro gyro;

	public GyroSubsystem() {
		gyro = Hardware.INSTANCE.gyro;
	
		//Calibrates gyro upon start
		gyro.initGyro();
	}
	
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

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
}

package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimbSubsystem extends Subsystem {
	private static CANTalon armTalon = Hardware.INSTANCE.armTalon;
	private static CANTalon winchTalon = Hardware.INSTANCE.winchTalon;
	private static CANTalon winchFollowerTalon = Hardware.INSTANCE.winchFollowerTalon;
	private static final int MAX_ENCODER_POSITION = 1023;
	private static final int MIN_ENCODER_POSITION = 0;
	private static final int BUFFER = 5;
	public ClimbSubsystem() {
		armTalon.changeControlMode(CANTalon.ControlMode.Position);
		
		winchFollowerTalon.changeControlMode(CANTalon.ControlMode.Follower);
		winchFollowerTalon.set(winchTalon.getDeviceID());
	}

	@Override
	protected void initDefaultCommand() {
		winchTalon.set(0);
		armTalon.set(0);
	}

	public void extendArm() {
		armTalon.set(1023);
	}

	public void retractArm() {
		armTalon.set(0);
	}

	public void setArmPosition(double position) {
		armTalon.set(position);
	}
	
	public double getArmPosition() {
		return armTalon.getEncPosition();
	}

	public boolean cannotMoveArmUp() {
		return (armTalon.getEncPosition() >= MAX_ENCODER_POSITION - BUFFER);
	}
	
	public boolean cannotMoveArmDown() {
		return (armTalon.getEncPosition() <= MIN_ENCODER_POSITION + BUFFER);
	}
	
	public void extendWinch() {
		winchTalon.set(1);
	}
	
	public void retractWinch() {
		winchTalon.set(-1);
	}
	
	public void setWinchSpeed(double speed) {
		winchTalon.set(speed);
	}
}

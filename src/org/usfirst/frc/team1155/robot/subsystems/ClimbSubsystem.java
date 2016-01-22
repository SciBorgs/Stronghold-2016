package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimbSubsystem extends Subsystem {
	private static CANTalon armTalon = Hardware.INSTANCE.armTalon;
	private static CANTalon winchTalon = Hardware.INSTANCE.winchTalon;
	private static CANTalon winchFollowerTalon = Hardware.INSTANCE.winchFollowerTalon;
	
	private static SmartDashboard dashboard = Robot.dashboard;
	
	private static final int MAX_ENCODER_POSITION = 1023;
	private static final int MIN_ENCODER_POSITION = 0;
	private static final int BUFFER = 5;
	
	private static int previousArmEncoderPosition, previousWinchEncoderPosition;
	
	public ClimbSubsystem() {	
		previousArmEncoderPosition = armTalon.getEncPosition();
		previousWinchEncoderPosition = winchTalon.getEncPosition();
		
		armTalon.changeControlMode(CANTalon.ControlMode.Position);
		
		winchTalon.changeControlMode(CANTalon.ControlMode.Position);
		winchFollowerTalon.changeControlMode(CANTalon.ControlMode.Follower);
		winchFollowerTalon.set(winchTalon.getDeviceID());
		
	}

	@Override
	protected void initDefaultCommand() {
		winchTalon.set(MIN_ENCODER_POSITION);
		armTalon.set(MIN_ENCODER_POSITION);
	}

	
	//Arm Methods
	public void rotateArmOut() {
		armTalon.set(MAX_ENCODER_POSITION);
	}

	public void rotateArmIn() {
		armTalon.set(MIN_ENCODER_POSITION);
	}

	public void setArmPosition(double position) {
		armTalon.set(position);
	}
	
	public void updateArmDashboard() {
		if (previousArmEncoderPosition > armTalon.getEncPosition()) {
			dashboard.putBoolean("Rotating Arm Out", true);
			dashboard.putBoolean("Rotating Arm In", false);
	
		} else if (previousArmEncoderPosition < armTalon.getEncPosition()) {
			dashboard.putBoolean("Rotating Arm Out", false);
			dashboard.putBoolean("Rotating Arm In", true);
		}
		
		if (previousArmEncoderPosition >= MAX_ENCODER_POSITION - BUFFER) {
			dashboard.putBoolean("Arm Can Rotate Out", false);
		} else {
			dashboard.putBoolean("Arm Can Rotate Out", true);
		}
		
		if (previousArmEncoderPosition <= MIN_ENCODER_POSITION + BUFFER) {
			dashboard.putBoolean("Arm Can Rotate In", false);
		} else {
			dashboard.putBoolean("Arm Can Rotate In", true);
		}
		
		previousArmEncoderPosition = armTalon.getEncPosition();
		dashboard.putNumber("Arm Encoder Position", armTalon.getEncPosition());
	}
	
	public double getArmPosition() {
		return armTalon.getEncPosition();
	}

	public boolean cannotRotateArmOut() {
		return (armTalon.getEncPosition() >= MAX_ENCODER_POSITION - BUFFER);
	}
	
	public boolean cannotRotateArmIn() {
		return (armTalon.getEncPosition() <= MIN_ENCODER_POSITION + BUFFER);
	}
	
	
	//Winch Methods
	public void extendWinch() {
		winchTalon.set(MAX_ENCODER_POSITION);
	}
	
	public void retractWinch() {
		winchTalon.set(MIN_ENCODER_POSITION);
	}
	
	public boolean cannotExtendWinch() {
		return (winchTalon.getEncPosition() >= MAX_ENCODER_POSITION - BUFFER);
	}
	
	public boolean cannotRetractWinch() {
		return (winchTalon.getEncPosition() <= MIN_ENCODER_POSITION + BUFFER);
	}
	
	public void updateWinchDashboard() {
		if (previousWinchEncoderPosition > armTalon.getEncPosition()) {
			dashboard.putBoolean("Extending Winch", true);
			dashboard.putBoolean("Retracting Winch", false);
	
		} else if (previousWinchEncoderPosition < armTalon.getEncPosition()) {
			dashboard.putBoolean("Extending Winch", false);
			dashboard.putBoolean("Retracting Winch", true);
		}
		
		if (previousWinchEncoderPosition >= MAX_ENCODER_POSITION - BUFFER) {
			dashboard.putBoolean("Winch Can Extend", false);
		} else {
			dashboard.putBoolean("Winch Can Extend", true);
		}
		
		if (previousWinchEncoderPosition <= MIN_ENCODER_POSITION + BUFFER) {
			dashboard.putBoolean("Winch Can Retract", false);
		} else {
			dashboard.putBoolean("Winch Can Retract", true);
		}
		
		previousWinchEncoderPosition = winchTalon.getEncPosition();
		dashboard.putNumber("Winch Encoder Position", winchTalon.getEncPosition());
	}
	
}

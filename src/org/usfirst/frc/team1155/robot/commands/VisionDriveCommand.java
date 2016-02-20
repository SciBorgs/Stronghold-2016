package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionDriveCommand extends Command {

	private double distanceToDrive;
	
	private static final double DRIVE_SPEED = 0.2;
	private static final double DISTANCE_AWAY = 2;
	
	public VisionDriveCommand() {
		requires(Robot.driveSubsystem);
	}

	@Override
	protected void initialize() {
		distanceToDrive = Robot.targetVector.xDistance * Math.cos(50 * Math.PI/180);
		Robot.driveSubsystem.setSpeed(-DRIVE_SPEED, -DRIVE_SPEED);
	}

	@Override
	protected void execute() {
		distanceToDrive = Robot.targetVector.xDistance * Math.cos(50 * Math.PI/180);
		SmartDashboard.putNumber("Distance to drive", distanceToDrive);
	}

	@Override
	protected boolean isFinished() {
		return distanceToDrive <= DISTANCE_AWAY;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
		Robot.driveSubsystem.setSpeed(0, 0);		
	}

}

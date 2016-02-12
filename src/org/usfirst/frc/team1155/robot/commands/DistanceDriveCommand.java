package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DistanceDriveCommand extends Command {
	
	private double distanceToDrive;	
	private double initialDistance;
	
	private static final double DRIVE_SPEED = 0.5;
	private static final double DISTANCE_BUFFER = 2;
		
	public DistanceDriveCommand(double distance) {
		this.distanceToDrive = distance;
	}
	
	@Override
	//drives the robot
	protected void initialize() {
		requires(Robot.driveSubsystem);
		
		initialDistance = Robot.driveSubsystem.getEncoderDistance();
		Robot.driveSubsystem.setSpeed(DRIVE_SPEED, DRIVE_SPEED);
	}

	@Override
	protected void execute() {
	
	}

	@Override
	//finishes when the robot drives a certain number of ticks
	protected boolean isFinished() {
		double changeInEncoderDistance = Robot.driveSubsystem.getEncoderDistance() - initialDistance;
		return Math.abs(changeInEncoderDistance - distanceToDrive) <= DISTANCE_BUFFER;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
		
	}

}

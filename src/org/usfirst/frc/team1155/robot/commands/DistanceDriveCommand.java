package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DistanceDriveCommand extends Command {
	
	private double distanceToDrive;	// FEET
	private double initialDistance;
	
	private static final double DRIVE_SPEED = 0.5;
	private static final double DISTANCE_BUFFER = 2;
		
	/**
	 * Command for driving a certain distance in encoder ticks
	 * 
	 * @param distance Distance to travel in encoder ticks
	 */
	public DistanceDriveCommand(double distance) {
		this.distanceToDrive = distance;
	}
	
	@Override
	// Starts robot's drive
	protected void initialize() {
		requires(Robot.driveSubsystem);
		
		initialDistance = Robot.driveSubsystem.getEncoderDistance();
		Robot.driveSubsystem.setSpeed(DRIVE_SPEED, DRIVE_SPEED);
	}

	@Override
	protected void execute() {
	
	}

	@Override
	// Finishes when the robot drives a certain number of encoder ticks (distance traveled)
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

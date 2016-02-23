package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceDriveCommand extends Command {
	
	private double distanceToDrive;	// FEET
	private double initialDistance;
	private double changeInEncoderDistance;
	
	private static final double DRIVE_SPEED = 0.2;
	private static final double DISTANCE_BUFFER = 5;
		
	/**
	 * Command for driving a certain distance in encoder ticks
	 * 
	 * @param distance Distance to travel in encoder ticks
	 */
	public DistanceDriveCommand(double distance) {
		requires(Robot.driveSubsystem);
 
		this.distanceToDrive = distance;
		changeInEncoderDistance = 0;
	}
	
	
	@Override
	// Starts robot's drive
	protected void initialize() {
		initialDistance = Robot.driveSubsystem.getEncoderDistance();
		System.out.println("Initial encoder pos: " + initialDistance);
		Robot.driveSubsystem.setSpeed(DRIVE_SPEED, DRIVE_SPEED);
	}

	@Override
	protected void execute() {
		changeInEncoderDistance = Math.abs(Robot.driveSubsystem.getEncoderDistance() - initialDistance);
	}

	@Override
	// Finishes when the robot drives a certain number of encoder ticks (distance traveled)
	protected boolean isFinished() {
		SmartDashboard.putNumber("Distance Driven", changeInEncoderDistance);
		return changeInEncoderDistance >= distanceToDrive;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
		System.out.println("Distance Drive Over");
	}

	@Override
	protected void interrupted() {
		
	}

}

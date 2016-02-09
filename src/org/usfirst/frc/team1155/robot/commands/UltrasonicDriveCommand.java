package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UltrasonicDriveCommand extends Command{

	private double stopDistanceFromWall;
	
	private static final double ULTRASONIC_BUFFER = 2;
	private static final double DRIVE_SPEED = 0.5;
	
	public UltrasonicDriveCommand(double stopDistanceFromWall) {
		this.stopDistanceFromWall = stopDistanceFromWall;
	}
	@Override
	protected void initialize() {
		requires(Robot.driveSubsystem);
		
		Robot.driveSubsystem.setSpeed(DRIVE_SPEED, DRIVE_SPEED);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.driveSubsystem.getClosestUltrasonicDistance() - stopDistanceFromWall) <= ULTRASONIC_BUFFER;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UltrasonicDriveCommand extends Command{

	private double stopDistanceFromWall;
	
	private static final double ULTRASONIC_BUFFER = 2;
	private static final double DRIVE_SPEED = 0.5;
	
	/**
	 * Autonomous Command
	 * <br><br>
	 * Drive for certain distance. Uses ultrasonic to determine when to stop the robot. 
	 * 
	 * @param stopDistanceFromWall How far from wall you want the robot to stop
	 */
	public UltrasonicDriveCommand(double stopDistanceFromWall) {
		requires(Robot.driveSubsystem);
		this.stopDistanceFromWall = stopDistanceFromWall;
	}
	@Override
	protected void initialize() {
		Robot.driveSubsystem.setSpeed(DRIVE_SPEED, DRIVE_SPEED);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	//stops driving when the robot is close to the wall 
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

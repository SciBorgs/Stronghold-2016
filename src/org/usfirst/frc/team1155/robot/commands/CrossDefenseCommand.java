package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CrossDefenseCommand extends Command{
	
	private final double ANGLE_BUFFER = 5; 
	//private final double TIME_TO_CROSS = 1.2;
	private final double INITIAL_SPEED = 0.5;
	
	private boolean didPointDown;
	private double initialAngle;
	
	//gets the angle before crossing
	public CrossDefenseCommand() {
		requires(Robot.driveSubsystem);
		didPointDown = false;
		initialAngle = Robot.driveSubsystem.chickenGyro.getAngle();
	}
	
	@Override
	protected void initialize() {
		Robot.driveSubsystem.setSpeed(INITIAL_SPEED, INITIAL_SPEED);
	}

	@Override
	//checks if the robot pointed downwards after crossing
	protected void execute() {
		if(Robot.driveSubsystem.chickenGyro.getAngle() > initialAngle) {
			didPointDown = true;
		}
	}

	@Override
	//finishes when the robot pointed down and its angle is within the buffer
	protected boolean isFinished() {
		return didPointDown && Math.abs(Robot.driveSubsystem.chickenGyro.getAngle() - initialAngle) <= ANGLE_BUFFER;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}

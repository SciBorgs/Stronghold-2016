package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CrossDefenseCommand extends Command{
	
	private final double ANGLE_BUFFER = 5; 
	//private final double TIME_TO_CROSS = 1.2;
	private final double INITIAL_SPEED = 0.5;
	
	private boolean didPointDown;
	private double initialAngle;
	
	/**
	 * Autonomous command to cross most defenses
	 */
	public CrossDefenseCommand() {
		requires(Robot.driveSubsystem);
		didPointDown = false;
		Robot.driveSubsystem.stabalizationGyro.reset();
		Robot.driveSubsystem.driveGyro.reset();
		// Creates the starting angle to check for after crossing defense 
		// (IE when the robot is level to the ground again)
		initialAngle = Robot.driveSubsystem.stabalizationGyro.getAngle();
	}
	
	@Override
	protected void initialize() {
		Robot.driveSubsystem.setSpeed(-INITIAL_SPEED, -INITIAL_SPEED);
	}

	@Override
	// Checks if the robot pointed downwards after crossing 
	// (IE went up a ramp and went back down another ramp)
	protected void execute() {
		if((int) Robot.driveSubsystem.driveGyro.getAngle() > 0){
			Robot.driveSubsystem.setSpeed(-INITIAL_SPEED - .2, -INITIAL_SPEED + .2);
		} else if((int) Robot.driveSubsystem.driveGyro.getAngle() < 0){
			Robot.driveSubsystem.setSpeed(-INITIAL_SPEED + .2, -INITIAL_SPEED - .2);
		} else {
			Robot.driveSubsystem.setSpeed(-INITIAL_SPEED, -INITIAL_SPEED);
		}
		
		if(Robot.driveSubsystem.stabalizationGyro.getAngle() > initialAngle) {
			didPointDown = true;
		}
		SmartDashboard.putNumber("Stabalization Angle", Robot.driveSubsystem.stabalizationGyro.getAngle());
	}

	@Override
	// Finishes when the robot pointed down and its angle is within the buffer
	protected boolean isFinished() {
		return didPointDown && Math.abs(Robot.driveSubsystem.stabalizationGyro.getAngle() - initialAngle) <= ANGLE_BUFFER;
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

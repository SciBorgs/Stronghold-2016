package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterIOCommand extends Command {

	private Mode mode;

	private final double INPUT_SPEED = -0.5, SHOOT_SPEED = 1.0;
	
	public enum Mode {
		INPUT, OUTPUT;
	}

	public ShooterIOCommand(Mode mode) {
		requires(Robot.shootSubsystem);
		this.mode = mode;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shootSubsystem.setSpeed((mode == Mode.OUTPUT) ? SHOOT_SPEED : INPUT_SPEED);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (mode == Mode.INPUT) 
			return Robot.shootSubsystem.isBallPossessed();
		else if (mode == Mode.OUTPUT)
			return !Robot.shootSubsystem.isBallPossessed() && Robot.shootSubsystem.isPistonRetracted();
		else 
			return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shootSubsystem.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.shootSubsystem.setSpeed(0);
	}
}

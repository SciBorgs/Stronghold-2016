package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConveyorBeltCommand extends Command {

	private final double CONVEYOR_SPEED = .3;

	/**
	 * Starts Conveyor belt to move ball up to shooter
	 */
	public ConveyorBeltCommand() {
		requires(Robot.intakeSubsystem);
	}
	
	// Starts the conveyor belt in the shooter
	protected void initialize() {
		Robot.intakeSubsystem.setConveyorSpeed(CONVEYOR_SPEED);
	}

	protected void execute() {
	}

	// Ends when it detects the ball in the compartment
	protected boolean isFinished() {
		return Robot.shootSubsystem.isBallPossessed();
	}

	protected void end() {
		Robot.intakeSubsystem.setConveyorSpeed(0);
	}

	protected void interrupted() {
		Robot.intakeSubsystem.setConveyorSpeed(0);
	}
}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConveyorBeltCommand extends Command {

	private final double CONVEYOR_SPEED = .7;

	/**
	 * Starts Conveyor belt to move ball up to shooter
	 */
	public ConveyorBeltCommand() {
		requires(Robot.intakeSubsystem);
	}
	
	// Starts the conveyor belt in the shooter
	protected void initialize() {
		
	}

	protected void execute() {
		System.out.println("Rolling the ball in");
		Robot.intakeSubsystem.setConveyorSpeed(CONVEYOR_SPEED);
		Robot.intakeSubsystem.setRollerSpeed(CONVEYOR_SPEED);
	}

	// Ends when it detects the ball in the compartment
	protected boolean isFinished() {
		return Robot.shootSubsystem.isBallPossessed();
	}

	protected void end() {
		Robot.intakeSubsystem.setConveyorSpeed(0);
		Robot.intakeSubsystem.setRollerSpeed(0);
	}

	protected void interrupted() {
		Robot.intakeSubsystem.setConveyorSpeed(0);
		Robot.intakeSubsystem.setRollerSpeed(0);
	}
}

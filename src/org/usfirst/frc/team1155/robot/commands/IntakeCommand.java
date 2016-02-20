package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Operatores conveyor belt and roller based on different conditions
 */
public class IntakeCommand extends Command {

	private final double CONVEYOR_SPEED = .8, ROLLER_SPEED = .7;
	
	private IntakeMode mode;

	public enum IntakeMode {
		ULTRA_BASED,
		CONTINUOUS
	}
	
	public IntakeCommand(IntakeMode mode) {
		this.mode = mode;
	}
	
	// Starts the conveyor belt in the shooter
	protected void initialize() {
		
	}

	protected void execute() {
		if(mode == IntakeMode.ULTRA_BASED) {
			Robot.intakeSubsystem.setConveyorSpeed(-CONVEYOR_SPEED);
			Robot.intakeSubsystem.setRollerSpeed(0);
		} else if (mode == IntakeMode.CONTINUOUS) {
			Robot.intakeSubsystem.setConveyorSpeed(-CONVEYOR_SPEED);
		}
	}

	// Ends when it detects the ball in the compartment
	protected boolean isFinished() {
		if (mode == IntakeMode.ULTRA_BASED && Robot.intakeSubsystem.isBallIn()) {
			Robot.intakeSubsystem.setBallInRobot(true);
			return true;
		}
		return false;
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

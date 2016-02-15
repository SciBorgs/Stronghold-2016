package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterIOCommand extends Command {

	private Mode mode;

	private final double INPUT_SPEED = -0.5, SHOOT_SPEED = 1.0;
	
	/**
	 * Sets shooter wheels in put ball in compartment for storage or to shoot ball
	 * 
	 * <ul>
	 * <li> INPUT sets wheels to store ball </li>
	 * <li> OUTPUT sets wheels to shoot ball </li>
	 * </ul>
	 *
	 */
	public enum Mode {
		INPUT, OUTPUT;
	}
	
	/**
	 * Manipulates shooter for storing ball or shooting
	 * 
	 * @param mode Determines if the shooter will take in a ball or shoot the stored ball
	 */
	public ShooterIOCommand(Mode mode) {
		requires(Robot.shootSubsystem);
		this.mode = mode;
	}

	// Moves wheels based on mode
	protected void initialize() {
		Robot.shootSubsystem.setSpeed((mode == Mode.OUTPUT) ? SHOOT_SPEED : INPUT_SPEED);
	}

	protected void execute() {
		
	}

		
	protected boolean isFinished() {
		//finishes if the ball is possessed
		if (mode == Mode.INPUT) 
			return Robot.shootSubsystem.isBallPossessed();
		//also checks if the piston is down 
		else if (mode == Mode.OUTPUT)
			return !Robot.shootSubsystem.isBallPossessed() && Robot.shootSubsystem.isPistonRetracted();
		else 
			return false;
	}

	protected void end() {
		Robot.shootSubsystem.setSpeed(0);
	}

	protected void interrupted() {
		Robot.shootSubsystem.setSpeed(0);
	}

}
	
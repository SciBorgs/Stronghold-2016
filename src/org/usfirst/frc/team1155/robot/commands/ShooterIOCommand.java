package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.commands.HolderCommand.HolderPosition;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterIOCommand extends Command {

	private final double INPUT_SPEED = -0.5, SHOOT_SPEED = 1.0;
	private Timer pistonExtendLagTime;
	
	/**
	 * Manipulates shooter for storing ball or shooting
	 * 
	 * @param mode Determines if the shooter will take in a ball or shoot the stored ball
	 */
	public ShooterIOCommand() {
		requires(Robot.shootSubsystem);
		pistonExtendLagTime = new Timer();
	}

	// Moves wheels based on mode
	protected void initialize() {
		
	}

	protected void execute() {
		if(!Robot.shootSubsystem.isPistonRetracted()) {
			Robot.shootSubsystem.retractPiston();
			Robot.intakeSubsystem.setBallInRobot(false);
			System.out.println("BOOM SHOT");
			pistonExtendLagTime.start();
		}
	}

		
	protected boolean isFinished() {
		if(pistonExtendLagTime.get() >= 3) {
			new HolderCommand(HolderPosition.OPEN).start();
			return true;//Finishes when one second has passed	
		}
		return false;
	}

	protected void end() {
		Robot.shootSubsystem.extendPiston();
		Robot.shootSubsystem.setBallPossessed(false);
		Robot.intakeSubsystem.setBallInRobot(false);
	}

	protected void interrupted() {
		Robot.shootSubsystem.extendPiston();
	}

}
	
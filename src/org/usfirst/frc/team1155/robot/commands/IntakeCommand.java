package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command {

	private static final double ROLLER_SPEED = 1;
	
	// Encoder Position. 1/2 of Max encoder rotation ticks
	private static final int POSITION_TO_ROTATE = 512;
														
	private static IntakeMode mode;
	private static Pivot pivot;

	private static int position = 0;

	/**
	 * Enum for defining the intake mode
	 * <ul>
	 * <li> PIVOT rotates arm </li> 
	 * <li> PIVOT_AND_ROLL rotates arm and also spins arm roller to take in ball </li>
	 * </ul>
	 */
	public enum IntakeMode {
		PIVOT, PIVOT_AND_ROLL;
	}

	
	/**
	 * Enum for defining how to move arm
	 * <ul>
	 * <li> UP Moves to max encoder position </li>
	 * <li> DOWN Moves to lowest encoder position </li>
	 * <li> NEUTRAL Moves to middle encoder position </li>
	 * <ul>
	 */
	public enum Pivot {
		UP, DOWN, NEUTRAL;
	}

	/**
	 * Manipulates forward arm of robot to take in ball or raise obstacles
	 * 
	 * @param mode How to move arm (Just raise arm or raise and take in ball)
	 * @param pivot Where to move arm (UP, DOWN, NEUTRAL)
	 */
	public IntakeCommand(IntakeMode mode, Pivot pivot) {
		this.mode = mode;
		this.pivot = pivot;

		switch (pivot) {
		case UP:
			position = POSITION_TO_ROTATE * 2;
			break;
		case DOWN:
			position = 0;
			break;
		case NEUTRAL:
			position = POSITION_TO_ROTATE;
			break;
		default:
			position = 0;
			break;
		}
	}

	@Override
	protected void initialize() {
		Robot.intakeSubsystem.setPivotIntake(position);
		switch (mode) {
		case PIVOT:
			//nothing
			break;
		case PIVOT_AND_ROLL:
			Robot.intakeSubsystem.setRoller(ROLLER_SPEED);
			break;
		default:
			break;
		}

	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		if (mode == IntakeMode.PIVOT) {
			return (pivot == Pivot.DOWN && Robot.intakeSubsystem.pivotTalon.get() == 0) ||
				   (pivot == Pivot.UP && Robot.intakeSubsystem.pivotTalon.get() == POSITION_TO_ROTATE * 2) ||
				   (pivot == Pivot.NEUTRAL && Robot.intakeSubsystem.pivotTalon.get() == POSITION_TO_ROTATE);
		}
		else {
			return Robot.shootSubsystem.isBallPossessed();
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command {
	// Pivot = rotate arm up/down
	// Pivot/roll = rotates arm up/down + rotates motor on arm
	private static final double ROLLER_SPEED = 1;
	private static final int POSITION_TO_ROTATE = 512; // Encoder Position.
														// Approx 1/2 of full
														// rotation
	private static IntakeMode mode;
	private static Pivot pivot;

	private static int position = 0;

	public enum IntakeMode {
		PIVOT, PIVOT_AND_ROLL;
	}

	public enum Pivot {
		UP, DOWN, NEUTRAL;
	}

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

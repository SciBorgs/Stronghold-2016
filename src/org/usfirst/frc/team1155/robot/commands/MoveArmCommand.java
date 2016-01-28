package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArmCommand extends Command {
	private static ClimbSubsystem arms = Robot.arms;
	private static Position position;
	
	// This variable is used if arm is moved manually
	private double newPosition;

	public static enum Position {
		// Bottom is arm in resting position
		BOTTOM,
		// Top is arm in position to put carabiner on bar
		TOP,

		// If arm needs to be moved by driver, these are used
		UP, DOWN;
	}

	public MoveArmCommand(Position p) {
		requires(Robot.arms);
		position = p;
	}

	@Override
	protected void initialize() {
		arms.updateArmDashboard();
	}

	@Override
	protected void execute() {
		switch (position) {
			case TOP:
				arms.rotateArmOut();
				break;
			case BOTTOM:
				arms.rotateArmIn();
				break;
			case UP:
				newPosition = 10 + arms.getArmPosition();
				arms.setArmPosition(newPosition);
				break;
			case DOWN:
				newPosition = -10 + arms.getArmPosition();
				arms.setArmPosition(newPosition);
				break;
			default:
				break;
		}
		arms.updateArmDashboard();
	}

	@Override
	protected boolean isFinished() {
		// If arm is moving up and it can't move up anymore, stop the arm
		// If arm is moving down and it can't move down anymore, stop the arm
		return (arms.cannotRotateArmOut() && (position == Position.TOP || position == Position.UP))
				|| (arms.cannotRotateArmIn() && (position == Position.BOTTOM || position == Position.DOWN));
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}

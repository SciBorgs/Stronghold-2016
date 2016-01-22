package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	private static ClimbSubsystem arms = Robot.arms;
	private static Position position;

	public static enum Position {
		// Bottom is arm in resting position
		BOTTOM,
		// Top is arm in position to put carabiner on bar
		TOP,

		// If arm needs to be moved by driver, these are used
		UP, DOWN;
	}

	public MoveArm(Position p) {
		requires(Robot.arms);
		position = p;
	}

	@Override
	protected void initialize() {
		arms.updateArmDashboard();
	}

	@Override
	protected void execute() {
		// This var is used if arm is moved manually
		double newPos;
		switch (position) {
		case TOP:
			arms.rotateArmOut();
			break;
		case BOTTOM:
			arms.rotateArmIn();
			break;
		case UP:
			newPos = 10 + arms.getArmPosition();
			arms.setArmPosition(newPos);
			break;
		case DOWN:
			newPos = -10 + arms.getArmPosition();
			arms.setArmPosition(newPos);
			break;
		default:
			break;
		}
		arms.updateArmDashboard();
	}

	@Override
	protected boolean isFinished() {
		// If arm is moving up, and it can not more anymore
		// and the position chosen was Top
		// or the arm has reached its limit from Up being called repeatedly,
		// stop command.
		// Same for arm moving down
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

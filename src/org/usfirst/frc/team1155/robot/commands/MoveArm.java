package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	private static ClimbSubsystem arms = Robot.arms;
	private static Position position;
	
	
	public MoveArm(Position p) {
		position = p;
	}
	
	public static enum Position {	
		// Bottom is arm in resting position
		BOTTOM,
		// Top is arm in position to put carabiner on bar
		TOP,
		
		// If arm needs to be moved by driver, these are used
		UP,
		DOWN;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		//This var is used if arm is moved manually
		double newPos;
		
		switch (position) {
		case TOP:
			arms.extendArm();
			break;
		case BOTTOM:
			arms.retractArm();
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
	}

	@Override
	protected boolean isFinished() {
		// If arm is moving up, and it can not more anymore 
		// and the position chosen was Top 
		// or the arm has reached its limit from Up being called repeatedly, stop command.
		//Same for arm moving down
		return (arms.cannotMoveArmUp() && (position == Position.TOP || position == Position.UP)) ||
			   (arms.cannotMoveArmDown() && (position == Position.BOTTOM || position == Position.DOWN));
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}

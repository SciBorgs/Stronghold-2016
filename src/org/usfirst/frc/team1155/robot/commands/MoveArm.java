package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	private static ClimbSubsystem arms = Robot.arms;
	private static Position position;
	
	public static enum Position {
		BOTTOM (0),
		TOP (1023),
		UP (10),
		DOWN (-10);
		
		private final double position;
		
		Position(double position) {
			this.position = position;
		}
		
		public double getPosition() {
			return position;
		}
	}
	
	
	public MoveArm(Position p) {
		position = p;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		switch (position) {
		case TOP:
			arms.extendArm();
			break;
		case BOTTOM:
			arms.retractArm();
			break;
		default:
			double newPos = position.getPosition() + arms.getArmPosition();
			arms.setArmPosition(newPos);
			break;
		}
	}

	@Override
	protected boolean isFinished() {
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

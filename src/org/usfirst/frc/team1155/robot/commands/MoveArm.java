package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	private static ClimbSubsystem arms = Robot.arms;
	private static double position;
	
	public enum Position {
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
		position = p.getPosition();
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		if (position == Position.TOP.getPosition())
			arms.extendArm();
		else if (position == Position.BOTTOM.getPosition())
			arms.retractArm();
		else {
			double newPos = position + arms.getArmPosition();
			arms.setArmPosition(newPos);
		}
	}

	@Override
	protected boolean isFinished() {
		return (arms.cannotMoveArmUp() && position >= Position.BOTTOM.getPosition())
				|| (arms.cannotMoveArmDown() && position <= Position.BOTTOM.getPosition());
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
		
	}

}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	private ArmSubsystem arms = Robot.arms;
	private double speed;
	
	public enum Direction {
		UP_HALF_SPEED (.5),
		UP_FULL_SPEED (1),
		DOWN_HALF_SPEED (-.5),
		DOWN_FULL_SPEED(-1);
		
		private double speed;
		
		Direction(double speed) {
			this.speed = speed;
		}
		
		public double getSpeed() {
			return speed;
		}
	}
	
	
	public MoveArm(Direction d) {
		speed = d.getSpeed();
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		arms.setSpeed(speed);
	}

	@Override
	protected boolean isFinished() {
		return arms.cannotMove();
	}

	@Override
	protected void end() {
		ArmSubsystem.setStill();
	}

	@Override
	protected void interrupted() {
		end();
		
	}

}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class WinchCommand extends Command {

	private static ClimbSubsystem arms = Robot.arms;
	private Direction direction;

	// If you want the robot to be reeled up to the bar, use UP
	// If you want the robot to drop down to floor, use DOWN
	public static enum Direction {
		UP, DOWN;
	}

	public WinchCommand(Direction d) {
		requires(Robot.arms);
		direction = d;
	}

	@Override
	protected void initialize() {
		arms.updateWinchDashboard();
	}

	@Override
	protected void execute() {
		switch (direction) {
			case UP:
				arms.extendWinch();
				break;
			case DOWN:
				arms.retractWinch();
				break;
		}
		arms.updateWinchDashboard();
	}

	@Override
	protected boolean isFinished() {
		// if the arm is going up and it can't go up anymore, stop the arm
		// if the arm is going down and it can't go down anymore, stop the arm
		return (direction == Direction.UP && arms.cannotExtendWinch() || direction == Direction.DOWN
				&& arms.cannotRetractWinch());
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {

	}

}

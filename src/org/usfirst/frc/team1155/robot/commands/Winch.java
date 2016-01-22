package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class Winch extends Command {

	private static ClimbSubsystem arms = Robot.arms;
	private Direction dir;

	// If you want the robot to be reeled up to the bar, use Up
	// If you want the robot to drop down to floor, use Down
	public static enum Direction {
		UP, DOWN;
	}

	public Winch(Direction d) {
		requires(Robot.arms);
		dir = d;
	}

	@Override
	protected void initialize() {
		arms.updateWinchDashboard();
	}

	@Override
	protected void execute() {
		switch (dir) {
		case UP:
			arms.extendWinch();
			isFinished();
			break;
		case DOWN:
			arms.retractWinch();
			isFinished();
			break;
		}
		arms.updateWinchDashboard();
	}

	@Override
	protected boolean isFinished() {
		return (dir == Direction.UP && arms.cannotExtendWinch() || dir == Direction.DOWN
				&& arms.cannotRetractWinch());
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {

	}

}

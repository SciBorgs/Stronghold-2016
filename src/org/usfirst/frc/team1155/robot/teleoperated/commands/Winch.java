package org.usfirst.frc.team1155.robot.teleoperated.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class Winch extends Command {

	private static ClimbSubsystem arms = Robot.arms;
	private Direction dir;

	public Winch(Direction d) {
		dir = d;
	}
	
	// If you want the robot to be reeled up to the bar, use Up
	// If you want the robot to drop down to floor, use Down
	public static enum Direction {
		UP, DOWN;
	}

	@Override
	protected void initialize() {
		requires(Robot.arms);
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
	}

	@Override
	protected boolean isFinished() {
		return (dir == Direction.UP && arms.cannotMoveWinchUp() ||
				dir == Direction.DOWN && arms.cannotMoveWinchDown());
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}

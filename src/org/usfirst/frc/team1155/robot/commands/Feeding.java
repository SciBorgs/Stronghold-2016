package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

public class Feeding extends Command {

	private static Feeder feeder = Robot.feeder;

	public Feeding() {
		requires(Robot.feeder);
	}

	@Override
	protected void initialize() {
		feeder.updateFeederDashboard();
	}

	@Override
	protected void execute() {
		// Starts conveyor to move ball up to shooter
		feeder.feed();
		feeder.updateFeederDashboard();
		
	}

	@Override
	protected boolean isFinished() {
		return feeder.isFed();
	}

	@Override
	protected void end() {
		feeder.stopFeeding();
	}

	@Override
	protected void interrupted() {
	}

}

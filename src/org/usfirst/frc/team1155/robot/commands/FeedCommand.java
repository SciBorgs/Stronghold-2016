package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.FeederSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class FeedCommand extends Command {

	private static FeederSubsystem feeder = Robot.feeder;

	public FeedCommand() {
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

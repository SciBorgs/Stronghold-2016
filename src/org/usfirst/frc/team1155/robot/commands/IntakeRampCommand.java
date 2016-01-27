package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.FeederSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeRampCommand extends Command {

	private static FeederSubsystem feeder = Robot.feeder;

	public IntakeRampCommand() {
		requires(Robot.feeder);
	}

	@Override
	protected void initialize() {
		feeder.updateRampDashboard();
	}

	@Override
	protected void execute() {
		// Toggles ramp at bottom of feeder to "pop" ball into feeder system
		feeder.togglePiston();
		feeder.updateRampDashboard();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {
	}

}
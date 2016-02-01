package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.FeederSubsystem;

import edu.wpi.first.wpilibj.command.Command;

//MAY BE DEPRECATED. AFFIRM WITH ARUN REGARDING INTAKE ARM COMMANDS
public class IntakeRampCommand extends Command {

	private static FeederSubsystem feeder = Robot.feeder;

	public IntakeRampCommand() {
		requires(Robot.feeder);
	}

	@Override
	protected void initialize() {
		feeder.updateFeederDashboard();
	}

	@Override
	protected void execute() {
		// Toggles ramp at bottom of feeder to "pop" ball into feeder system
		feeder.updateFeederDashboard();
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
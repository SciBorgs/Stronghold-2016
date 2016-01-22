package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeRamp extends Command {

	private static Feeder feeder = Robot.feeder;

	public IntakeRamp() {
		requires(Robot.feeder);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		// Toggles ramp at bottom of feeder to "pop" ball into feeder system
		feeder.togglePiston();
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
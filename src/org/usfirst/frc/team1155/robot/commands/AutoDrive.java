package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command {
	// Not finished. Need routines
	private Drive drive = Robot.drive;

	@Override
	protected void initialize() {
		requires(drive);
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {

		return false;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}

}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ColorDriveCommand extends Command {
	private int colorToCheckFor;
	private static final double DRIVE_SPEED = 0.8;

	DriveSubsystem drive = Robot.drive;

	public ColorDriveCommand(int color) {
		requires(Robot.drive);
		colorToCheckFor = color;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		drive.setSpeed(DRIVE_SPEED, DRIVE_SPEED);
		if (drive.isInThreshold())
			drive.setSpeed(0, 0);

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}

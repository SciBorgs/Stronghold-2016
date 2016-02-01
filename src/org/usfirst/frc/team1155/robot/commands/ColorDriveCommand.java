package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/*

 TO BE FIXED AFTER CAMERA FIXED

*/

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

	}

	@Override
	protected void execute() {
		drive.setSpeed(DRIVE_SPEED, DRIVE_SPEED);
		if (drive.isInThreshold())
			drive.setSpeed(0, 0);

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

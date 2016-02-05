package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoDriveCommand extends Command {
	private static DriveSubsystem drive = Robot.drive;
	// In feet, distance from midline to beyond outerworks
	private static double distanceToDrive, distanceDriven;
	private static boolean isFinished;

	// Speed to not overshoot. Inverse curve
	private static double driveSpeed = 1 / (distanceToDrive - drive.getDistanceDriven());

	public AutoDriveCommand(double distance) {
		requires(Robot.drive);
		distanceToDrive = distance;
	}

	@Override
	protected void initialize() {
		drive.resetTimer();
		drive.startTimer();
		isFinished = false;
	}

	@Override
	protected void execute() {
		// If the robot hasn't reached the target distance yet, keep driving
		// If it has reached the target distance, stop the robot
		distanceDriven += drive.getDistanceDriven();
		if (distanceDriven < distanceToDrive) {
			drive.setSpeed(driveSpeed, driveSpeed);
		} else {
			isFinished = true;
		}
	}

	@Override
	protected boolean isFinished() {
		// Set to true in execute
		return isFinished;
	}

	@Override
	protected void end() {
		// Stops the robot and stops and resets timer
		drive.stopTimer();
		drive.resetTimer();
		drive.stopMoving();
	}

	@Override
	protected void interrupted() {
		end();
	}

}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoDriveCommand extends Command {
	private static DriveSubsystem drive = Robot.drive;
	private static double distanceToDrive; // in feet, distance from midline to
										   // past outerworks
	private static boolean isFinished;

	private static double driveSpeed = 1/(distanceToDrive - drive.getDistanceDriven()); //speed to not overshoot
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
		if (drive.getDistanceDriven() < distanceToDrive) {
			drive.setSpeed(driveSpeed, driveSpeed);
		} else {
			isFinished = true;
		}
	}

	@Override
	protected boolean isFinished() {
		return isFinished; // Set to true in execute
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

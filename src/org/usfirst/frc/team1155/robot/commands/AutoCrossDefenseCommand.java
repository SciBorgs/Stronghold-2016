package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoCrossDefenseCommand extends Command {
	// Not finished. Need routines
	private static DriveSubsystem drive = Robot.drive;
	private static double distanceToDrive; // in feet, distance from midline to
											// past outerworks
	private static boolean isAtDefense, isFinished;

	private static final double INITIAL_DRIVE_SPEED = .8; // set full speed for Talons
	private static final double DEFENSE_SPEED = 1;
	private static final long TIMER_SLEEPER = 3; //in seconds; the amount of time elapsed until gyro is considered stable

	public AutoCrossDefenseCommand(double distance) {
		requires(Robot.drive);
		distanceToDrive = distance;
	}

	@Override
	protected void initialize() {
		drive.resetGyro();
		// drive.resetEncoder();
		drive.resetTimer();
		drive.startTimer();
		
		drive.resetGyro();
		
		isAtDefense = false;
		isFinished = false;
	}

	@Override
	protected void execute() {
		// If robot is not there yet, set talons to initial drive speed
		if (drive.getDistanceDriven() < distanceToDrive)
			drive.setSpeed(INITIAL_DRIVE_SPEED, INITIAL_DRIVE_SPEED);
		
		//If robot is there, sets talons to defense speed and set isAtDefense to true
		if (drive.getDistanceDriven() == distanceToDrive) {
			drive.setSpeed(DEFENSE_SPEED, DEFENSE_SPEED);
			isAtDefense = true;
		}
		
		//If robit is at defense, it has been for seconds, and gyro is stable, set talons to defense speed and set isFinished variable to true, or else set speed to 0
		if (isAtDefense) {
			if (drive.timerBuffer(4) && drive.isGyroStable()) {
				drive.setSpeed(DEFENSE_SPEED, DEFENSE_SPEED);
				isFinished = true;
			}
			else {
				drive.setSpeed(0, 0);
			}
		}

	}

	@Override
	protected boolean isFinished() {
		return isFinished; //Set to true in execute when at defense
	}

	@Override
	protected void end() {
		drive.resetGyro();
		drive.stopTimer();
		drive.resetTimer();
		drive.stopMoving();
	}

	@Override
	protected void interrupted() {
		end();
	}

}

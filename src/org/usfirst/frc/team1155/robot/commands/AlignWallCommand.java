package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignWallCommand extends Command {

	// Difference between sensor values to be properly aligned
	// Error margin in degrees
	private static final double ERROR_MARGIN = 30;
	private static final double TALON_SPEED = 0.2;

	private static DriveSubsystem drive = Robot.drive;

	public AlignWallCommand() {
		requires(Robot.drive);
		
	}

	protected void initialize() {
		drive.updateDriveDashboard();
		drive.updateUltrasonicsDashboard();
	}

	protected void execute() {
		// When the right ultrasonic exceeds left ultrasonic by the margin of error
		if (drive.angleToAlignTo() < -ERROR_MARGIN) 
			drive.setSpeed(TALON_SPEED, -TALON_SPEED);

		// When the left ultrasonic exceeds right ultrasonic by the margin of error
		else if (drive.angleToAlignTo() > ERROR_MARGIN)
			drive.setSpeed(-TALON_SPEED, TALON_SPEED);
		
		drive.updateDriveDashboard();
		drive.updateUltrasonicsDashboard();
	}

	protected boolean isFinished() {
		// When sensors are almost the same distance away
		// Or the sensors do not return a value. (Object is too far away)
		return (drive.angleToAlignTo() < ERROR_MARGIN &&
				drive.angleToAlignTo() > -ERROR_MARGIN);
	}

	protected void end() {
	}

	protected void interrupted() {

	}
}
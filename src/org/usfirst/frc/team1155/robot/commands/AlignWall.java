package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignWall extends Command {

	// Difference between sensor values to be properly aligned
	// Error margin in degrees
	private static final double ERROR_MARGIN = 30;
	private static final double TALON_SPEED = 0.2;

	private static Drive drive = Robot.drive;

	public AlignWall() {
		requires(Robot.drive);
	}

	protected void initialize() {

	}

	protected void execute() {
		// When the right ultrasonic exceeds left ultrasonic by a significant margin
		if (drive.angleToAlignTo() < -ERROR_MARGIN) 
			drive.setSpeed(TALON_SPEED, -TALON_SPEED);

		// When the left ultrasonic exceeds right ultrasonic by a significant margin
		else if (drive.angleToAlignTo() > ERROR_MARGIN)
			drive.setSpeed(-TALON_SPEED, TALON_SPEED);
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
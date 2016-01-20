package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignWall extends Command {
	private double angle;

	// Difference in sensor distance can be up to this value to proceed.
	private static final double ERROR_MARGIN = 30;
	private static final double TALON_SPEED = 0.2;

	private static Drive drive;

	public AlignWall() {
		requires(Robot.drive);
		drive = Robot.drive;
		angle = 0;
	}

	protected void initialize() {

	}

	protected void execute() {
		// When the right ultrasonic is in the front
		if (drive.angleToAlignTo() < -ERROR_MARGIN) {
			Robot.drive.setSpeed(TALON_SPEED, -TALON_SPEED);
		}

		// When the left ultrasonic is in the front.
		else if (drive.angleToAlignTo() > ERROR_MARGIN)
			Robot.drive.setSpeed(-TALON_SPEED, TALON_SPEED);
	}

	protected boolean isFinished() {
		// When sensors are almost the same distance away,
		// or the sensors cannot compute
		return (drive.angleToAlignTo() < ERROR_MARGIN ||
				drive.angleToAlignTo() > -ERROR_MARGIN);
	}

	protected void end() {
		// Stops
	}

	protected void interrupted() {

	}
}
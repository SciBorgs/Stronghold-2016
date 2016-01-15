package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignWall extends Command {
	double angle, errorMargin, talonSpeed;
	public DriveSystem driveSystem = new DriveSystem();

	public AlignWall() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		errorMargin = 30; // Difference in sensor distance can be up to this
							// value to proceed.
		talonSpeed = 0.2;

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		angle = driveSystem.ultraSonicAngle();
		if (angle > errorMargin) { // When the right US is in the front
			Robot.drive.setSpeed(talonSpeed, -talonSpeed);
		} else if (angle < -errorMargin) { // When the left US is in the front.
			Robot.drive.setSpeed(-talonSpeed, talonSpeed);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (angle < errorMargin && angle > -errorMargin); // When sensors are almost the same distance away, or the sensors cannot compute.
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.setSpeed(0, 0); //Stops
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
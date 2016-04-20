package org.usfirst.frc.team1155.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import java.util.TimerTask;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.commands.AutonomousCommand.Position;

/**
 *
 */
public class RotateToTape extends Command {
	private boolean turnLeft, timerStart;
	private final double SPEED = .5;
	private Timer timer;
	
	public RotateToTape(Position p) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		switch (p) {
		case SLOT_1:
		case SLOT_2:
			turnLeft = false;
			break;
		case SLOT_3:
		case SLOT_4:
			turnLeft = true;
			break;
		default:
			turnLeft = true;
			break;
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer = new Timer();
		timerStart = false;
		if (turnLeft)
			Robot.driveSubsystem.setSpeed(-SPEED, SPEED);
		else
			Robot.driveSubsystem.setSpeed(SPEED, -SPEED);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Robot.imageSubsystem.isTargetTape() && timerStart == false) {
			timerStart = true;
			timer.start();
		}
		return timer.get() > .1;
	}

	// Called once after isFinished returns true
	protected void end() {
		
		Robot.driveSubsystem.setSpeed(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PivotCommand extends Command {

	private double speed, time;
	private Timer timer;
		
    public PivotCommand(double time, double speed) {
        requires(Robot.intakeSubsystem);
        
        timer = new Timer();
        this.speed = speed;
        this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intakeSubsystem.setPivotIntakePosition(speed);
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeSubsystem.setPivotIntakePosition(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

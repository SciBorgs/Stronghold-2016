package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PivotCommand extends Command {

	private double position;
	
	private final double BUFFER = 10;
	
    public PivotCommand(double position) {
        requires(Robot.intakeSubsystem);
        
        this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intakeSubsystem.setPivotIntakePosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.intakeSubsystem.getPivotIntakePosition() - BUFFER) <= position;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConveyorBeltCommand extends Command {
	
	private final double CONVEYOR_SPEED = .5;
	
    public ConveyorBeltCommand() {
        
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	requires(Robot.conveyorSubsystem);
    	Robot.conveyorSubsystem.setSpeed(CONVEYOR_SPEED);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.shootSubsystem.isBallPossessed();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.conveyorSubsystem.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.conveyorSubsystem.setSpeed(0);
    }
}

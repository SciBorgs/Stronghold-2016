package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConveyorBeltCommand extends Command {
	
	private final double CONVEYOR_SPEED = .5;
	
	//og constructor
    public ConveyorBeltCommand() {
        
        
    }
    
    //Starts the conveyor belt in the shooter
    protected void initialize() {
    	requires(Robot.conveyorSubsystem);
    	Robot.conveyorSubsystem.setSpeed(CONVEYOR_SPEED);
    }

   
    protected void execute() {
    }

    //ends when it detects the ball in the compartment
    protected boolean isFinished() {
        return Robot.shootSubsystem.isBallPossessed();
    }

    protected void end() {
    	Robot.conveyorSubsystem.setSpeed(0);
    }

    protected void interrupted() {
    	Robot.conveyorSubsystem.setSpeed(0);
    }
}

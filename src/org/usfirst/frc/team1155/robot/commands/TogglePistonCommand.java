package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TogglePistonCommand extends Command {

	private boolean isFinished;
	private PistonMode mode;
	
	public enum PistonMode {
		EXTEND,
		RETRACT;
	}
	
    public TogglePistonCommand(PistonMode mode) {
        requires(Robot.shootSubsystem);
        
		this.mode = mode;
        isFinished = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch(mode) {
    	case EXTEND:	
    		Robot.shootSubsystem.extendPiston();
    		break;
    	case RETRACT:
    		Robot.shootSubsystem.retractPiston();
    		break;
    	}
    	
    	isFinished = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shootSubsystem.turnOffPiston();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

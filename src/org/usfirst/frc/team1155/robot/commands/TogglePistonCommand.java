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
    
    //extends or retracts piston
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

    protected void execute() {
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void end() {
    	Robot.shootSubsystem.turnOffPiston();
    }

    protected void interrupted() {
    }
}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TogglePistonCommand extends Command {

	private boolean isFinished;
	private PistonMode mode;
	
	/**
	 * Controls piston movement
	 * <ul>
	 * <li> EXTEND extends piston </li>
	 * <li> RETRACT retracts piston </li>
	 * <ul>
	 *
	 */
	public enum PistonMode {
		EXTEND,
		RETRACT;
	}
	
	/**
	 * Manipulates piston in ball storage compartment
	 * 
	 * @param mode Determines if piston will retract or extend
	 */
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

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeRampCommand extends Command {

	private static Direction direction;
	
	private static final double DELTA_ENCODER_POSITION = 10;
	
	public enum Direction {
		UP, DOWN;
	}
	
    public IntakeRampCommand(Direction direction) {
        this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	requires(Robot.intakeSubsystem);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(direction) {
    	case UP:
    		Robot.intakeSubsystem.setPivotTalon(DELTA_ENCODER_POSITION);
    		break;
    	case DOWN:
    		Robot.intakeSubsystem.setPivotTalon(-DELTA_ENCODER_POSITION);
    		break;
    	default:
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (direction == Direction.DOWN && !Robot.intakeSubsystem.canPivotDown()) ||
        	   (direction == Direction.UP && !Robot.intakeSubsystem.canPivotUp());
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeSubsystem.setPivotTalon(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }
}

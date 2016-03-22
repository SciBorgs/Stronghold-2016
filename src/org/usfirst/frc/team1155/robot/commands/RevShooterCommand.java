package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.commands.AutonomousCommand.Defense;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RevShooterCommand extends Command {
	
	private Defense defense;
	private Timer timer;
	
    public RevShooterCommand(Defense defense) {
    	requires(Robot.shootSubsystem);
    	this.defense = defense;
    	timer = new Timer();
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shootSubsystem.setShooterSpeed(-.55);
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 4;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(defense == Defense.ROCK_WALL  || defense == Defense.ROUGH_TERRAIN || defense == Defense.MOAT)
    	new ShooterIOCommand().start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

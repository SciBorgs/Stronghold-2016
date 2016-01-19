package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;
import org.usfirst.frc.team1155.robot.subsystems.UltrasonicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollisionDetection extends Command {
	// store range from ultrasonic sensor (in inches)
	private static double range; 
	// when the ultrasonic sensor returns this range, stop the robot will stop when within minRange + or - buffer
	private static final double MIN_RANGE = .05, BUFFER = 1;
	private static UltrasonicSubsystem ultrasonics = Robot.ultrasonics;
	private static Drive drive = Robot.drive;
	
    public CollisionDetection() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ultrasonics);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	// activate the ultrasonic sensors
    	UltrasonicSubsystem.leftUltrasonic.setAutomaticMode(true);
    	UltrasonicSubsystem.rightUltrasonic.setAutomaticMode(true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	range = ultrasonics.findClosestRange();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (BUFFER > Math.abs(range - MIN_RANGE));
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.setSpeed(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

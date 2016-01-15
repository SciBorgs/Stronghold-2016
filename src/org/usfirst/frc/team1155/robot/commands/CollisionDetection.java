package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.TalonSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.UltrasonicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollisionDetection extends Command {
	double range; // store range from ultrasonic sensor (in inches)
	private static final double MIN_RANGE = .05, BUFFER = 1; // when the ultrasonic sensor returns this range, stop the robot
	                                                         // will stop when within minRange + or - buffer

	
	private static UltrasonicSubsystem ultrasonicSubsystem = Robot.ultrasonicSubsystem;
	private static TalonSubsystem talonSubsystem = Robot.talonSubsystem;
	
    public CollisionDetection() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ultrasonicSubsystem);
        requires(Robot.talonSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	// activate the ultrasonic sensors
    	UltrasonicSubsystem.leftUltrasonic.setAutomaticMode(true);
    	UltrasonicSubsystem.rightUltrasonic.setAutomaticMode(true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	range = ultrasonicSubsystem.findClosestRange();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (BUFFER > Math.abs(range - MIN_RANGE));
    }

    // Called once after isFinished returns true
    protected void end() {
    	TalonSubsystem.talonWheelLeft.set(0);
    	TalonSubsystem.talonWheelRight.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

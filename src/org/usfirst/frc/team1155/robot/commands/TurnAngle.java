package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;
import org.usfirst.frc.team1155.robot.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Command;
/*
 * This is the Command used to do a 180 degree turn using Gyro
 * Written by Ankith
 * */
public class TurnAngle extends Command {
	
	private Drive drive = Robot.drive;	
	private static double angle;
	private static double currentAngle; //Required vars
	private static boolean finishedTurning;
	private static GyroSubsystem gyro = Robot.gyros;
	
	//Enum stuff for angle measures
	public enum Turn {
		RIGHT_ANGLE (90),
		FORTYFIVE_ANGLE (45),
		STRAIGHT_ANGLE (180),
		THREEQUARTER_ANGLE (270);
		
		private final double angle;
		
		//Sets angle from OI
		Turn(double angle) {
			this.angle = angle;
		}
		
		public double getAngle() {
			return angle;
		}
	}
	
	//Parameter is from preset values in Enum
	public TurnAngle(Turn angleChoice){
		requires(Robot.drive);
		angle = angleChoice.getAngle();
	}
	
	//Parameter is any angle wanted
	public TurnAngle(double a){
		requires(Robot.drive);
		angle = a;
	}
	
	@Override
	protected void initialize() {
		//Gets original gyro value so we don't have to reset and meddle
		gyro.resetGyro();
		currentAngle = gyro.getAngle();
	}

	@Override
	protected void execute() {
		currentAngle = gyro.getAngle();
		//gets the current gyro value while turning
		finishedTurning = gyro.turn(angle, currentAngle);
		//DRIVE THE FUCKING ROBOT HERE 
		
	}

	@Override
	protected boolean isFinished() {
		return finishedTurning;//end command if 180 turn is finished
	}

	@Override
	protected void end() {
		drive.setSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
		end();

	}

}

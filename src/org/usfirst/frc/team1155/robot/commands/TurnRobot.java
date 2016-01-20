package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Command;
/*
 * This is the Command used to do a 180 degree turn using Gyro
 * Written by Ankith
 * */
public class TurnRobot extends Command {
	
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
	
	//Field variables
	private Drive drive = Robot.drive;
	

	
	private double angle;
	private double originalAngle; //Required vars


	//Parameter is from preset values in Enum
	public TurnRobot(Turn angleChoice){
		requires(Robot.drive);
		angle = angleChoice.getAngle();
	}
	
	//Parameter is any angle wanted
	public TurnRobot(double a){
		requires(Robot.drive);
		angle = a;
	}
	
	@Override
	protected void initialize() {
		//Gets original gyro value so we don't have to reset and meddle
		originalAngle = drive.getAngle();	
	}

	@Override
	protected void execute() {
		drive.turnRobot(angle);
	}

	@Override
	protected boolean isFinished() {
		return drive.canTurn(angle, originalAngle);//end command if 180 turn is finished
	}

	@Override
	protected void end() {
		drive.stopMoving();

	}

	@Override
	protected void interrupted() {
		end();

	}

}

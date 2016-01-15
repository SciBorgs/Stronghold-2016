package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.UltraSubSystem;

import edu.wpi.first.wpilibj.command.Command;
/*
 * This is the Command used to do a 180 degree turn using Gyro
 * Written by Ankith
 * */
public class TurnAngle extends Command {
	public enum Turn {//Enum stuff for angle measures
		RIGHT_ANGLE (90),
		FORTYFIVE_ANGLE (45),
		THREEQUARTER_ANGLE (270),
		STRAIGHT_ANGLE (180);
		
		private final double angle;
		Turn(double angle) {
			this.angle = angle;
		}
		
		public double getAngle() {
			return angle;
		}
	}
	
	private double angle;
	private double gyroAngle, originalAngle;//Required vars
	private boolean finished;
	private Ultrasonics ultra = Robot.ultrasub;

	public TurnAngle(TurnAngle.Turn angleChoice){//given an enum value
		requires(Robot.ultrasub);
		angle = angleChoice.getAngle();
	}
	
	public TurnAngle(double a){//given an ACTUAL value
		requires(Robot.ultrasub);
		angle = a;
	}
	
	@Override
	protected void initialize() {
		originalAngle = ultra.gyro.getAngle();//Gets original gyro value so we don't have to reset and meddle
		
	}

	@Override
	protected void execute() {
		gyroAngle = ultra.gyro.getAngle();//gets the current gyro value while turning
		finished = ultra.turnAngle(angle, originalAngle, gyroAngle);
	}

	@Override
	protected boolean isFinished() {
			
		return finished;//end command if 180 turn is finished
	}

	@Override
	protected void end() {
		ultra.setSpeed(0, 0);

	}

	@Override
	protected void interrupted() {
		end();

	}

}

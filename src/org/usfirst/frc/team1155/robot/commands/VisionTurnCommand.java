package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionTurnCommand extends Command {

	private double angleToTurn;
	private double turnSpeed = 0;

	private final double ANGLE_TURN_SPEED = 0.35;
	private final double ANGLE_BUFFER = 5;

	/**
	 * Autonomous Command
	 * <br><br>
	 * Command for turning robot a certain angle based on changes in the images taken by the camera
	 * 
	 * @param angle Angle to turn
	 */
	public VisionTurnCommand() {
		requires(Robot.driveSubsystem);
	}
	//Vision must be running parallel 
	@Override
	protected void initialize() {	
		//System.out.println("Running Vision Turn");
		if(!Robot.imageSubsystem.isTargetTape()) {
			//System.out.println("No Tape");
			end();
			return;
		}
		angleToTurn = Robot.targetVector.theta;
	}

	@Override
	protected void execute() {
		if(!Robot.imageSubsystem.isTargetTape()) {
			//System.out.println("No Tape");
			end();
			return;
		}
		angleToTurn = Robot.targetVector.theta;
		//SmartDashboard.putNumber("Angle To Turn" , angleToTurn);
		
		turnSpeed = .5;  //Determined using desmos grapher
		//double turnSpeed = (angleToTurn > 0) ? ANGLE_TURN_SPEED : -ANGLE_TURN_SPEED;
		if (angleToTurn > 0) {
			Robot.driveSubsystem.setSpeed(turnSpeed, -turnSpeed);
		}
		else if (angleToTurn < 0) {
			Robot.driveSubsystem.setSpeed(-turnSpeed, turnSpeed);
		}
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(angleToTurn) <= ANGLE_BUFFER || angleToTurn == 0;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
		end();

	}
}

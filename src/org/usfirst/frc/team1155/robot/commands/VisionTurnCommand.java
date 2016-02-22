package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionTurnCommand extends Command {

	private double angleToTurn;

	private final double ANGLE_TURN_SPEED = 0.2;
	private final double ANGLE_BUFFER = 3;

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
		System.out.println("Runing Vision Turn");
		if(!Robot.imageSubsystem.isTargetTape()) {
			System.out.println("No Tape");
			end();
			return;
		}
		angleToTurn = Robot.targetVector.theta;

		//double turnSpeed = (angleToTurn > 0) ? ANGLE_TURN_SPEED : -ANGLE_TURN_SPEED;
		if (angleToTurn > 0) {
			Robot.driveSubsystem.setSpeed(ANGLE_TURN_SPEED, -ANGLE_TURN_SPEED);
		}
		else if (angleToTurn < 0) {
			Robot.driveSubsystem.setSpeed(-ANGLE_TURN_SPEED, ANGLE_TURN_SPEED);
		}
	}

	@Override
	protected void execute() {
		angleToTurn = Robot.targetVector.theta;
		SmartDashboard.putNumber("Angle To Turn" , angleToTurn);
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

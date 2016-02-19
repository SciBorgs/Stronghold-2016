package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionTurnDriveCommand extends Command {

	private double angleToTurn;

	private final double ANGLE_TURN_SPEED = 0.1;
	private final double ANGLE_BUFFER = 1;

	/**
	 * Autonomous Command
	 * <br><br>
	 * Command for turning robot a certain angle based on changes in the images taken by the camera
	 * 
	 * @param angle Angle to turn
	 */
	public VisionTurnDriveCommand() {
		requires(Robot.driveSubsystem);
	}
	//Vision must be running parallel 
	@Override
	protected void initialize() {	
		angleToTurn = Robot.targetVector.theta;

		//double turnSpeed = (angleToTurn > 0) ? ANGLE_TURN_SPEED : -ANGLE_TURN_SPEED;
		if (angleToTurn > 0) {
			Robot.driveSubsystem.setSpeed(-ANGLE_TURN_SPEED, ANGLE_TURN_SPEED);
		}
		else if (angleToTurn < 0) {
			Robot.driveSubsystem.setSpeed(ANGLE_TURN_SPEED, -ANGLE_TURN_SPEED);
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

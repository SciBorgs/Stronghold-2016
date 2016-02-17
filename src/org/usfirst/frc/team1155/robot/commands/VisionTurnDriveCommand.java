package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionTurnDriveCommand extends Command {

	private double angleToTurn;

	private final double ANGLE_TURN_SPEED = 0.5;
	private final double ANGLE_BUFFER = .1;

	/**
	 * Autonomous Command
	 * <br><br>
	 * Command for turning robot a certain angle based on changes in the images taken by the camera
	 * 
	 * @param angle Angle to turn
	 */
	public VisionTurnDriveCommand(double angle) {
		requires(Robot.driveSubsystem);
		angleToTurn = angle;
	}
	//Vision must be running parallel 
	@Override
	protected void initialize() {double turnSpeed = (angleToTurn > 0) ? -ANGLE_TURN_SPEED : ANGLE_TURN_SPEED;
		Robot.driveSubsystem.setSpeed(turnSpeed, -turnSpeed);
		
		angleToTurn = Robot.targetVector.theta;
	}

	@Override
	protected void execute() {
		angleToTurn = Robot.targetVector.theta;
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(angleToTurn) <= ANGLE_BUFFER;
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

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RotateCommand extends Command {

	RobotPosition rotate;

	/**
	 * Angle to turn robot to face window on tower
	 * <ul>
	 * <li> SLOT_1: Robot needs to rotate 45 degrees to face closest window </li>
	 * <li> SLOT_2: Robot needs to rotate 20 degrees to face closest window </li>
	 * <li> SLOT_3: Robot needs to rotate -10 degrees to face closest window </li>
	 * <li> SLOT_4: Robot needs to rotate -45 degrees to face closest window </li>
	 * </ul>
	 *
	 */
	public enum RobotPosition {
		// Degrees
		SLOT_1(45), SLOT_2(20), SLOT_3(-10), SLOT_4(-45);

		private final double angle;

		RobotPosition(double angle) {
			this.angle = angle;
		}

		public double getAngle() {
			return angle;
		}
	}

	private static final int BUFFER = 5;

	/**
	 * Autonomous Command
	 * <br>
	 * Rotates robot to face closest tower window without camera guidance
	 * 
	 * @param position Position of robot at start of autonomous 
	 */
	public RotateCommand(RobotPosition position) {
		Robot.driveSubsystem.driveGyro.reset();
		this.rotate = rotate;
	}

	@Override
	protected void initialize() {
		switch (rotate) {
		case SLOT_1:
			Robot.driveSubsystem.setSpeed(0, 0.5);
			break;
		case SLOT_2:
			Robot.driveSubsystem.setSpeed(0, 0.5);
			break;
		case SLOT_3:
			Robot.driveSubsystem.setSpeed(0.5, 0);
			break;
		case SLOT_4:
			Robot.driveSubsystem.setSpeed(0.5, 0);
			break;
		default:
			Robot.driveSubsystem.setSpeed(0, 0);

		}

	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return (Robot.driveSubsystem.driveGyro.getAngle() + BUFFER >= rotate.getAngle()) || 
			   (Robot.driveSubsystem.driveGyro.getAngle() - BUFFER <= rotate.getAngle());
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

}

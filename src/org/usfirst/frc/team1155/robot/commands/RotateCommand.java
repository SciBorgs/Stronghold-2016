package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RotateCommand extends Command {

	Rotate rotate;

	public enum Rotate {
		// Degrees
		SLOT_1(45), SLOT_2(20), SLOT_3(-10), SLOT_4(-45);

		private final double angle;

		Rotate(double angle) {
			this.angle = angle;
		}

		public double getAngle() {
			return angle;
		}
	}

	private static final int BUFFER = 5;

	public RotateCommand(Rotate rotate) {
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

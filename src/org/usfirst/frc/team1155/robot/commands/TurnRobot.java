package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class TurnRobot extends Command {

	private Drive drive = Robot.drive;

	private double angle;
	private double currentAngle;

	public TurnRobot(Turn angleChoice) {
		requires(Robot.drive);
		angle = angleChoice.getAngle();
	}

	// Enum stuff for angle measures
	public enum Turn {
		RIGHT_ANGLE(90), 
		FORTYFIVE_ANGLE(45), 
		STRAIGHT_ANGLE(180), 
		THREEQUARTER_ANGLE(270);

		private final double angle;

		// Sets angle from OI
		Turn(double angle) {
			this.angle = angle;
		}

		public double getAngle() {
			return angle;
		}
	}

	// Other constructor for finer angles
	public TurnRobot(double a) {
		requires(Robot.drive);
		angle = a;
	}

	@Override
	protected void initialize() {
		// Gets current gyro value so we don't have to reset and meddle
		currentAngle = drive.getAngle();
	}

	@Override
	protected void execute() {
		drive.turnRobot(angle);
	}

	@Override
	protected boolean isFinished() {
		// End command if 180 turn is finished
		// NOTE TO SELF, change parameter names of this method in Drive class
		return drive.canTurn(angle, currentAngle);
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

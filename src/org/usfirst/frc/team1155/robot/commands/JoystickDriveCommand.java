package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickDriveCommand extends Command{

	private Joystick leftJoystick, rightJoystick;
	
	/**
	 * Teleop Drive Command
	 */
	public JoystickDriveCommand() {
		requires(Robot.driveSubsystem);

		leftJoystick = Robot.oi.leftJoystick;
		rightJoystick = Robot.oi.rightJoystick;
	}
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.driveSubsystem.setSpeed(leftJoystick.getY(), rightJoystick.getY());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
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

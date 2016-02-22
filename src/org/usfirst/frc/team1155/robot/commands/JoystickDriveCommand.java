package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickDriveCommand extends Command{

	private Joystick leftJoystick, rightJoystick;
	private double leftVal, rightVal;
	
	/**
	 * Teleop Drive Command
	 */
	public JoystickDriveCommand(Joystick leftJoy, Joystick rightJoy) {
		requires(Robot.driveSubsystem);

		leftJoystick = leftJoy;
		rightJoystick = rightJoy;
	}
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		//Temporary drive curve, please fix
		leftVal = -leftJoystick.getY() * Math.abs(leftJoystick.getY());
		rightVal = -rightJoystick.getY() * Math.abs(rightJoystick.getY());
		double limits = (leftJoystick.getRawAxis(3) + 1) / 2.0;
		leftVal *= limits;
		rightVal *= limits;
		Robot.driveSubsystem.setSpeed(leftVal, rightVal);
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

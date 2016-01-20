package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {
	
	private static Joystick leftJoy, rightJoy;
	
	private Drive drive = Robot.drive;
	private double leftJoyVal, rightJoyVal;
	
	public DriveCommand() {
		leftJoy = Hardware.INSTANCE.leftJoystick;
		rightJoy = Hardware.INSTANCE.rightJoystick;

	}
	
	@Override
	protected void initialize() {
		requires(Robot.drive);

	}

	@Override
	protected void execute() {
		leftJoyVal = leftJoy.getY();
		rightJoyVal = rightJoy.getY();
		drive.setSpeed(leftJoyVal, rightJoyVal);
		
	}

	@Override
	protected boolean isFinished() {
		
		return (leftJoyVal == 0 && rightJoyVal == 0);
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

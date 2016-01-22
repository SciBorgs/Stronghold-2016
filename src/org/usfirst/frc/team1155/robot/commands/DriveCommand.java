package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {
	private static Joystick leftJoy, rightJoy;

	private Drive drive = Robot.drive;

	public DriveCommand() {
		requires(Robot.drive);
		leftJoy = Hardware.INSTANCE.leftJoystick;
		rightJoy = Hardware.INSTANCE.rightJoystick;

	}

	@Override
	protected void initialize() {
		drive.updateDriveDashboard();
	}

	@Override
	protected void execute() {
		drive.setSpeed(leftJoy.getY(), rightJoy.getY());
		drive.updateDriveDashboard();
	}

	@Override
	protected boolean isFinished() {
		return false;
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

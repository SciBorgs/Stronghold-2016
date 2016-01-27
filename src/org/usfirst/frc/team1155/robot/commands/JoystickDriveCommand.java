package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoystickDriveCommand extends Command {
	private static Joystick leftJoy, rightJoy;
	private static SmartDashboard dashboard = Robot.dashboard;

	private DriveSubsystem drive = Robot.drive;

	public JoystickDriveCommand() {
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
		dashboard.putNumber("LeftJoy_Position", leftJoy.getY());
		dashboard.putNumber("RightJoy_Position", rightJoy.getY());
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

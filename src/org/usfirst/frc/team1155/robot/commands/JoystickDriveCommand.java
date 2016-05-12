package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickDriveCommand extends Command{

	private Joystick gamePad;
	private Button driveStraight;
	private double leftVal, rightVal, avgVal;
	
	/**
	 * Teleop Drive Command
	 */
	
	public JoystickDriveCommand(Joystick gamePad, Button driveStraight) {
		requires(Robot.driveSubsystem);
		
		this.gamePad = gamePad;
		this.driveStraight = driveStraight;
		leftVal = gamePad.getRawAxis(1);
		rightVal = gamePad.getRawAxis(5);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		System.out.println("RUNNING DRIVE");
		//Temporary drive curve, please fix
		leftVal = -gamePad.getRawAxis(1) * Math.abs(gamePad.getRawAxis(1));
		rightVal = -gamePad.getRawAxis(5) * Math.abs(gamePad.getRawAxis(5));
		if(driveStraight.get()) {
			avgVal = (leftVal + rightVal) / 2;
			Robot.driveSubsystem.setSpeed(avgVal, avgVal);
		} else {
			Robot.driveSubsystem.setSpeed(leftVal, rightVal);
		}
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

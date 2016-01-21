package org.usfirst.frc.team1155.robot.autonomous.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command{
	//Not finished. Need routines
	private Drive drive = Robot.drive;
	
	
	@Override
	protected void initialize() {
		requires(drive);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}

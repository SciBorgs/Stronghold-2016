package org.usfirst.frc.team1155.robot.teleoperated.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

public class Feeding extends Command{

	private static Feeder feeder = Robot.feeder;
	
	public Feeding(){
		requires(Robot.feeder);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		feeder.feed();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return feeder.isFed();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		feeder.stopFeeding();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
}

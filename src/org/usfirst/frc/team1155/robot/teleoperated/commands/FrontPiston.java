package org.usfirst.frc.team1155.robot.teleoperated.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

public class FrontPiston extends Command{
	
	private static Feeder feeder = Robot.feeder;
	
	public FrontPiston(){
		requires(Robot.feeder);
	}


	@Override
	protected void initialize() {		
	}

	@Override
	protected void execute() {
		feeder.toggleFrontPiston();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
	}

}
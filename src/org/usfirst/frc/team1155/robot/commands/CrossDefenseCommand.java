package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CrossDefenseCommand extends Command{
	
	private final double ANGLE_BUFFER = 5; 
	private final double TIME_TO_CROSS = 1.2;
	private final double INITIAL_SPEED = 0.5;
	
	public CrossDefenseCommand(){
		
	}
	
	@Override
	protected void initialize() {
		requires(Robot.driveSubsystem);
		
		Robot.driveSubsystem.setSpeed(INITIAL_SPEED, INITIAL_SPEED);
		
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}

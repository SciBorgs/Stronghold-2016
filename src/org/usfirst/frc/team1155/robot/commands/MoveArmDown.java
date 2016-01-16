package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArmDown extends Command {

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		ArmSubsystem.moveBackward();
		
	}

	@Override
	protected boolean isFinished() {
		
		return aButton.get();
	}

	@Override
	protected void end() {
		ArmSubsystem.setStill();
	}

	@Override
	protected void interrupted() {
		end();
		
	}

}

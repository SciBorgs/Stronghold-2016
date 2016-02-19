package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HolderCommand extends Command {
	
	public enum HolderMode {
		OPEN,
		CLOSED;
	}
	
	public HolderCommand(HolderMode mode) {
		if(mode == HolderMode.OPEN) {
			Robot.intakeSubsystem.setHolderSpeed(-0.4);
		}else {
			Robot.intakeSubsystem.setHolderSpeed(0.4);
		}
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
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

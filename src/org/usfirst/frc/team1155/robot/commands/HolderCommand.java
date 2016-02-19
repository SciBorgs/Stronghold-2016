package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HolderCommand extends Command{

	public enum HolderPosition {
		OPEN,
		CLOSED;
	}
	
	private HolderPosition holderPosition;
	
	public HolderCommand(HolderPosition position) {		
		requires(Robot.intakeSubsystem);
		holderPosition = position;
	}
	@Override
	protected void initialize() {
		if(holderPosition == HolderPosition.OPEN) {
			Robot.intakeSubsystem.setHolderSpeed(-0.4);
		} else {
			Robot.intakeSubsystem.setHolderSpeed(0.4);
		}
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		if(holderPosition == HolderPosition.OPEN) {
			return Robot.intakeSubsystem.holderLimitSwitch_Open.get();
		}else {
			return Robot.intakeSubsystem.holderLimitSwitch_Closed.get();
		}
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

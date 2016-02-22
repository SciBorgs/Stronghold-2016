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
		holderPosition = position;
	}
	@Override
	protected void initialize() {
		if(holderPosition == HolderPosition.OPEN) {
			Robot.intakeSubsystem.setHolderSpeed(0.4);
		} else {
			Robot.intakeSubsystem.setHolderSpeed(-0.4);
		}
	}

	@Override
	protected void execute() {
		System.out.println(Robot.intakeSubsystem.holderLimitSwitch_Open.get() + " " + Robot.intakeSubsystem.holderLimitSwitch_Closed.get());
		if(holderPosition == HolderPosition.OPEN) {
			Robot.intakeSubsystem.setHolderSpeed(0.4);
		} else {
			Robot.intakeSubsystem.setHolderSpeed(-0.4);
		}
	}

	@Override
	protected boolean isFinished() {
		if(holderPosition == HolderPosition.OPEN) {
			Robot.shootSubsystem.setBallPossessed(false);
			return !Robot.intakeSubsystem.holderLimitSwitch_Open.get();
		}else {
			Robot.shootSubsystem.setBallPossessed(true);
			return !Robot.intakeSubsystem.holderLimitSwitch_Closed.get();
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

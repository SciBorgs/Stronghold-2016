package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.FeederSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmCommand extends Command {
	
	private static FeederSubsystem feeder = Robot.feeder;

	@Override
	protected void initialize() {
		requires(Robot.feeder);
		
	}

	@Override
	protected void execute() {
		feeder.updateArmDashboard();
		feeder.toggleSecondaryArm();
		feeder.secondaryArmRollersON();
	}

	@Override
	protected boolean isFinished() {
		return feeder.isFed();
	}

	@Override
	protected void end() {
		feeder.resetSecondaryArm();
		feeder.secondaryArmRollersOFF();
	}

	@Override
	protected void interrupted() {
		
	}
}

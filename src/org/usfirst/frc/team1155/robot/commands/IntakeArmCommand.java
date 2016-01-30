package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.FeederSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmCommand extends Command {
	
	private static FeederSubsystem feeder = Robot.feeder;

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires(Robot.feeder);
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		feeder.updateArmDashboard();
		feeder.toggleSArm();
		feeder.SArmRollersON();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return feeder.isFed();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		feeder.resetSArm();
		feeder.SArmRollersOFF();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}

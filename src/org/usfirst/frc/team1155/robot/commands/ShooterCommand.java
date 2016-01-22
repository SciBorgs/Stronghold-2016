package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterCommand extends Command {

	private static ShooterSubsystem shooter = Robot.shooter;
	private static double shooterSpeed;
	
	public ShooterCommand(double speed) {
		requires(Robot.shooter);
		shooterSpeed = speed;
	}
	
	@Override
	protected void initialize() {	
	}

	@Override
	protected void execute() {
		shooter.startShooter(shooterSpeed);	
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		shooter.stopShooter();
	}

	@Override
	protected void interrupted() {	
	}

}

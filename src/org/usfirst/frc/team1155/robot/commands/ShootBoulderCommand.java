package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;
import org.usfirst.frc.team1155.robot.commands.TogglePistonCommand.Mode;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBoulderCommand extends CommandGroup {
	private Command shooterIoCommand;
	private Command togglePistonCommand;
	
	public ShootBoulderCommand() {
		requires(Robot.shootSubsystem);
		shooterIoCommand = new ShooterIOCommand(Mode.OUTPUT);
		
		addSequential(shooterIoCommand, 2);
		addSequential(new TogglePistonCommand(Mode.EXTEND), 2);	
		addSequential(new TogglePistonCommand(Mode.RETRACT), 2);	

	}
	@Override
	protected void initialize() {
		start();
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return !Robot.shootSubsystem.isBallPossessed() && Robot.shootSubsystem.isPistonRetracted();
	}

	@Override
	protected void end() {
		cancel();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}
}

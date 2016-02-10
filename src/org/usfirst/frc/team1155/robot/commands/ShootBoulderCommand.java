package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;
import org.usfirst.frc.team1155.robot.commands.TogglePistonCommand.PistonMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBoulderCommand extends CommandGroup {
	
	public ShootBoulderCommand() {		
		addSequential(new ShooterIOCommand(Mode.OUTPUT), 2);
		addSequential(new TogglePistonCommand(PistonMode.EXTEND), 1);	
		addSequential(new TogglePistonCommand(PistonMode.RETRACT), 1);	
	}

}

package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;
import org.usfirst.frc.team1155.robot.commands.TogglePistonCommand.PistonMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBoulderCommand extends CommandGroup {
	
	/**
	 * Command Group
	 * <br> <br>
	 * <b> First </b> Revs up shooter wheels
	 * <br>
	 * <b> Second </b> Extends piston to shoot ball into spinning wheels
	 * <br>
	 * <b> Third </b> Retracts piston 
	 */
	public ShootBoulderCommand() {		
		addParallel(new ShooterIOCommand(Mode.OUTPUT), 2); // revs up the wheels
		addParallel(new TogglePistonCommand(PistonMode.EXTEND), 1);	// extends piston
		addSequential(new TogglePistonCommand(PistonMode.RETRACT), 1);	//retract piston
	}

}

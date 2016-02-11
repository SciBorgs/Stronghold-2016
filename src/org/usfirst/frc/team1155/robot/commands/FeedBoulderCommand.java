package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FeedBoulderCommand extends CommandGroup {
    
    public FeedBoulderCommand() {
    	addSequential(new ConveyorBeltCommand());
        addSequential(new ShooterIOCommand(Mode.INPUT));
    }
}

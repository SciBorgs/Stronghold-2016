package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.commands.IntakeCommand.IntakeMode;
import org.usfirst.frc.team1155.robot.commands.IntakeCommand.Pivot;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FeedBoulderCommand extends CommandGroup {
   
	/**
	 Takes in a boulder and moves it into ball compartment 
	 */
    public FeedBoulderCommand() {
    	addParallel(new IntakeCommand(IntakeMode.PIVOT_AND_ROLL, Pivot.DOWN)); // When ball is reached, rotate arm down to take in
    	addParallel(new ConveyorBeltCommand()); // Start conveyor to move ball to shooter
        addParallel(new ShooterIOCommand(Mode.INPUT)); // Activates shooter to put it into storage compartment
    }
}

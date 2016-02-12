package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.commands.IntakeCommand.IntakeMode;
import org.usfirst.frc.team1155.robot.commands.IntakeCommand.Pivot;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FeedBoulderCommand extends CommandGroup {
    //sequence of commands: closes the arm to feed the ball in when the ball is reached, and sucks it in 
    public FeedBoulderCommand() {
    	addParallel(new IntakeCommand(IntakeMode.PIVOT_AND_ROLL, Pivot.DOWN));
    	addParallel(new ConveyorBeltCommand());
        addParallel(new ShooterIOCommand(Mode.INPUT));
    }
}

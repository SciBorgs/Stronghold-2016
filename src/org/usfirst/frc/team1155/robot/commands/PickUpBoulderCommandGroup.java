package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.commands.HolderCommand.HolderPosition;
import org.usfirst.frc.team1155.robot.commands.RollerCommand.IntakeMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickUpBoulderCommandGroup extends CommandGroup {
    
    public  PickUpBoulderCommandGroup() {
        addSequential(new RollerCommand(IntakeMode.ULTRA_BASED));
        addParallel(new HolderCommand(HolderPosition.CLOSED));
        addSequential(new RollerCommand(IntakeMode.CONTINUOUS));
    }
}

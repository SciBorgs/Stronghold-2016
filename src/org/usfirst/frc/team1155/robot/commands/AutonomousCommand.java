package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;
import org.usfirst.frc.team1155.robot.commands.SlowRotateCommand.Rotate;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup{
	
	private final double DISTANCE_TO_DEFENSE = 2;
	
	public enum Defense {
		PORTCULLIS,
		CHEVALDEFRISE,
		MOAT,
		RAMP,
		ROUGH_TERRAIN,
		ROCK_WALL,
		SALLYPORT,
		DRAWBRIDGE;
	}
	
	public enum Position {
		SLOT_1,
		SLOT_2,
		SLOT_3,
		SLOT_4,
	}
	
	public AutonomousCommand(Defense defense, Position position) {
		switch(defense) {
		case PORTCULLIS:
			//addSequential(new IntakeCommand(Pivot.UP));
			addSequential(new DistanceDriveCommand(36)); //change distance later on
			//addSequential(new IntakeCommand(Pivot.NEUTRAL));
			break;
		case CHEVALDEFRISE:
			//addSequential(new IntakeCommand(Pivot.DOWN));
			addSequential(new DistanceDriveCommand(36));
			//addSequential(new IntakeCommand(Pivot.NEUTRAL));
			break;
		case MOAT:
			addSequential(new DistanceDriveCommand(36)); 
			break;
		case RAMP:
			addSequential(new DistanceDriveCommand(36)); 
			break;
		case DRAWBRIDGE:
			//can't do
			break;
		case ROCK_WALL:
			addSequential(new DistanceDriveCommand(36)); 
			break;
		case SALLYPORT:
			//can't do
			break;
		case ROUGH_TERRAIN:
			addSequential(new DistanceDriveCommand(36)); 
			break;
		default:
			break;
		}
		
		//addSequential(new DriveToTape());
		addSequential(new VisionCommand(false)); //starts vision
		switch(position) {
		case SLOT_1: case SLOT_2:
			addSequential(new SlowRotateCommand(Rotate.RIGHT)); //turns until tape is found
			break;
		case SLOT_3: case SLOT_4:
			addSequential(new SlowRotateCommand(Rotate.LEFT)); //turns until tape is found
			break;
		default:
			break;
		}
		addSequential(new VisionTurnDriveCommand(Robot.targetVector.theta)); //rotates to angle of tape
		addSequential(new ShooterIOCommand(Mode.OUTPUT)); //shoots
		
	}

}

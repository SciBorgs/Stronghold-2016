package org.usfirst.frc.team1155.robot.commands;

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
		addSequential(new DistanceDriveCommand(DISTANCE_TO_DEFENSE));
		switch(defense) {
		case PORTCULLIS:
			break;
		case CHEVALDEFRISE:
			break;
		case MOAT:
			break;
		case RAMP:
			break;
		case DRAWBRIDGE:
			break;
		case ROCK_WALL:
			break;
		case SALLYPORT:
			break;
		case ROUGH_TERRAIN:
			break;
		default:
			break;
		}
//		addSequential(new ColorDriveCommand());
//		addParrallel(new VisionCommand(), 1);
//		addParrallel(new TurnToDriveCommand());
	}

}

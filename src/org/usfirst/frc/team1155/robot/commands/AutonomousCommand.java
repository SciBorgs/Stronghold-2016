package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.commands.RotateCommand.RobotPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup{
	
	private final double DISTANCE_TO_DEFENSE = 99; //or 74 in; conflicting sources 
	private final double DISTANCE_TO_GREEN_TAPE = 59.5;
	
	/**
	 * Types of Defenses
	 * <ul>
	 * <li> PORTCULLIS </li>
	 * <li> CHEVALDEFRISE </li>
	 * <li> MOAT </li>
	 * <li> RAMP </li>
	 * <li> ROUGH_TERRAIN </li>
	 * <li> ROCK_WALL </li>
	 * <li> SALLYPORT </li>
	 * <li> DRAWBRIDGE </li>
	 * </ul>
	 */
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
	
	/**
	 * SLOT_1 is furthest from Low Bar
	 */
	public enum Position {
		SLOT_1,
		SLOT_2,
		SLOT_3,
		SLOT_4,
	}
	/**
	 * This lets you decide what type of defense you want to cross and what position you are at 
	 * 
	 * @param defense Type of defense to cross
	 * @param position Position of robot at beginning of autonomous
	 * 
	 */
	public AutonomousCommand(Defense defense, Position position) {
		addSequential(new DistanceDriveCommand(DISTANCE_TO_DEFENSE)); // Drives up to defense
		
		switch(defense) {
		case PORTCULLIS:
//			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.UP)); // Lift portcullis
			addSequential(new CrossDefenseCommand(0.2)); // Drive over defense
//			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.NEUTRAL)); // Drop portcullis
			break;
		case CHEVALDEFRISE:
//			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.DOWN)); // Push down plates
			addSequential(new CrossDefenseCommand(0.2)); // Drive over defense
//			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.NEUTRAL)); // Release plates
			break;
		case MOAT:
			addSequential(new CrossDefenseCommand(0.5)); // Drive over defense
			break;
		case RAMP:
			addSequential(new CrossDefenseCommand(0.5)); // Drive over defense
			break;
		case DRAWBRIDGE:
			// Can't do
			break;
		case ROCK_WALL:
			addSequential(new CrossDefenseCommand(0.4)); // Drive over defense
			break;
		case SALLYPORT:
			// Can't do
			break;
		case ROUGH_TERRAIN:
			addSequential(new TimedDrive(1.5, 0.8)); // Drive over defense
			break;
		default:
			break;
		}
		
		// Aligns the robot with the tower and then shoots
		//addSequential(new DriveToTape());
		addSequential(new DistanceDriveCommand(DISTANCE_TO_GREEN_TAPE));
		switch(position) {
		case SLOT_1:
			addSequential(new RotateCommand(RobotPosition.SLOT_1)); // Turns towards goal
			break;
		case SLOT_2:
			addSequential(new RotateCommand(RobotPosition.SLOT_2)); 
			break;
		case SLOT_3: 
			addSequential(new RotateCommand(RobotPosition.SLOT_3));
			break;
		case SLOT_4:
			addSequential(new RotateCommand(RobotPosition.SLOT_4)); 
			break;
		default:
			break;
		}
		addSequential(new VisionTurnCommand()); // Rotates to tape on tower
		addSequential(new VisionDriveCommand());
		if(Robot.imageSubsystem.isTargetTape()) {
			addSequential(new ShooterIOCommand()); // Shoots
		}
		
	}

}

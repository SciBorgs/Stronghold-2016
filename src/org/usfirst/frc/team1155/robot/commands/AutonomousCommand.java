package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup{
	
	private final double DISTANCE_TO_DEFENSE = 70; //or 74 in; conflicting sources 
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
			addSequential(new PivotCommand(IntakeSubsystem.PIVOT_DOWN_POSITION)); // Lift portcullis
			addSequential(new TimedDrive(2, .6)); // Drive over defense
//			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.NEUTRAL)); // Drop portcullis
			break;
		case CHEVALDEFRISE:
			addSequential(new PivotCommand(IntakeSubsystem.PIVOT_DOWN_POSITION)); 
			addSequential(new TimedDrive(1.5, .5));
//			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.DOWN)); // Push down plates
			// Drive over defense
//			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.NEUTRAL)); // Release plates
			break;
		case MOAT:
			addSequential(new TimedDrive(1.5, 0.8)); // Drive over defense
			break;
		case RAMP:
			addSequential(new TimedDrive(2.0, 1.0)); // Drive over defense
			break;
		case DRAWBRIDGE:
			// Can't do
			break;
		case ROCK_WALL:
			addSequential(new TimedDrive(1.5, 0.8)); // Drive over defense
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
		if (!(defense == Defense.PORTCULLIS || defense == Defense.CHEVALDEFRISE))
			addSequential(new PivotCommand(IntakeSubsystem.PIVOT_SHOOT_POSITION));
		
		addSequential(new RotateToTape(position));
		addParallel(new RevShooterCommand());
		
		addSequential(new VisionTurnCommand()); // Rotates to tape on tower
		addSequential(new VisionDriveCommand()); // revs
		
		addSequential(new ShooterIOCommand());

	}

}

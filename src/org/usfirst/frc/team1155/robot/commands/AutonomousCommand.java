package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.commands.IntakeCommand.IntakeMode;
import org.usfirst.frc.team1155.robot.commands.IntakeCommand.Pivot;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand.Mode;
import org.usfirst.frc.team1155.robot.commands.SlowRotateCommand.Rotate;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup{
	
	private final double DISTANCE_TO_DEFENSE = 2;
	//defenses
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
	//positions of defenses
	public enum Position {
		SLOT_1,
		SLOT_2,
		SLOT_3,
		SLOT_4,
	}
	//routines based on type of defense
	public AutonomousCommand(Defense defense, Position position) {
		addSequential(new DistanceDriveCommand(24)); //drive up to defense
		switch(defense) {
		case PORTCULLIS:
			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.UP)); //lift portcullis
			addSequential(new CrossDefenseCommand()); //drive over
			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.NEUTRAL));
			break;
		case CHEVALDEFRISE:
			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.DOWN)); //push down plates
			addSequential(new CrossDefenseCommand()); //drive over
			addSequential(new IntakeCommand(IntakeMode.PIVOT, Pivot.NEUTRAL));
			break;
		case MOAT:
			addSequential(new CrossDefenseCommand()); //drive over
			break;
		case RAMP:
			addSequential(new CrossDefenseCommand()); //drive over
			break;
		case DRAWBRIDGE:
			//can't do
			break;
		case ROCK_WALL:
			addSequential(new CrossDefenseCommand()); //drive over
			break;
		case SALLYPORT:
			//can't do
			break;
		case ROUGH_TERRAIN:
			addSequential(new CrossDefenseCommand()); //drive over
			break;
		default:
			break;
		}
		
		//aligns the robot with the tower and then shoots
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

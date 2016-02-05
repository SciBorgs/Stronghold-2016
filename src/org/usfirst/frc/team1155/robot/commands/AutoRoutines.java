package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoRoutines extends CommandGroup{
	
	private static final double DISTANCE_TO_DEFENSE = 12; //in feet
	
	public AutoRoutines(){
		StandardRoutine();

		//Once we have a better idea of what routines we have, there will be a switch case to determine 
		//what routines we will be executing
	}
	
	//example routines
	public void StandardRoutine(){
		addSequential(new AutoCrossDefenseCommand(DISTANCE_TO_DEFENSE));
		addSequential(new VisionCommand(false));
		addSequential(new TurnRobotCommand(Robot.targetVector.theta));
		addSequential(new AutoDriveCommand(Robot.targetVector.xDistance));
		addSequential(new ShooterCommand(1));
	}
	
//	public void shootBall(){
//		//addParallel(new Vision()); if we use vision
//		//addParallel(new Shoot()); Shoots the ball
//	}
//	
//	public void returnToNeutral(){
//		addSequential(new TurnRobot(180)); //turns it around
//		addSequential(new DriveCommand()); //drives it back
//	}
	
}

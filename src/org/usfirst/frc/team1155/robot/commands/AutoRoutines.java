package org.usfirst.frc.team1155.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoRoutines extends CommandGroup{
	
	
	public AutoRoutines(){
		goThroughDefenseNAME();
		shootBall();
		returnToNeutral();
		
		//Once we have a better idea of what routines we have, there will be a switch case to determine 
		//what routines we will be executing
	}
	
	//example routines
	public void goThroughDefenseNAME(){
		addSequential(new DriveCommand()); //drives
		addSequential(new TurnRobot(135)); //turns to align with it, not real angle pls don't use this angle
		addSequential(new DriveCommand()); //continues driving into the courtyard
	}
	
	public void shootBall(){
		//addParallel(new Vision()); if we use vision
		//addParallel(new Shoot()); Shoots the ball
	}
	
	public void returnToNeutral(){
		addSequential(new TurnRobot(180)); //turns it around
		addSequential(new DriveCommand()); //drives it back
	}
	
}

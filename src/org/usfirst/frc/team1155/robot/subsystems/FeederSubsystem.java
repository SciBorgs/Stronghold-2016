package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FeederSubsystem extends Subsystem {

	/*
	 * Needs Hardware. Please add necessary hardware to both Hardware class and update this subsystem
	 */

	private DigitalInput limit = Hardware.INSTANCE.limitSwitch;
	private Ultrasonic ultrasonic = Hardware.INSTANCE.feederUltrasonic;
	private CANTalon topAxle = Hardware.INSTANCE.topAxle;
	private CANTalon botAxle = Hardware.INSTANCE.botAxle;
	private CANTalon sArm = Hardware.INSTANCE.sArmTalon;
	private CANTalon sArmRoller = Hardware.INSTANCE.sArmRollerTalon;
	private SmartDashboard dashboard = Robot.dashboard;
	private static final int MOVING_UP = 1;
	private static final int STOP_MOVING = 0;
	private static final int MOVING_DOWN = -1;	
	private static final int ULTRARANGE = 2;
	
	public FeederSubsystem() {
		botAxle.changeControlMode(CANTalon.TalonControlMode.Follower);
		botAxle.set(topAxle.getDeviceID());
		ultrasonic.setAutomaticMode(true);
	}

	//For feeding robot
	public void feed() {
		topAxle.set(MOVING_UP);
	}
	
	//For stopping to feed robot
	public void stopFeeding() {
		topAxle.set(STOP_MOVING);
	}
	
	//For checking if robot is fed
	public boolean isFed() {
		// if limit switch is true (not pressed), keep moving (DEPRICATED)
		//return !limit.get();
		if (ultrasonic.getRangeInches() < ULTRARANGE){
			return true;
		}
		else {
			return false;
		}
	}
	
	//Updates SmartDashboard
	public void updateFeederDashboard() {
		if(!limit.get()) 
			//If ball is in robot prints true to SmartDashboard
			dashboard.putBoolean("Is Robot Fed", true);
		else
			//If ball is not in robot prints false to SmartDashboard
			dashboard.putBoolean("Is Robot Fed", false);
	}
	
	
	//Updates SmartDashboard
	public void updateArmDashboard() {
		if(sArm.get() < 0){
			dashboard.putString("Small arm movement", "IN");
		}
		else if(sArm.get() == 0){
			dashboard.putString("Small arm movement", "NOT MOVING");
		}
		else if(sArm.get() > 0){
			dashboard.putString("Small arm movement", "OUT");
		}
	}
	
	//TODO add sensor or other method for stopping feeder arm movement at limits
	//Small arm commands (SArm)
	
	//toggle arm rotate inwards
	public void toggleSArm(){
		sArm.set(MOVING_DOWN);
	}
	
	//halt arm
	public void stopSArm(){
		sArm.set(STOP_MOVING);
	}
	
	//reset arm
	public void resetSArm(){
		sArm.set(MOVING_UP);
	}
	
	//start arm rollers
	public void SArmRollersON(){
		sArmRoller.set(MOVING_UP);
	}
	
	//stop arm rollers
	public void SArmRollersOFF(){
		sArmRoller.set(STOP_MOVING);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}

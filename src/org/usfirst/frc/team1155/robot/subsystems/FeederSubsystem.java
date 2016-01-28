package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FeederSubsystem extends Subsystem {

	/*
	 * Needs Hardware. Please add necessary hardware to both Hardware class and update this subsystem
	 */

	private DigitalInput limit = Hardware.INSTANCE.limitSwitch;
	private CANTalon topAxle = Hardware.INSTANCE.topAxle;
	private CANTalon botAxle = Hardware.INSTANCE.botAxle;
	private Solenoid pistonFeeder = Hardware.INSTANCE.pistonFeeder;
	private SmartDashboard dashboard = Robot.dashboard;
	private static final int MOVING_UP = 1;
	private static final int STOP_MOVING = 0;

	public FeederSubsystem() {
		botAxle.changeControlMode(CANTalon.TalonControlMode.Follower);
		botAxle.set(topAxle.getDeviceID());
	}

	public void feed() {
		topAxle.set(MOVING_UP);
	}
	
	public void stopFeeding() {
		topAxle.set(STOP_MOVING);
	}
	
	public boolean isFed() {
		// if limit switch is true (not pressed), keep moving
		return !limit.get();
	}
	
	public void updateFeederDashboard() {
		if(!limit.get()) 
			dashboard.putBoolean("Is Robot Fed", true);
		else
			dashboard.putBoolean("Is Robot Fed", false);
	}
	
	//Piston Methods
	public void togglePiston(){
		pistonFeeder.set(!pistonFeeder.get());
	}
	
	public void updateRampDashboard() {
		dashboard.putBoolean("Is Ramp Up", pistonFeeder.get());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
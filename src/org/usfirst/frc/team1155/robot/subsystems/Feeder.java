package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feeder extends Subsystem {

	/*
	 * Needs Hardware. Please add necessary hardware to both Hardware class and update this subsystem
	 */

	private DigitalInput limit;
	private CANTalon topAxle, botAxle;
	private Solenoid pistonFeeder;
	private static SmartDashboard dashboard = Robot.dashboard;
	private static final int MOVING_UP = 1;
	private static final int STOP_MOVING = 0;

	public Feeder() {
		limit = Hardware.INSTANCE.limitSwitch;
		topAxle = Hardware.INSTANCE.topAxle;
		botAxle = Hardware.INSTANCE.botAxle;
		pistonFeeder = Hardware.INSTANCE.pistonFeeder;
		
		botAxle.changeControlMode(CANTalon.ControlMode.Follower);
	}

	public void feed() {
		topAxle.set(MOVING_UP);
		dashboard.putBoolean("Is robot feeding", true);
	}
	
	public void stopFeeding() {
		topAxle.set(STOP_MOVING);
		dashboard.putBoolean("Is robot feeding", false);
	}
	
	public boolean isFed() {
		// if limit switch is true (not pressed), keep moving
		dashboard.putBoolean("Is robot fed", true);
		return !limit.get();
	}
	
	public void togglePiston(){
		pistonFeeder.set(!pistonFeeder.get());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}

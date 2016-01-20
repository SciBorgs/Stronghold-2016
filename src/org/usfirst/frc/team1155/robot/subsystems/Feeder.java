package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Feeder extends Subsystem {

	/*
	 * Needs Hardware. Please add necessary hardware to both Hardware class and update this subsystem
	 */

	private DigitalInput limit;
	private CANTalon topAxle, botAxle;
	private static final int MOVING_UP = 1;
	private static final int STOP_MOVING = 0;

	public Feeder() {
		limit = Hardware.INSTANCE.limitSwitch;
		topAxle = Hardware.INSTANCE.topAxle;
		botAxle = Hardware.INSTANCE.botAxle;
		
		botAxle.changeControlMode(CANTalon.ControlMode.Follower);
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

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}

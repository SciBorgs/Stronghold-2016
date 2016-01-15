package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Feeder extends Subsystem {

	private CANTalon axle;
	private DigitalInput limit;
	private static final int MOVING_UP = 1;
	private static final int STOP_MOVING = 0;

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public Feeder() {
		axle = Hardware.INSTANCE.topAxle;
		limit = Hardware.INSTANCE.limitSwitch;
	}

	public void feedingMotion() {
		// if limit switch is true (not pressed), keep moving
		if (limit.get())
			axle.set(MOVING_UP);
		axle.set(STOP_MOVING);
	}

}

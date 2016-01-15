package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Feeder extends Subsystem {
	
	private CANTalon axle;
	private DigitalInput limit;
	private final int movingUp, stopMoving;
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public Feeder(){
		axle = Hardware.INSTANCE.topAxle;
		limit = Hardware.INSTANCE.limitSwitch;
		movingUp = 1; //assuming 1 points up
		stopMoving = 0;
	}

	public void feedingMotion(){
		if (limit.get())
			axle.set(movingUp); //if limit switch is true (not pressed), keep moving
		axle.set(stopMoving);
	}
	
}

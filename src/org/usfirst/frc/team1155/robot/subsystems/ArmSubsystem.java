package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem { 
	private static CANTalon leftArm = Hardware.INSTANCE.leftArmTalon;
	private static CANTalon rightArm = Hardware.INSTANCE.rightArmTalon;
	
	@Override
	protected void initDefaultCommand() {
		
	}
	public static void setStill() {
		leftArm.set(0);
		rightArm.set(0);
	}
	public static void extend() {
		leftArm.set(1);
		rightArm.set(1);
	}
	public static void retract() {
		leftArm.set(-1);
		rightArm.set(-1);
	}
	
	public void setSpeed(double speed) {
		leftArm.set(speed);
		rightArm.set(speed);
	}
	public boolean cannotMove() {
		return false;
	}

}

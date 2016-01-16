package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem { 
	public static leftArm = Hardware.INSTANCE.leftArmTalon;
	public static rightArm = Hardware.INSTANCE.rightArmTalon;
	@Override
	protected void initDefaultCommand() {
		
	}
	public static void setStill() {
		leftArm.set(0);
		rightArm.set(0);
	}
	public static void moveForward() {
		leftArm.set(1);
		rightArm.set(1);
	}
	public static void moveBackward() {
		leftArm.set(-1);
		rightArm.set(-1);
	}

}

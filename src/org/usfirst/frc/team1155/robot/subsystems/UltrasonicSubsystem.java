package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.Ultrasonic;

public class UltrasonicSubsystem {
	Ultrasonic leftUltrasonic, rightUltrasonic;
	
	//In feet
	private static final double closestDistance = 12;
	
	public UltrasonicSubsystem() {
		leftUltrasonic = Hardware.INSTANCE.leftUltrasonic;
		rightUltrasonic = Hardware.INSTANCE.rightUltrasonic;
		leftUltrasonic.setEnabled(true);
		rightUltrasonic.setEnabled(true);
	}
	
	public void pingUltrasonics() {
		leftUltrasonic.ping();
		rightUltrasonic.ping();
	}
	
	public boolean isPathObstructed() {
		if(leftUltrasonic.getRangeInches() <= closestDistance && rightUltrasonic.getRangeInches() <= closestDistance)
			return true;
		else 
			return false;
	}
	
	public double angleToAlignTo() {
		
	}
}

package org.usfirst.frc.team1155.robot.subsystems;
import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class ShooterSubsystem extends Subsystem {
	
	private static CANTalon leftShooterTalon, rightShooterTalon;
	private static double shooterSpeed;
	
	public ShooterSubsystem(double shooterSpeedInput) {
		shooterSpeed = shooterSpeedInput;
		leftShooterTalon = Hardware.INSTANCE.leftShooterTalon;
		rightShooterTalon = Hardware.INSTANCE.rightShooterTalon;
	}
	
	public void startShooter() {
		leftShooterTalon.set(shooterSpeed);
		rightShooterTalon.set(-shooterSpeed);
	}
	
	public void stopShooter() {
		leftShooterTalon.set(0);
		rightShooterTalon.set(0);
	}
	
	protected void initDefaultCommand() {
		
	}

}

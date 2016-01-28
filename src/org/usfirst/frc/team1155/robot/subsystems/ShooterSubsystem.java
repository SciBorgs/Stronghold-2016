package org.usfirst.frc.team1155.robot.subsystems;
import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ShooterSubsystem extends Subsystem {
	
	private CANTalon leftShooterTalon, rightShooterTalon;
	
	private SmartDashboard dashboard = Robot.dashboard;
	
	public ShooterSubsystem() {
		leftShooterTalon = Hardware.INSTANCE.leftShooterTalon;
		rightShooterTalon = Hardware.INSTANCE.rightShooterTalon;
	}
	
	//For starting shooter
	public void startShooter(double shooterSpeed) {
		leftShooterTalon.set(shooterSpeed);
		rightShooterTalon.set(-shooterSpeed);
	}
	
	//For stopping shooter
	public void stopShooter() {
		leftShooterTalon.set(0);
		rightShooterTalon.set(0);
	}
	
	//Updates SmartDashboard
	public void updateShooterDashboard() {
		//Prints speeds of each shooter wheel
		dashboard.putNumber("Right Shooter Speed", rightShooterTalon.get());
		dashboard.putNumber("Left Shooter Speed", leftShooterTalon.get());
	}
	
	protected void initDefaultCommand() {
		
	}

}

package org.usfirst.frc.team1155.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.vision.USBCamera;

public enum Hardware {
	INSTANCE;
	
	//Limit switch
	public DigitalInput limitSwitch;
	
	//User input devices
	public Joystick gamepad, leftJoystick, rightJoystick;
	
	//Ultrasonic sensors
	public Ultrasonic leftUltrasonic, rightUltrasonic;
	
	//CANTalons for driving
	public CANTalon frontRightTalon, backRightTalon, frontLeftTalon, backLeftTalon;

	//CANTalon for Arm movement
	public CANTalon armTalon;
	
	//CANTalons for winch
	public CANTalon winchTalon, winchFollowerTalon;
	
	//CANTalons for feeder
	public CANTalon topAxle, botAxle;
	
	public Gyro gyro;
	
	public USBCamera camera;
	
	public Solenoid frontPiston;
	
	Hardware() {
		limitSwitch = new DigitalInput(0);
		
		gamepad = new Joystick(0);
		leftJoystick = new Joystick(1);
		rightJoystick = new Joystick(2);
		
		leftUltrasonic = new Ultrasonic(0,1);
		rightUltrasonic = new Ultrasonic(2,3);
		
		frontRightTalon = new CANTalon(0);
		backRightTalon = new CANTalon(2);
		frontLeftTalon = new CANTalon(3);
		backLeftTalon = new CANTalon(5);
		
		armTalon = new CANTalon(6);
		
		winchTalon = new CANTalon(7);
		winchFollowerTalon = new CANTalon(8);
		
		topAxle = new CANTalon(9);
		botAxle = new CANTalon(10);
		
		gyro = new Gyro(0);
		
		camera = new USBCamera("USBCam");
		
		frontPiston = new Solenoid(0);
	}
}

package org.usfirst.frc.team1155.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;

public enum Hardware {
	INSTANCE;
	
	//Limit switch
	public DigitalInput limitSwitch;
	
	//User input devices
	public Joystick gamepad, leftJoystick, rightJoystick;
	
	//Ultrasonic sensors
	public Ultrasonic leftUltrasonic, rightUltrasonic;
	
	//CANTalons
	public CANTalon topAxle, frontRightTalon, midRightTalon, backRightTalon, frontLeftTalon, midLeftTalon, backLeftTalon;

	public Gyro gyro;
	
	Hardware() {
		limitSwitch = new DigitalInput(0);
		
		gamepad = new Joystick(0);
		leftJoystick = new Joystick(1);
		rightJoystick = new Joystick(2);
		
		leftUltrasonic = new Ultrasonic(0,1);
		rightUltrasonic = new Ultrasonic(2,3);
		
		topAxle = new CANTalon(0);
		frontRightTalon = new CANTalon(1);
		midRightTalon = new CANTalon(2);
		backRightTalon = new CANTalon(3);
		frontLeftTalon = new CANTalon(4);
		midLeftTalon = new CANTalon(5);
		backLeftTalon = new CANTalon(6);
		
		gyro = new Gyro(0);
	}
}

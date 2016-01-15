package org.usfirst.frc.team1155.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;

public enum Hardware {
	INSTANCE;
	
	public CANTalon topAxle;
	public DigitalInput limitSwitch;
	
	public Joystick gamepad;
	public Joystick leftJoystick;
	public Joystick rightJoystick;
	
	public Ultrasonic leftUltrasonic;
	public Ultrasonic rightUltrasonic;
	public CANTalon frontRightTalon;
	
	public CANTalon midRightTalon;
	public CANTalon backRightTalon;
	public CANTalon frontLeftTalon;
	public CANTalon midLeftTalon;
	public CANTalon backLeftTalon;

	public Gyro gyro;
	
	Hardware() {
		topAxle = new CANTalon(0);
		limitSwitch = new DigitalInput(0);
		
		gamepad = new Joystick(0);
		leftJoystick = new Joystick(1);
		rightJoystick = new Joystick(2);
		
		leftUltrasonic = new Ultrasonic(0,1);
		rightUltrasonic = new Ultrasonic(2,3);
		frontRightTalon = new CANTalon(4);
		
		midRightTalon = new CANTalon(5);
		backRightTalon = new CANTalon(6);
		frontLeftTalon = new CANTalon(7);
		midLeftTalon = new CANTalon(8);
		backLeftTalon = new CANTalon(9);
		
		gyro = new Gyro(11);
	}
}

package org.usfirst.frc.team1155.robot;

public class PortMap {
	// PDB ports
	public static final int DRIVE_FRONT_LEFT_TALON = 6;
	public static final int DRIVE_FRONT_RIGHT_TALON = 7;
	public static final int DRIVE_BACK_LEFT_TALON = 3;
	public static final int DRIVE_BACK_RIGHT_TALON = 10;

	public static final int CONVEYOR_TALON = 9;
	public static final int BOULDER_HOLDER_TALON = 1;
	public static final int HOLDER_OPEN_LIMIT_SWITCH = 0;
	public static final int HOLDER_CLOSED_LIMIT_SWITCH = 1;
	
	public static final int CLIMB_ARM_TALON = 11;
	public static final int CLIMB_WINCH_TALON = 4;
	
	public static final int SHOOT_LEFT_TALON = 5;
	public static final int SHOOT_RIGHT_TALON = 8;
	
	public static final int INTAKE_ROLLER_TALON = 2;
	public static final int INTAKE_PIVOT_TALON = 12;//switched with boulder_holder_talon
	
	// Analog channels
	public static final int[] DRIVE_LEFT_ULTRASONIC = {0, 1};
	public static final int[] DRIVE_RIGHT_ULTRASONIC = {0, 1};
	public static final int[] BALL_DETECTOR_ULTRASONIC = {7, 8};
	
	public static final int STABALIZATION_GYRO = 0;
	public static final int DRIVE_GYRO = 1;

	public static final int[] SHOOT_BOULDER_PISTON = {2,1};
	
	public static final int JOYSTICK_LEFT = 0;
	public static final int JOYSTICK_RIGHT = 1;
	public static final int GAMEPAD = 2;

	public static final int SHOOT_CHECKER_LIMIT_SWITCH = 3;
	public static final int UPPER_LIMIT_SWITCH = 0;
	public static final int CAMERA_TILT = 2;
	
	public static final int ARDUINO_DIGITAL_OUTPUT = 0;
}
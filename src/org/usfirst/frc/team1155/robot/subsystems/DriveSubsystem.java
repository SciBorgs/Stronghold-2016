
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    
	public CANTalon frontRightTalon, frontLeftTalon, backRightTalon, backLeftTalon;
	public Ultrasonic leftUltrasonic, rightUltrasonic;
	public Gyro chickenGyro; 
	
	private static final double WHEEL_RADIUS = 4; //inches
	
	//instantiates drive hardware
	public DriveSubsystem() {
		frontRightTalon = new CANTalon(PortMap.DRIVE_FRONT_RIGHT_TALON);
		frontLeftTalon = new CANTalon(PortMap.DRIVE_FRONT_LEFT_TALON);
		backRightTalon = new CANTalon(PortMap.DRIVE_BACK_RIGHT_TALON);
		backLeftTalon = new CANTalon(PortMap.DRIVE_BACK_LEFT_TALON);
		
		backRightTalon.setInverted(true);
		frontRightTalon.setInverted(true);
		
		backRightTalon.set(frontRightTalon.getDeviceID());
		backLeftTalon.set(frontLeftTalon.getDeviceID());
		
		leftUltrasonic = new Ultrasonic(PortMap.DRIVE_LEFT_ULTRASONIC[0], PortMap.DRIVE_LEFT_ULTRASONIC[1]);
		rightUltrasonic = new Ultrasonic(PortMap.DRIVE_RIGHT_ULTRASONIC[0],PortMap.DRIVE_RIGHT_ULTRASONIC[1]);
				
		rightUltrasonic.setAutomaticMode(true);
		rightUltrasonic.setEnabled(true);
		
		leftUltrasonic.setAutomaticMode(true);
		leftUltrasonic.setEnabled(true);
		
		chickenGyro = new AnalogGyro(PortMap.DRIVE_ANALOG_GYRO);
	}
    public void initDefaultCommand() {
                    	    	
    }
    //alter motor values
    public void setSpeed(double leftVal, double rightVal) {
    	frontRightTalon.set(rightVal);
    	frontLeftTalon.set(leftVal);	
    }
    //avg ultrasonic distance0
    public double getAverageUltrasonicDistance() {
    	return (leftUltrasonic.getRangeInches() + rightUltrasonic.getRangeInches())/2;
    }
    //gets closest distance from a specific ultrasonic to an object
    public double getClosestUltrasonicDistance() {
    	return Math.min(leftUltrasonic.getRangeInches(), rightUltrasonic.getRangeInches());
    }
    
    public double getEncoderDistance() {
    	return (frontRightTalon.getEncPosition()/1023.0) * (Math.PI * 2 * WHEEL_RADIUS);
    }
    
    public double getAngle() {
    	return chickenGyro.getAngle() % 360;
    }
}


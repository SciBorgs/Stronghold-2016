
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
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public CANTalon frontRightTalon, frontLeftTalon, backRightTalon, backLeftTalon;
	public Ultrasonic leftUltrasonic, rightUltrasonic;
	public Gyro chickenGyro; 
	
	private static final double WHEEL_RADIUS = 4; //inches

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
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new DriveCommand());
    	
    }
    public void setSpeed(double leftVal, double rightVal) {
    	frontRightTalon.set(rightVal);
    	frontLeftTalon.set(leftVal);	
    }
    
    public double getAverageUltrasonicDistance() {
    	return (leftUltrasonic.getRangeInches() + rightUltrasonic.getRangeInches())/2;
    }
    
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


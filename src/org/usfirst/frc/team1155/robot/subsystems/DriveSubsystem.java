
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the movement of the robot. Tank drive
 */
public class DriveSubsystem extends Subsystem {
    
	public CANTalon frontRightTalon, frontLeftTalon, backRightTalon, backLeftTalon;
	public Ultrasonic leftUltrasonic, rightUltrasonic;
	
	/** Gyro used mostly in autonomous for determining if the defense has been crossed */
	public AnalogGyro stabalizationGyro; 
	/** Gyro used mostly for turning robot */
	public AnalogGyro driveGyro;

	private static final double WHEEL_RADIUS = 4; // Inches
	
	// Instantiates drive hardware
	public DriveSubsystem() {
		frontRightTalon = new CANTalon(PortMap.DRIVE_FRONT_RIGHT_TALON);
		frontLeftTalon = new CANTalon(PortMap.DRIVE_FRONT_LEFT_TALON);
		backRightTalon = new CANTalon(PortMap.DRIVE_BACK_RIGHT_TALON);
		backLeftTalon = new CANTalon(PortMap.DRIVE_BACK_LEFT_TALON);
		
		backRightTalon.changeControlMode(TalonControlMode.Follower);
		backLeftTalon.changeControlMode(TalonControlMode.Follower);
		
		backRightTalon.setInverted(true);
		frontRightTalon.setInverted(true);
		
		backRightTalon.set(frontRightTalon.getDeviceID());
		backLeftTalon.set(frontLeftTalon.getDeviceID());
		
//		leftUltrasonic = new Ultrasonic(PortMap.DRIVE_LEFT_ULTRASONIC[0], PortMap.DRIVE_LEFT_ULTRASONIC[1]);
//		rightUltrasonic = new Ultrasonic(PortMap.DRIVE_RIGHT_ULTRASONIC[0],PortMap.DRIVE_RIGHT_ULTRASONIC[1]);
				
//		rightUltrasonic.setAutomaticMode(true);
//		rightUltrasonic.setEnabled(true);
		
//		leftUltrasonic.setAutomaticMode(true);
//		leftUltrasonic.setEnabled(true);
		
		stabalizationGyro = new AnalogGyro(PortMap.STABALIZATION_GYRO);
		driveGyro = new AnalogGyro(PortMap.DRIVE_GYRO);
	}
	
    public void initDefaultCommand() {
                    	    	
    }
    /**
     * Method for setting speed of the robot in terms of VBus
     * 
     * @param leftVal VBus value to set left talon to
     * @param rightVal VBus value to set right talon to
     */
    public void setSpeed(double leftVal, double rightVal) {
    	frontRightTalon.set(rightVal);
    	frontLeftTalon.set(leftVal);
    	
    	backRightTalon.set(frontRightTalon.getDeviceID());
		backLeftTalon.set(frontLeftTalon.getDeviceID());
    }
	
	/**
	 * Calculates average distance detected by the two ultrasonics in front of the robot
	 * 
	 * @return Returns the average distance in inches
	 */
    public double getAverageUltrasonicDistance() {
    	return (leftUltrasonic.getRangeInches() + rightUltrasonic.getRangeInches())/2;
    }
    
    
    /**
     * Returns the closest distance detected by either ultrasonic
     *  
     * @return Returns closest distance in inches
     */
    public double getClosestUltrasonicDistance() {
    	return Math.min(leftUltrasonic.getRangeInches(), rightUltrasonic.getRangeInches());
    }
    
    /**
     * Keeps track of and returns total distance traveled by robot relative to its Front Right Talon
     * 
     * @return Returns encoder distance traveled total
     */
    public double getEncoderDistance() {
    	SmartDashboard.putNumber("Encoder Value", (frontRightTalon.getEncPosition()/1023.0) * (Math.PI * 2 * WHEEL_RADIUS));
    	return (frontRightTalon.getEncPosition()/1023.0) * (Math.PI * 2 * WHEEL_RADIUS);
    }

}


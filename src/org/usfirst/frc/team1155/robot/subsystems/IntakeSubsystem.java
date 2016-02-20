
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem
 * <br>
 * Controls the movement of the front intake system of the robot
 */
public class IntakeSubsystem extends Subsystem {
	
    private static final double BALL_IN_DISTANCE = 7;
		
	public CANTalon intakeTalon, pivotTalon, conveyorTalon, holderTalon;
	public DigitalInput holderLimitSwitch_Open, holderLimitSwitch_Closed;
	public Ultrasonic ballDetector;
	public boolean ballInRobot = false;
	
	public IntakeSubsystem() {
    	intakeTalon = new CANTalon(PortMap.INTAKE_ROLLER_TALON);
    	pivotTalon = new CANTalon(PortMap.INTAKE_PIVOT_TALON);
    	conveyorTalon = new CANTalon(PortMap.CONVEYOR_TALON);
    	holderTalon = new CANTalon(PortMap.BOULDER_HOLDER_TALON);
		
    	holderLimitSwitch_Open = new DigitalInput(PortMap.HOLDER_OPEN_LIMIT_SWITCH);
    	holderLimitSwitch_Closed = new DigitalInput(PortMap.HOLDER_CLOSED_LIMIT_SWITCH);
    	
    	intakeTalon.changeControlMode(TalonControlMode.PercentVbus);
    	pivotTalon.changeControlMode(TalonControlMode.PercentVbus);
    	conveyorTalon.changeControlMode(TalonControlMode.PercentVbus);
    	holderTalon.changeControlMode(TalonControlMode.PercentVbus);
    	
    	ballDetector = new Ultrasonic(PortMap.BALL_DETECTOR_ULTRASONIC[0], PortMap.BALL_DETECTOR_ULTRASONIC[1]);
	}
	
	/**
	 * Controls rotational movement of arm
	 * @param position Encoder position to move to
	 */
	public void setPivotIntakePosition(double position){
		pivotTalon.set(position);
	}
	
	public double getPivotIntakePosition() {
		return pivotTalon.getSetpoint();
	}
	
	/**
	 * Controls rolling speed of roller on the arm
	 * 
	 * @param speed Speed to set the talons of the roller to
	 */
	public void setRollerSpeed(double speed) {
		intakeTalon.set(speed);
	}
	
	/**
	 * Controls rolling speed of input conveyor
	 * 
	 * @param speed Speed to set the talons of the roller to
	 */
	public void setConveyorSpeed(double speed) {
		conveyorTalon.set(speed);
	}
	
	/**
	 * Controls turning speed of boulder holder
	 * 
	 * @param speed Speed to set the talons of the roller to
	 */
	public void setHolderSpeed(double speed) {
		holderTalon.set(speed);
	}
	
	/**
	 * Controls turning speed of boulder holder
	 * 
	 * @return booleanWhether the ball is in the robot, based off ultrasonic values
	 */
	public boolean isBallIn() {  
		return ballDetector.getRangeInches() <= BALL_IN_DISTANCE;
	}
	
	public void setBallInRobot(boolean value) {
		ballInRobot = value;
	}
	
	public boolean isBallInRobot() {
		return ballInRobot;
	}

	@Override
	protected void initDefaultCommand() {
	}    
}


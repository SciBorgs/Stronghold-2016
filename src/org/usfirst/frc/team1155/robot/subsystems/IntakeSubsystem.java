
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem
 * <br>
 * Controls the movement of the front intake system of the robot
 */
public class IntakeSubsystem extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
		
	public CANTalon intakeTalon, pivotTalon, conveyorTalon, holderTalon;
	
	public IntakeSubsystem() {
    	intakeTalon = new CANTalon(PortMap.INTAKE_ROLLER_TALON);
    	pivotTalon = new CANTalon(PortMap.INTAKE_PIVOT_TALON);
    	conveyorTalon = new CANTalon(PortMap.CONVEYOR_TALON);
    	holderTalon = new CANTalon(PortMap.BOULDER_HOLDER_TALON);
		
    	intakeTalon.changeControlMode(TalonControlMode.PercentVbus);
    	pivotTalon.changeControlMode(TalonControlMode.Position);
    	conveyorTalon.changeControlMode(TalonControlMode.PercentVbus);
    	holderTalon.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	/**
	 * Controls rotational movement of arm
	 * @param position Encoder position to move to
	 */
	public void setPivotIntakePosition(double position){
		pivotTalon.set(position);
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
	
	

	@Override
	protected void initDefaultCommand() {
	}    
}


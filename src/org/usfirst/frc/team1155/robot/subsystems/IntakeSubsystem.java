
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
		
	public CANTalon rollerTalon, pivotTalon;
	
	public IntakeSubsystem() {
    	rollerTalon = new CANTalon(PortMap.INTAKE_ROLLER_TALON);
    	pivotTalon = new CANTalon(PortMap.INTAKE_PIVOT_TALON);
		
    	pivotTalon.changeControlMode(TalonControlMode.Position);
	}
	
	/**
	 * Controls rotational movement of arm
	 * @param position Encoder position to move to
	 */
	public void setPivotIntake(double position){
		pivotTalon.set(position);
	}
	
	/**
	 * Controls rolling speed of roller on the arm
	 * 
	 * @param speed Speed to set the talons of the roller to
	 */
	public void setRoller(double speed) {
		rollerTalon.set(speed);
	}

	@Override
	protected void initDefaultCommand() {
	}    
}


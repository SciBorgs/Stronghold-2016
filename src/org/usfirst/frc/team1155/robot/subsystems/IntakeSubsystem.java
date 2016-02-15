
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
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
	
	public void setPivotIntake(double position){
		pivotTalon.set(position);
	}
	
	public void setRoller(double speed) {
		rollerTalon.set(speed);
	}

	@Override
	protected void initDefaultCommand() {
	}    
}


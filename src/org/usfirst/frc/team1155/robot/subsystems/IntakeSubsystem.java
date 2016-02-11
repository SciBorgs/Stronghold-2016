
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
	private boolean isRolling;
	
	public CANTalon rollerTalon, pivotTalon;
	
	public IntakeSubsystem() {
		isRolling = false;
		pivotTalon.changeControlMode(TalonControlMode.Position);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	rollerTalon = new CANTalon(PortMap.INTAKE_ROLLER_TALON);
    	pivotTalon = new CANTalon(PortMap.INTAKE_PIVOT_TALON);
    	
    }
    
    public void toggleRoller() {
    	rollerTalon.set((isRolling)? 0: 1);
    	isRolling = !isRolling;
    }
    
    public void setPivotTalon() {
    	pivotTalon.set(0);
    }
    
    
    
}


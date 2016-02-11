
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
	
	private static final int MAX_ENC_POS = 1023, MIN_ENC_POS = 0;
	
	public CANTalon rollerTalon, pivotTalon;
	
	public IntakeSubsystem() {
    	rollerTalon = new CANTalon(PortMap.INTAKE_ROLLER_TALON);
    	pivotTalon = new CANTalon(PortMap.INTAKE_PIVOT_TALON);
		
    	pivotTalon.changeControlMode(TalonControlMode.Position);
		
		isRolling = false;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand()); 	
    }
    
    public void toggleRoller() {
    	rollerTalon.set((isRolling)? 0: 1);
    	isRolling = !isRolling;
    }
    
    public void setPivotTalon(double deltaEncoderPosition) {
    	pivotTalon.set(pivotTalon.get() + deltaEncoderPosition);
    }
    
    public boolean canPivotUp() {
    	return pivotTalon.get() < MAX_ENC_POS;
    }
    
    public boolean canPivotDown() {
    	return pivotTalon.get() > MIN_ENC_POS;
    }
    
    
    
}


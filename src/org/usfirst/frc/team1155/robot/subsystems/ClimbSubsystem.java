
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbSubsystem extends Subsystem {
       
	public CANTalon winchTalon, armTalon;
    
    public ClimbSubsystem() {
        winchTalon = new CANTalon(13);
        winchTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        winchTalon.enableBrakeMode(true);
        
    	armTalon = new CANTalon(PortMap.CLIMB_ARM_TALON);
    	armTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }
    
    public boolean openArm() {
    	armTalon.set(1023);
    	return true;
    }
    
    public boolean closeArm() {
    	armTalon.set(0);
    	return true;
    }
    
    public void extendWinch() {
    	winchTalon.set(0.5);
    }
    
    public void retractWinch() {
    	winchTalon.set(-0.5);
    }
    
    public void stopWinch() {
    	winchTalon.set(0);
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}


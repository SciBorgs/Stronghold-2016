
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbSubsystem extends Subsystem {
       
	public CANTalon winchTalon, armTalon;
	
    public void initDefaultCommand() {
        winchTalon = new CANTalon(PortMap.CLIMB_WINCH_TALON);
    	armTalon = new CANTalon(PortMap.CLIMB_ARM_TALON);
    }
}


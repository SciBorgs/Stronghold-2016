
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *Subsystem
 *<br><br>
 *Controls movement of Conveyor belts for intake of ball
 */
public class ConveyorSubsystem extends Subsystem {
    
	public CANTalon topTalon, bottomTalon;
	
    public void initDefaultCommand() {
    	topTalon = new CANTalon(PortMap.CONVEYOR_TOP_TALON);
    	bottomTalon = new CANTalon(PortMap.CONVEYOR_BOTTOM_TALON);
    	
    	bottomTalon.setInverted(true);
    	bottomTalon.set(topTalon.getDeviceID());
    }
    
    /**
     * Sets speed of the conveyor's talons.
     * @param speed Speed to set conveyor to by Vbus values
     */
    public void setSpeed(double speed){
    	topTalon.set(speed);
    }
    	
}

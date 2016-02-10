
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ConveyorSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public CANTalon topTalon, bottomTalon;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	topTalon = new CANTalon(PortMap.CONVEYOR_TOP_TALON);
    	bottomTalon = new CANTalon(PortMap.CONVEYOR_BOTTOM_TALON);
    	
    	bottomTalon.setInverted(true);
    	bottomTalon.set(topTalon.getDeviceID());
    }
    
    public void setSpeed(double speed){
    	topTalon.set(speed);
    }
    	
}

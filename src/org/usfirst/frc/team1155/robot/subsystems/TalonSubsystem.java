
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TalonSubsystem extends Subsystem {
	public static CANTalon talonWheelLeft;
	public static CANTalon talonWheelRight;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());

    	talonWheelLeft = Hardware.INSTANCE.talonWheelLeft;
    	talonWheelRight = Hardware.INSTANCE.talonWheelRight;
    }
}


package org.usfirst.frc.team1155.robot.subsystems;
import org.usfirst.frc.team1155.robot.Hardware;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */


//MOVE INTO DRIVE SUBSYSTEM
//CREATE DRIVE COMMAND UTILIZING THIS
//ALSO REWRITE
public class ColorSensor extends Subsystem {
	private double maxThreshold;
	private double minThreshold;
    private AnalogInput colorsensor; 
    
    public ColorSensor(){
    	colorsensor = Hardware.INSTANCE.colorSensor;
    }
    public void setMaxThreshold(){
    	maxThreshold = colorsensor.getVoltage();
    }
    public void setMinThreshold(){
    	minThreshold = colorsensor.getVoltage();
    }
    
    public double getMinThreshold(){
    	return minThreshold;
    }
    public double getMaxThreshold(){
    	return maxThreshold;
    }
	
    public boolean isInThreshold(double x){
    	return x >= minThreshold || x <= maxThreshold;
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


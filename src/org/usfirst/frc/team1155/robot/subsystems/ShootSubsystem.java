
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem 
 * <br>
 * Controls the shooter on the robot
 */
public class ShootSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon followerTalon, mainTalon;
	private DigitalInput ballChecker; 
	private DoubleSolenoid boulderPusher;
		
	private boolean isPistonRetracted;
	
	public ShootSubsystem() {
		// Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	followerTalon = new CANTalon(PortMap.SHOOT_LEFT_TALON);
    	mainTalon = new CANTalon(PortMap.SHOOT_RIGHT_TALON);
    	
    	mainTalon.changeControlMode(TalonControlMode.PercentVbus);
    	
    	followerTalon.changeControlMode(TalonControlMode.Follower);
    	followerTalon.set(mainTalon.getDeviceID());
    	followerTalon.setInverted(true);
    	
    	ballChecker = new DigitalInput(PortMap.SHOOT_CHECKER_LIMIT_SWITCH);
    	boulderPusher = new DoubleSolenoid(PortMap.SHOOT_BOULDER_PUSHER[0], PortMap.SHOOT_BOULDER_PUSHER[1]);
    	
    	isPistonRetracted = false;
	}
	
    public void initDefaultCommand() {
       
    }
    
    /**
     * Sets speed of the shooter 
     * @param speed Speed to set talons of shooter in terms of Percent VBus
     */
    public void setSpeed(double speed) {
    	mainTalon.set(speed);
    }
    
    /**
     * Extends the storage compartment piston to launch ball into shooter wheels
     */
    public void extendPiston() {
    	boulderPusher.set(Value.kForward);
    	isPistonRetracted = false;
    }
    
    /**
     * Retracts the storage compartment piston
     */
    public void retractPiston() {
    	boulderPusher.set(Value.kReverse);
    	isPistonRetracted = true;
    }
    
    /**
     * Disables the storage compartment piston
     */
    public void turnOffPiston() {
    	boulderPusher.set(Value.kOff);
    }
    
    /**
     * Checks to see if there is a ball inside storage compartment.
     * <br>
     * Determined by limit switch
     * @return True if ball is in storage compartment
     */
    public boolean isBallPossessed() {
    	return ballChecker.get();
    }
    
    /**
     * Checks to see if the storage compartment piston is retracted
     * @return True if piston is retracted
     */
    public boolean isPistonRetracted() {
    	return isPistonRetracted;
    }
 
}


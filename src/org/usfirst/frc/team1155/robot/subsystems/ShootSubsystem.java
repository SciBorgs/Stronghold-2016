
package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShootSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon followerTalon, mainTalon;
	private DigitalInput ballChecker; 
	private DoubleSolenoid boulderPusher;
		
	private boolean isPistonRetracted;
	
    public void initDefaultCommand() {
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
    
    public void setSpeed(double speed) {
    	mainTalon.set(speed);
    }
    
    public void extendPiston() {
    	boulderPusher.set(Value.kForward);
    	isPistonRetracted = false;
    }
    
    public void retractPiston() {
    	boulderPusher.set(Value.kReverse);
    	isPistonRetracted = true;
    }
    
    public void turnOffPiston() {
    	boulderPusher.set(Value.kOff);
    }
    
    public boolean isBallPossessed() {
    	return ballChecker.get();
    }
    
    public boolean isPistonRetracted() {
    	return isPistonRetracted;
    }
 
}


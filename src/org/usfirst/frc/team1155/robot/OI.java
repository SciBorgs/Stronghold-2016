package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.ConveyorBeltCommand;
import org.usfirst.frc.team1155.robot.commands.JoystickDriveCommand;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand;
import org.usfirst.frc.team1155.robot.commands.VisionTurnDriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends Command {
    public Joystick leftJoystick, rightJoystick, gamePad;
    
    private Command joystickDrive, conveyor;
  //Input is the conveyor, load is the window motor (loading it into firing position).  Left joystick is input, right joystick is shoot
    private Button inputBall, loadBall, resetHolder;
    private Button revShooter, shoot;
    private Button aim;
    
    public OI() {
    	leftJoystick = new Joystick(PortMap.JOYSTICK_LEFT);
    	rightJoystick = new Joystick(PortMap.JOYSTICK_RIGHT);
    	gamePad = new Joystick(PortMap.GAMEPAD);
    	
    	//Button map
    	inputBall = new JoystickButton(leftJoystick, 1);
    	loadBall = new JoystickButton(leftJoystick, 2);
    	revShooter = new JoystickButton(rightJoystick, 1);
    	shoot = new JoystickButton(rightJoystick, 2);
    	aim = new JoystickButton(gamePad, 1);
    	
    	//Initialize drive command
    	joystickDrive = new JoystickDriveCommand(leftJoystick, rightJoystick);
    	
    	//Initialize conveyor command
    	conveyor = new ConveyorBeltCommand();
    	
    	//Initialize input button mapping (boulder input into the shooter)
    	//Input works in three stages.  The first is the intake, which captures the ball.  In this stage, intake and conveyor are
    	//rolling
    	//Second stage, intake is pushing the ball into the conveyor, and conveyor rolls it up
    	//Third stage, ball is at top of conveyor, and window motor pushes it up into shooting position
    	//First stage isn't ready
    	
    	
    	//Initialize shoot button mapping
    	//Shooting works in two stages.  The first is the motors revving up.  The second is the piston retracting, and pushing the
    	//ball into the shooter
    	shoot.whenPressed(new ShooterIOCommand());
    	
    	aim.whenPressed(new VisionTurnDriveCommand());
    }

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		joystickDrive.start();
	}

	@Override
	protected void execute() {
		// Temporary holder speed control <REMOVE>	
		if(leftJoystick.getPOV() == 0) {
			Robot.intakeSubsystem.setHolderSpeed(-0.4);
			Robot.shootSubsystem.setBallPossessed(true);  //Jerry-rigged, test setup
			System.out.println("Up the ball goes");
		}
		else if(leftJoystick.getPOV() == 180) {
			Robot.intakeSubsystem.setHolderSpeed(0.4);
			Robot.shootSubsystem.setBallPossessed(false);
			System.out.println("Down the ball goes");
		}
		else {
			Robot.intakeSubsystem.setHolderSpeed(0);
		}
		
		//Temporary shooting code <REMOVE>
		if(revShooter.get()) {
			Robot.shootSubsystem.setShooterSpeed(rightJoystick.getRawAxis(3));  //Set to the correct axis
			System.out.println("Revving shooter");
		}
		else {
			Robot.shootSubsystem.setShooterSpeed(0);
		}
		
		//Temporary conveyor code <REMOVE>
		if(inputBall.get()) {
			conveyor.start();
		} else conveyor.cancel();
		
		//Temporary intake pivot code <REMOVE>
		if(gamePad.getPOV() == 0) {
			Robot.intakeSubsystem.setPivotIntakePosition(0.7);
			System.out.println("Pivot: " + Robot.intakeSubsystem.getPivotIntakePosition());
		} else if(gamePad.getPOV() == 180){
			Robot.intakeSubsystem.setPivotIntakePosition(-0.4);
			System.out.println("Pivot: " + Robot.intakeSubsystem.getPivotIntakePosition());
		}
		else {
			Robot.intakeSubsystem.setPivotIntakePosition(0);
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		joystickDrive.cancel();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		joystickDrive.cancel();
	}
}


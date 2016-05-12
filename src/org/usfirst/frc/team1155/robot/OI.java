package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.JoystickDriveCommand;
import org.usfirst.frc.team1155.robot.commands.PickUpBoulderCommandGroup;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand;
import org.usfirst.frc.team1155.robot.commands.VisionTurnCommand;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends Command {
    public Joystick leftJoystick, rightJoystick, gamePad;
    
    private Command joystickDrive, intakeStart;
  //Input is the conveyor, load is the window motor (loading it into firing position).  Left joystick is input, right joystick is shoot
    private Button conveyorIn, loadBall, resetHolder;
    private Button revShooter, shoot, conveyorOut;
    private Button driveStraight;
    private Button moveArmUp, moveArmDown;
    private Button revIn;
  
    private Ultrasonic ultra;
    
    public OI() {
    	gamePad = new Joystick(PortMap.GAMEPAD);
    	
    	//Button map
    	/*analog for drive
			RB straight drive
			RT rev + A to shoot
			Dpad window motor
			LT LB conveyor
    	 */
    	//conveyorIn = new JoystickButton(gamePad, 1);
    	conveyorOut = new JoystickButton(gamePad, 5);
    	
    	driveStraight = new JoystickButton(gamePad, 6);
    	//revShooter = new JoystickButton(gamePad, 3);
    	shoot = new JoystickButton(gamePad, 1);
    	
    	//loadBall = new JoystickButton(gamePad, 2);
    	
    	moveArmUp = new JoystickButton(gamePad, 7);
    	//moveArmDown = new JoystickButton(gamePad, 8);
    	
    	revIn = new JoystickButton(gamePad, 8);
    	
    	
    	//Initialize drive command
    	joystickDrive = new JoystickDriveCommand(gamePad, driveStraight);
    	
    	//Intake command
    	intakeStart = new PickUpBoulderCommandGroup();
    	
    	//Initialize shoot button mapping
    	//Shooting works in two stages.  The first is the motors revving up.  The second is the piston retracting, and pushing the
    	//ball into the shooter
    	shoot.whenPressed(new ShooterIOCommand());
    }

	@Override
	protected void initialize() {
		joystickDrive.start();
	}

	@Override
	protected void execute() {
		if(conveyorOut.get()) {
			Robot.intakeSubsystem.conveyorTalon.set(.8);
		}
		else {
			Robot.intakeSubsystem.conveyorTalon.set(0);
		}
		
		//Temporary shooting code <REMOVE>
		if(gamePad.getRawAxis(3) > 0) {
			Robot.shootSubsystem.setShooterSpeed(.47);  //Set to the correct axis
			System.out.println("Revving shooter");
		}
		else if (revIn.get()) {
			Robot.shootSubsystem.setShooterSpeed(-.2);
		}
		else {
			Robot.shootSubsystem.setShooterSpeed(0);
		}
		
		
		//Robot.intakeSubsystem.setHolderSpeed(gamePad.getPOV());
		
		
		//Temporary conveyor code <REMOVE>
		if(gamePad.getRawAxis(2) > 0) {
			intakeStart.start();
		} else intakeStart.cancel();
		
		//Temporary intake pivot code <REMOVE>
//		if(gamePad.getPOV() == 0) {
//			Robot.intakeSubsystem.setPivotIntakePosition(Robot.intakeSubsystem.getPivotSetPosition() + 50);
//			System.out.println("Pivot: " + Robot.intakeSubsystem.getPivotIntakePosition() + " " + Robot.intakeSubsystem.getPivotSetPosition());
//		} else if(gamePad.getPOV() == 180){
//			Robot.intakeSubsystem.setPivotIntakePosition(Robot.intakeSubsystem.getPivotSetPosition() - 50);
//			System.out.println("Pivot: " + Robot.intakeSubsystem.getPivotIntakePosition() + " " + Robot.intakeSubsystem.getPivotSetPosition());
//		}
//		if(gamePad.getPOV() == 0) 
//			Robot.intakeSubsystem.setPivotIntakePosition(IntakeSubsystem.PIVOT_DOWN_POSITION);
//		else if (gamePad.getPOV() == 180) 
//			Robot.intakeSubsystem.setPivotIntakePosition(IntakeSubsystem.PIVOT_UP_POSITION);
//		else if (gamePad.getPOV() == 90 || gamePad.getPOV() == 270)
//			Robot.intakeSubsystem.setPivotIntakePosition(IntakeSubsystem.PIVOT_SHOOT_POSITION);
		if(gamePad.getPOV() == 0) {
			Robot.intakeSubsystem.setHolderSpeed(-0.5);
		}
		else if(gamePad.getPOV() == 180) {
			Robot.intakeSubsystem.setHolderSpeed(0.5);
		}
		else {
			Robot.intakeSubsystem.setHolderSpeed(0);
		}
		
		
//		if (moveArmUp.get()) {
//			Robot.climbSubsystem.armTalon.set(1);
//		} 
//		else if (moveArmDown.get()) {
//			Robot.climbSubsystem.armTalon.set(-1);
//		}
//		else {
//			Robot.climbSubsystem.armTalon.set(0);
//		}
		
		if(!joystickDrive.isRunning()) {
			if(gamePad.getRawAxis(1) > 0.05 || gamePad.getRawAxis(5) > 0.05)
				joystickDrive.start();
		}
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		joystickDrive.cancel();
	}

	@Override
	protected void interrupted() {
		joystickDrive.cancel();
	}
}
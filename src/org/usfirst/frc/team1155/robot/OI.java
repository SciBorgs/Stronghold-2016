package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.JoystickDriveCommand;
import org.usfirst.frc.team1155.robot.commands.PickUpBoulderCommandGroup;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand;
import org.usfirst.frc.team1155.robot.commands.VisionTurnCommand;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.IntakeSubsystem;

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
    private Button inputBall, loadBall, resetHolder;
    private Button revShooter, shoot, conveyorReverse;
    private Button aim, driveStraight;
    private Button moveWinchUp, moveWinchDown, moveArmUp, moveArmDown, tiltCamDown, tiltCamUp;
    private Button breakMode, coastMode;
    private Ultrasonic ultra;
    private CANTalon iWork;
    public OI() {
    	leftJoystick = new Joystick(PortMap.JOYSTICK_LEFT);
    	rightJoystick = new Joystick(PortMap.JOYSTICK_RIGHT);
    	gamePad = new Joystick(PortMap.GAMEPAD);
    	
    	//Button map
    	//inputBall = new JoystickButton(leftJoystick, 1);
    	inputBall = new JoystickButton(gamePad, 7);
    	loadBall = new JoystickButton(leftJoystick, 2);
    	revShooter = new JoystickButton(rightJoystick, 1);
    	shoot = new JoystickButton(rightJoystick, 2);
    	aim = new JoystickButton(gamePad, 1);
    	driveStraight = new JoystickButton(leftJoystick, 4);
    	//conveyorReverse = new JoystickButton(leftJoystick, 2);
    	conveyorReverse = new JoystickButton(gamePad, 8);
    	moveWinchUp = new JoystickButton(gamePad, 2);
    	moveWinchDown = new JoystickButton(gamePad, 3);
    	moveArmUp = new JoystickButton(gamePad, 5);
    	moveArmDown = new JoystickButton(gamePad, 6);
    	//tiltCamDown = new JoystickButton(gamePad, 7);
    	//tiltCamUp = new JoystickButton(gamePad, 8);
    	
    	
    	//Initialize drive command
    	joystickDrive = new JoystickDriveCommand(leftJoystick, rightJoystick, driveStraight);
    	
    	//Intake command
    	intakeStart = new PickUpBoulderCommandGroup();
    	
    	//Initialize shoot button mapping
    	//Shooting works in two stages.  The first is the motors revving up.  The second is the piston retracting, and pushing the
    	//ball into the shooter
    	shoot.whenPressed(new ShooterIOCommand());
    	
    	aim.whenPressed(new VisionTurnCommand());
    	
//    	cameraPan = new Servo(9);
    	
    	iWork = new CANTalon(4);
    }

	@Override
	protected void initialize() {
		joystickDrive.start();
//		cameraPan.setAngle(90);
//		cameraTilt.setAngle(130);
	}

	@Override
	protected void execute() {
		//System.out.println(cameraTilt.get());
		//.set(leftJoystick.getRawAxis(3));
		System.out.println("shooter speed:" + -(rightJoystick.getRawAxis(3) - 1) / 2.0);
		Robot.intakeSubsystem.setHolderSpeed(Math.min(gamePad.getRawAxis(5) * 0.6 , 0.7));
		System.out.println(gamePad.getRawAxis(5));
		
		//SmartDashboard.putNumber("Window motor position", Robot.intakeSubsystem.holderTalon.getPosition());
		
		if(conveyorReverse.get()) {
			Robot.intakeSubsystem.conveyorTalon.set(.8);
		}
		else {
			Robot.intakeSubsystem.conveyorTalon.set(0);
		}
		
		//Temporary shooting code <REMOVE>
		if(revShooter.get()) {
			Robot.shootSubsystem.setShooterSpeed(Robot.shootSubsystem.SHOOT_SPEED);  //Set to the correct axis
			System.out.println("Revving shooter");
		}
		else {
			Robot.shootSubsystem.setShooterSpeed(0);
		}
		
		if(gamePad.getRawAxis(5) < -0.2) {
			Robot.shootSubsystem.mainTalon.set(0.25);
			Robot.shootSubsystem.followerTalon.set(-0.1);
		}
		
		//Temporary conveyor code <REMOVE>
		if(inputBall.get()) {
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
			Robot.intakeSubsystem.setPivotIntakePosition(0.5);
		}
		else if(gamePad.getPOV() == 180) {
			Robot.intakeSubsystem.setPivotIntakePosition(-1);
		}
		else {
			Robot.intakeSubsystem.setPivotIntakePosition(0);
		}
		
		if(gamePad.getRawButton(3)) {
			iWork.set(1);

			System.out.println("go");
		}
		else if (gamePad.getRawButton(2)) {
			iWork.set(-1);
		}
		else {
			iWork.set(0);
		}
		
		if (moveArmUp.get()) {
			Robot.climbSubsystem.armTalon.set(1);
		} 
		else if (moveArmDown.get()) {
			Robot.climbSubsystem.armTalon.set(-1);
		}
		else {
			Robot.climbSubsystem.armTalon.set(0);
		}

		/*if (tiltCamDown.get()) {
			Robot.imageSubsystem.cameraTilt.setAngle(ImageSubsystem.CAMERA_DOWN_POSITION);
		}
		if (tiltCamUp.get()) {
			Robot.imageSubsystem.cameraTilt.setAngle(ImageSubsystem.CAMERA_UP_POSITION);
		}*/
		
		//Restarts drive
		if(!joystickDrive.isRunning()) {
			if(leftJoystick.getY() > 0.05 || rightJoystick.getY() > 0.05)
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
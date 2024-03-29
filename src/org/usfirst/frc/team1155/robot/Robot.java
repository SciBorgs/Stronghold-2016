
package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.AutonomousCommand;
import org.usfirst.frc.team1155.robot.commands.AutonomousCommand.Defense;
import org.usfirst.frc.team1155.robot.commands.AutonomousCommand.Position;
import org.usfirst.frc.team1155.robot.commands.RevShooterCommand;
import org.usfirst.frc.team1155.robot.commands.VisionCommand;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem.TargetVector;
import org.usfirst.frc.team1155.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ShootSubsystem;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	
	public static DriveSubsystem driveSubsystem;
	public static ClimbSubsystem climbSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	public static ShootSubsystem shootSubsystem;
	public static ImageSubsystem imageSubsystem;
	
	public static VisionCommand vision;
	
	public static OI oi;

	public static TargetVector targetVector;
	public static boolean auto = false;
    Command autonomousCommand;
    SendableChooser defenseChooser, positionChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	driveSubsystem = new DriveSubsystem();
    	climbSubsystem = new ClimbSubsystem();
    	intakeSubsystem = new IntakeSubsystem();
    	shootSubsystem = new ShootSubsystem();
    	imageSubsystem = new ImageSubsystem();
    	
		oi = new OI();
		
		defenseChooser = new SendableChooser();
		defenseChooser.addDefault("Portcullis", Defense.PORTCULLIS);
		defenseChooser.addObject("ChevalDeFrise", Defense.CHEVALDEFRISE);
		defenseChooser.addObject("Moat", Defense.MOAT);
		defenseChooser.addObject("Drawbridge", Defense.DRAWBRIDGE);
		defenseChooser.addObject("Sallyport", Defense.SALLYPORT);
		defenseChooser.addObject("Rock Wall", Defense.ROCK_WALL);
		defenseChooser.addObject("Rough Terrain", Defense.ROUGH_TERRAIN);
		defenseChooser.addDefault("Ramp", Defense.RAMP);
		
		positionChooser = new SendableChooser();
		positionChooser.addDefault("Slot 1", Position.SLOT_1);
		positionChooser.addObject("Slot 2", Position.SLOT_2);
		positionChooser.addObject("Slot 3", Position.SLOT_3);
		positionChooser.addObject("Slot 4", Position.SLOT_4);

        
        //chooser.addDefault("Default Auto", new ExampleCommand());
//        chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putNumber("H Min", 73);
		SmartDashboard.putNumber("H Max", 74);
		SmartDashboard.putNumber("S Min", 101);
		SmartDashboard.putNumber("S Max", 102);
		SmartDashboard.putNumber("V Min", 252);
		SmartDashboard.putNumber("V Max", 253);

        SmartDashboard.putData("Defense", defenseChooser);
        SmartDashboard.putData("Position", positionChooser);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    }
	
	public void disabledPeriodic() {

        System.out.println(defenseChooser.getSelected() + " " + positionChooser.getSelected());
		Scheduler.getInstance().run();
	}
 
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	auto = true;
    	
    	Robot.driveSubsystem.toBreakMode();
		Robot.imageSubsystem.cameraTilt.setAngle(ImageSubsystem.CAMERA_UP_POSITION);
    	Robot.intakeSubsystem.pivotTalon.setPosition(0);
    	//Create the autonomous command based on values from the SmartDashboard
        autonomousCommand = new AutonomousCommand(
        	(Defense) defenseChooser.getSelected(), 
        	(Position) positionChooser.getSelected()
        );
        System.out.println(defenseChooser.getSelected() + " " + positionChooser.getSelected());
        new VisionCommand(false).start();
        
        Robot.intakeSubsystem.setHolderSpeed(-.6);
        
        //If not null (above creation worked) start the command
        if (autonomousCommand != null) {
        	autonomousCommand.start();
        }
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();

		Robot.imageSubsystem.cameraTilt.setAngle(ImageSubsystem.CAMERA_UP_POSITION);
        
    }

    public void teleopInit() {
    	climbSubsystem.winchTalon.enableBrakeMode(true);
		auto = false;
		Robot.shootSubsystem.setShooterSpeed(0);
    	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        //Create and start OI (user input/output manager)
        vision = new VisionCommand(true);
        vision.start();
        oi = new OI();
        oi.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    	
    }
}

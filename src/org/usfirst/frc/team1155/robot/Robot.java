
package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.AutonomousCommand;
import org.usfirst.frc.team1155.robot.commands.AutonomousCommand.Defense;
import org.usfirst.frc.team1155.robot.commands.AutonomousCommand.Position;
import org.usfirst.frc.team1155.robot.commands.JoystickDriveCommand;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem.TargetVector;
import org.usfirst.frc.team1155.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ShootSubsystem;

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
	
	public static OI oi;

	public static TargetVector targetVector;
	
    Command autonomousCommand;
    SendableChooser defenseChooser, positionChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	DriveSubsystem driveSubsystem = new DriveSubsystem();
//    	ClimbSubsystem climbSubsystem = new ClimbSubsystem();
    	IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    	ShootSubsystem shootSubsystem = new ShootSubsystem();
//    	ImageSubsystem imageSubsystem = new ImageSubsystem();
    	
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
		SmartDashboard.putNumber("H Min", 110);
		SmartDashboard.putNumber("H Max", 130);
		SmartDashboard.putNumber("S Min", 255);
		SmartDashboard.putNumber("S Max", 255);
		SmartDashboard.putNumber("V Min", 255);
		SmartDashboard.putNumber("V Max", 255);

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
    	//Create the autonomous command based on values from the SmartDashboard
        autonomousCommand = new AutonomousCommand(
        	(Defense) defenseChooser.getSelected(), 
        	(Position) positionChooser.getSelected()
        );
        
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
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        //Create and start OI (user input/output manager)
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


package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.AutoRoutines;
import org.usfirst.frc.team1155.robot.subsystems.ClimbSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.FeederSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem.TargetVector;
import org.usfirst.frc.team1155.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static DriveSubsystem drive;
	public static ClimbSubsystem arms;
	public static FeederSubsystem feeder;
	public static ImageSubsystem image;
	public static ShooterSubsystem shooter;
	public static SmartDashboard dashboard;

	public static TargetVector targetVector;

	private static AutoRoutines autonomous;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {	
		drive = new DriveSubsystem();
//		arms = new ClimbSubsystem();
//		feeder = new Feeder();
		image = new ImageSubsystem();
//		shooter = new ShooterSubsystem();
//		dashboard = new SmartDashboard();
//		autonomous = new AutoRoutines();
		// instantiate the command used for the autonomous period
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomous != null)
			autonomous.start();
		if (oi != null)
			oi.cancel();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		image.recordVideo();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomous != null)
			autonomous.cancel();
		if (oi != null)
			oi.start();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

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

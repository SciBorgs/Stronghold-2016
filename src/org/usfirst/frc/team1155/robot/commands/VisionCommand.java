package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCommand extends Command {
	private static ImageSubsystem image = Robot.image;
	private static SmartDashboard dashboard = Robot.dashboard;
	
	private boolean isFinished;
	
	public VisionCommand() {
		requires(Robot.image);
	}
	
	@Override
	protected void initialize() {
		isFinished = false;
	}

	// Modify if statements below for corresponding color
	// Implement return to user method(?)
	// Implement pick up box method
	// Implement "give" box to user method
	@Override
	protected void execute() {
		image.takePicture();	
		
		try {	
			//HUE_LOW = 79, HUE_HIGH = 89, SATURATION_LOW = 64, SATURATION_HIGH = 153, LUMINANCE_LOW = 40, LUMINANCE_HIGH = 253
			if(image.prepareImage(79, 89, 64, 153, 40, 253)) {
				Robot.targetVector = image.getTargetVector();
				isFinished = true;
			}

		} catch (NIVisionException e) {
			dashboard.putString("IMAGE ERROR", e.getMessage());
		}
		
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}
	
}

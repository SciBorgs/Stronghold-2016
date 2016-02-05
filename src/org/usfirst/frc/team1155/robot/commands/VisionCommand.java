package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.ImageSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCommand extends Command {
	private static ImageSubsystem image = Robot.image;
	
	private boolean isFinished;
	private boolean isTele;
	
	public VisionCommand(boolean isTele) {
		requires(Robot.image);
		this.isTele = isTele;
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
		image.recordVideo();
		image.takePicture();
		if(isTele) {
			image.drawPredictedShot();
			image.displayImage();
		}
		if (image.doesTargetExist()) {
			image.analyzeImage();
			if(image.isTargetTape()) {
				Robot.targetVector = image.getTargetVector();
			}
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

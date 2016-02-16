package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionCommand extends Command {

	private boolean isFinished;
	private boolean isTele;

	/**
	 * Dual-Purpose Command
	 * <br> <br>
	 * If Robot is in teleop, camera vision is provided for the driver
	 * <br>
	 * If robot is in autonomous,  camera analyzes its image and gives values for automatic targeting
	 * 
	 * @param isTele If the robot is currently in teleop or not
	 */
	public VisionCommand(boolean isTele) {
		requires(Robot.imageSubsystem);
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
		Robot.imageSubsystem.recordVideo();
		Robot.imageSubsystem.takePicture();
		if (isTele) {
			Robot.imageSubsystem.drawPredictedShot();
			Robot.imageSubsystem.displayImage();
		}
		if (Robot.imageSubsystem.doesTargetExist()) {
			Robot.imageSubsystem.analyzeImage();
			if (Robot.imageSubsystem.isTargetTape()) {
				Robot.targetVector = Robot.imageSubsystem.getTargetVector();
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

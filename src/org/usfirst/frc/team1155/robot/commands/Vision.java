package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.Image;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends Command {
	private static Image i = Robot.image;
	
	public Vision() {
		requires(Robot.image);
	}
	
	@Override
	protected void initialize() {

		
	}

	// Modify if statements below for corresponding color
	// Implement return to user method(?)
	// Implement pick up box method
	// Implement "give" box to user method
	@Override
	protected void execute() {
		i.takePicture();	
		
		try {	
			//HUE_LOW = 79, HUE_HIGH = 89, SATURATION_LOW = 64, SATURATION_HIGH = 153, LUMINANCE_LOW = 40, LUMINANCE_HIGH = 253
			if(i.prepareImage(79, 89, 64, 153, 40, 253)) {
				SmartDashboard.putString("GOt image", "monies");
			}
			else {
				SmartDashboard.putString("GOt image", "no monies mo pblms");
			}
			
		} catch (NIVisionException e) {
			//Image was not taken
			e.printStackTrace();
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
		
	}

	@Override
	protected void interrupted() {
		
	}
	
}

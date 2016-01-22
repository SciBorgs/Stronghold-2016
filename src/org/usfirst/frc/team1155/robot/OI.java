package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.AlignWall;
import org.usfirst.frc.team1155.robot.commands.Feeding;
import org.usfirst.frc.team1155.robot.commands.MoveArm;
import org.usfirst.frc.team1155.robot.commands.TurnRobot;
import org.usfirst.frc.team1155.robot.commands.Winch;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends Command{
	
	private Joystick gamepad, leftJoystick, rightJoystick;
	
	private JoystickButton alignButton, feederButton, moveArmButton, turnAngleButton, winchButton;
	
	public OI() {
		gamepad = Hardware.INSTANCE.gamepad;
		leftJoystick = Hardware.INSTANCE.leftJoystick;
		rightJoystick = Hardware.INSTANCE.rightJoystick;
		
		
		alignButton = new JoystickButton(gamepad, 1);
		feederButton = new JoystickButton(gamepad, 2);
		moveArmButton = new JoystickButton(gamepad, 3);
		turnAngleButton = new JoystickButton(gamepad, 4);
		winchButton = new JoystickButton(gamepad, 5);
	}
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		alignButton.whenPressed(new AlignWall());
		feederButton.whenPressed(new Feeding());
		moveArmButton.whenPressed(new MoveArm(MoveArm.Position.TOP));
		turnAngleButton.whenPressed(new TurnRobot(TurnRobot.Turn.STRAIGHT_ANGLE));
		winchButton.whenPressed(new Winch(Winch.Direction.DOWN));
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
		// TODO Auto-generated method stub
	}
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}


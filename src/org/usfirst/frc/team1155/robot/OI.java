package org.usfirst.frc.team1155.robot;

import org.usfirst.frc.team1155.robot.commands.JoystickDriveCommand;
import org.usfirst.frc.team1155.robot.commands.PickUpBoulderCommandGroup;
import org.usfirst.frc.team1155.robot.commands.ShooterIOCommand;
import org.usfirst.frc.team1155.robot.commands.VisionTurnCommand;

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
	// Input is the conveyor, load is the window motor (loading it into firing
	// position). Left joystick is input, right joystick is shoot
	private Button driveStraight;

	private Button defenseMode, climbMode, shootMode, inputMode;
	private Button primary, secondary, tertiary;

	private Ultrasonic ultra;
	private Servo cameraPan, cameraTilt;

	private Mode mode;

	private enum Mode {
		SHOOT, CLIMB, DEFENSE, INPUT;
	}

	public OI() {
		leftJoystick = new Joystick(PortMap.JOYSTICK_LEFT);
		rightJoystick = new Joystick(PortMap.JOYSTICK_RIGHT);
		gamePad = new Joystick(PortMap.GAMEPAD);

		// Button map
		defenseMode = new JoystickButton(gamePad, 1);
		climbMode = new JoystickButton(gamePad, 2);
		shootMode = new JoystickButton(gamePad, 3);
		inputMode = new JoystickButton(gamePad, 4);

		primary = new JoystickButton(gamePad, 8);
		secondary = new JoystickButton(gamePad, 9);
		tertiary = new JoystickButton(gamePad, 10);

		// Initialize drive command
		joystickDrive = new JoystickDriveCommand(leftJoystick, rightJoystick, driveStraight);

		// Intake command
		intakeStart = new PickUpBoulderCommandGroup();

		// Initialize shoot button mapping
		// Shooting works in two stages. The first is the motors revving up. The
		// second is the piston retracting, and pushing the
		// ball into the shooter

		Robot.imageSubsystem.setCameraTilt(90);

		mode = Mode.INPUT;

		// cameraPan = new Servo(9);
		// cameraTilt = new Servo(8);
	}

	@Override
	protected void initialize() {
		joystickDrive.start();
		// cameraPan.setAngle(90);
		// cameraTilt.setAngle(130);
	}

	@Override
	protected void execute() {
		System.out.println(Robot.intakeSubsystem.ballDetector.getRangeInches());
		SmartDashboard.putString("Mode", mode.toString());

		double gamePadPOV = gamePad.getPOV();

		if (inputMode.get())
			mode = Mode.INPUT;
		if (shootMode.get())
			mode = Mode.SHOOT;
		if (defenseMode.get())
			mode = Mode.DEFENSE;
		if (climbMode.get())
			mode = Mode.CLIMB;

		switch (mode) {
		case INPUT: // Control window motor (D-Pad)
			if (primary.get())
				intakeStart.start();
			else
				intakeStart.cancel();

			if (gamePadPOV == 0) {
				Robot.intakeSubsystem.setHolderSpeed(-0.4);
				Robot.shootSubsystem.setBallPossessed(true); // Jerry-rigged,
																// test setup
				System.out.println("Up the ball goes");
			} else if (gamePadPOV == 180) {
				Robot.intakeSubsystem.setHolderSpeed(0.4);
				Robot.shootSubsystem.setBallPossessed(false);
				System.out.println("Down the ball goes");
			} else {
				Robot.intakeSubsystem.setHolderSpeed(0);
			}
			break;
		case SHOOT: // Align and shoot button
			primary.whenPressed(new ShooterIOCommand());
			if (secondary.get()) {
				Robot.shootSubsystem.setShooterSpeed(-(rightJoystick.getRawAxis(3) - 1) / 2.0); // Set
																								// to
																								// the
																								// correct
																								// axis
				System.out.println("Revving shooter");
			} else {
				Robot.shootSubsystem.setShooterSpeed(0);
			}
			tertiary.whenPressed(new VisionTurnCommand());
			break;
		case DEFENSE: // Leave it
			break;
		case CLIMB: // Controls climbing motor (D-Pad). Trigger buttons for
					// winch control
			if (gamePadPOV == 0)
				Robot.intakeSubsystem.setPivotIntakePosition(Robot.intakeSubsystem.getPivotSetPosition() + 10);
			else if (gamePadPOV == 180)
				Robot.intakeSubsystem.setPivotIntakePosition(Robot.intakeSubsystem.getPivotSetPosition() - 10);
			System.out.println("Pivot: " + Robot.intakeSubsystem.getPivotIntakePosition() + " "
					+ Robot.intakeSubsystem.getPivotSetPosition());
			break;
		}

		// Restarts drive
		if (!joystickDrive.isRunning()) {
			if (leftJoystick.getY() > 0.05 || rightJoystick.getY() > 0.05)
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

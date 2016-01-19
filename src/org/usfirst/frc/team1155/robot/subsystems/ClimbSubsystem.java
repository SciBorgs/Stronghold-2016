package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimbSubsystem extends Subsystem {
	private static CANTalon armTalon = Hardware.INSTANCE.armTalon;

	@Override
	protected void initDefaultCommand() {

	}

	public static void setStill() {
		armTalon.set(0);

	}

	public static void extend() {
		armTalon.set(1);

	}

	public static void retract() {
		armTalon.set(-1);
	}

	public void setSpeed(double speed) {
		armTalon.set(speed);

	}

	public boolean cannotMove() {
		return false;
	}

}

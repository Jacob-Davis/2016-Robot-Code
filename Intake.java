package org.usfirst.frc.team1672.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class Intake {

	private SpeedController leftWheel,rightWheel;
	
	private boolean intaking = false;
	private boolean dispensing = false;
	
	private final double INTAKE_SPEED = .4;
	private final double DISPENSE_SPEED = .45;
	private final double SUCK_SPEED = .65;
	
	public Intake (SpeedController init1, SpeedController init2)
	{
		leftWheel = init1;
		rightWheel = init2;
	}
	
	public boolean isIntaking()
	{
		return intaking;
	}
	
	public boolean isDispensing()
	{
		return dispensing;
	}
	
	public void intake()
	{
		leftWheel.set(INTAKE_SPEED);
		rightWheel.set(-INTAKE_SPEED);
	}
	
	public void dispense()
	{
		leftWheel.set(-DISPENSE_SPEED);
		rightWheel.set(DISPENSE_SPEED);
	}
	
	public void suck()
	{
		leftWheel.set(SUCK_SPEED);
		rightWheel.set(SUCK_SPEED);
	}
	
	public void stop()
	{
		intaking = false;
		dispensing = false;
		leftWheel.set(0);
		rightWheel.set(0);
	}
}

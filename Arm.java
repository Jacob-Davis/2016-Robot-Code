package org.usfirst.frc.team1672.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class Arm {

	private SpeedController arm;

	private final double SPEED = .5;
	
	
	public Arm(SpeedController init)
	{
		arm = init;
	}
	
	public void up()
	{
		arm.set(SPEED);
	}
	
	public void down()
	{
		arm.set(-SPEED);
	}
	
	public void stop()
	{
		arm.set(0);
	}
}
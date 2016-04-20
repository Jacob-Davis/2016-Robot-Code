package org.usfirst.frc.team1672.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class OI
{
	//Joystick Mapping
	private static final int IN_BUTTON = 2;
	private static final int OUT_BUTTON = 3;
	
	private static final int RESET_BUTTON = 4;
	
	private static final int UP_BUTTON = 2;
	private static final int DOWN_BUTTON = 3;
	
	private static final double LONG_SPEED = 1.0;
	private static final double SHORT_SPEED = 0.85;
	
	private double lVal, rVal, lastL, lastR;
	
	private Robot robot = new Robot();
	
	private RobotDrive myDrive;
	private Arm myArm;
	private Shooter myShooter;
	private Intake myIntake;
	//private GDrive gDrive;
	
	private Joystick leftStick, rightStick;
	
	public OI(Joystick left, Joystick right)
	{
		myDrive = robot.myRobot;
		myArm = robot.myArm;
		myShooter = robot.myShooter;
		myIntake = robot.myIntake;
		//gDrive = robot.myBot;
		
		leftStick = left;
		rightStick = right;
	}
	
	public void drive()
	{
		lVal = leftStick.getY();
		rVal = rightStick.getY();
		if(lVal != lastL || rVal != lastR)
		{
			myDrive.tankDrive(lVal,rVal);
			lastL = lVal;
			lastR = rVal;
		}
		//gDrive.gyroTankDrive();
	}
	
	public void intake()
	{
		if(leftStick.getRawButton(IN_BUTTON))
		{
			myIntake.intake();
		}
		else if(leftStick.getRawButton(OUT_BUTTON))
		{
			myIntake.dispense();
		}
		else if(leftStick.getRawButton(4))
		{
			myIntake.suck();
		}
		else
		{
			myIntake.stop();
		}
	}
	
	public void shooter()
	{
		if(rightStick.getTrigger())
		{
			myShooter.shoot(1);
		}
		
		else if(rightStick.getRawButton(4))
		{
			myShooter.counter();
		}
		else if(rightStick.getRawButton(5))
		{
			myShooter.clockwise();
		}
		/*if(rightStick.getRawButton(4))
		{
			myShooter.reset();
		}*/
	}
	
	public void arm()
	{
		while(rightStick.getRawButton(UP_BUTTON))
		{
			myArm.up();
		}
		myArm.stop();
		
		while(rightStick.getRawButton(DOWN_BUTTON))
		{
			myArm.down();
		}
		myArm.stop();
	}
}

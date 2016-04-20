/*package org.usfirst.frc.team1672.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class Shooter 
{
	private SpeedController shooter;
	private Encoder myEncoder;
	private PIDController myPID;
	
	private static final double kp = .001;
	private static final double ki = .00;
	private static final double kd = .001;
	private static final double TOLERANCE = 10;
	private static final double RESET_POINT = 0.0;
	private static final double INTAKE_POINT = 50; //this value needs to be changed

	private final double STOP_MOTOR = 0; //stop motor
	private final double SHOOT_LENGTH = .5; //time to shoot a ball in seconds
	
	private boolean isReset = true;
	
	private static java.util.Timer myTimer = new Timer();
	
	public Shooter(SpeedController init, Encoder initEncoder)
	{
		shooter = init;
		myEncoder = initEncoder;
		myEncoder.setPIDSourceType(PIDSourceType.kRate);
		myPID = new PIDController(kp,ki,kd,myEncoder,shooter);
		
		myPID.setAbsoluteTolerance(TOLERANCE);
		myPID.reset();
		myPID.enable();
		myPID.setContinuous(false);
	}
	
	public void setShoot()
	{
		myPID.setSetpoint(120);
	}
	
	public void reset() //reposition arm for a shot
	{
		myPID.setSetpoint(RESET_POINT);
		isReset = true;
	}
	
	public void toIntakePoint()
	{
		isReset = false;
		myPID.setSetpoint(INTAKE_POINT);
	}
	
	public boolean isReset()
	{
		return isReset;
	}
	
	public void stop()
	{
		shooter.set(0);
	}
	
	public void shoot(double speed)
	{
		isReset = false;
		shooter.set(speed);
		
		myPID.disable();
		
		myTimer.schedule(new TimerTask() 
		{
			  @Override
			  public void run()
			  {
				   shooter.set(STOP_MOTOR);
				   myPID.enable();
			  }
		}, (long) (SHOOT_LENGTH*1000));
	}
	
	public void dispense()
	{
		isReset = false;
	}
}*/


package org.usfirst.frc.team1672.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class Shooter 
{
	private SpeedController shooter;

	private final double STOP_MOTOR = 0; //stop motor
	
	
	private boolean isReset = true;
	
	private static java.util.Timer myTimer = new Timer();
	
	private Intake myIntake;
	
	public Shooter(SpeedController init, Intake init2)
	{
		shooter = init;
		myIntake = init2;
	}
	
	public void reset() //reposition arm for a shot
	{
		isReset = true;
	}
	
	public boolean isReset()
	{
		return isReset;
	}
	
	public void stop()
	{
		shooter.set(0);
	}
	
	public void clockwise()
	{
		shooter.set(.15);
	}
	
	public void counter()
	{
		shooter.set(-.15);
	}
	
	public void shoot(double speed)
	{
		isReset = false;
		shooter.set(speed);
		
		myTimer.schedule(new TimerTask() 
		{
			  @Override
			  public void run()
			  {
				   myIntake.dispense();
			  }
		}, (long) (3000)); //1000 is half the time for the shot
		
		myTimer.schedule(new TimerTask() 
		{
			  @Override
			  public void run()
			  {
				  shooter.set(STOP_MOTOR);
			  }
		}, (long) (2000)); //1000 is half the time for the shot
	}
}


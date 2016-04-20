
package org.usfirst.frc.team1672.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;
    Joystick leftStick,rightStick;
    SendableChooser chooser;

    Shooter myShooter;
    Intake myIntake;
    Talon left,right;
    VictorSP arm, shooter;
    VictorSP leftIntake,rightIntake;
    Arm myArm;
    Encoder shooterEncoder;
    
    AxisCamera axisCam;
    CameraServer usbCam;

    public Robot() {
    	
    	left = new Talon(0);
    	right = new Talon(1);
    	left.setInverted(true);
    	right.setInverted(true);
    	
        myRobot = new RobotDrive(left, right);
        myRobot.setExpiration(0.1);
        leftStick = new Joystick(1);
        rightStick = new Joystick(0);
    	
    	shooter = new VictorSP(2);
    	arm = new VictorSP(3);
    	
    	leftIntake = new VictorSP(4);
    	rightIntake = new VictorSP(5);
    	
    	shooterEncoder = new Encoder(0,1, true, CounterBase.EncodingType.k4X); 
        shooterEncoder.setPIDSourceType(PIDSourceType.kRate);
    	
        myIntake = new Intake(leftIntake, rightIntake);
        myShooter = new Shooter(shooter, myIntake);
        myArm = new Arm(arm);
        
        usbCam = CameraServer.getInstance();
		usbCam.setQuality(25); 
		usbCam.startAutomaticCapture("cam0");
		
		axisCam = new AxisCamera("10.16.72.11");
		axisCam.writeMaxFPS(20);
		axisCam.writeResolution(Resolution.k320x240);
		axisCam.writeCompression(50);
		
		
        
    }
    
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addObject("Drive FWD Under Low Bar", "lowbar");
        chooser.addDefault("Do Nothing", "doNothing");
        chooser.addObject("Fast over Rough Terrain", "roughterrain");
        SmartDashboard.putData("Auto modes", chooser);
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous()
    {
    	String chosenAuto = (String) chooser.getSelected();
    	boolean running = true;
    	myRobot.setSafetyEnabled(false);
    	while(running && isAutonomous() && isEnabled())
    	{
    		if(chosenAuto.equals("doNothing"))
    		{
    			
    		}
    		else if(chosenAuto.equals("lowbar"))
    		{
    			myRobot.tankDrive(-.8, -.8);
    			Timer.delay(4);
    			myRobot.tankDrive(0, 0);
    		}
    		else if(chosenAuto.equals("lowbarShoot"))
    		{
    			myRobot.tankDrive(.4, .4);
    			Timer.delay(5.5);
    			
    			myRobot.tankDrive(.5, -.5); //turn right towards goal
    			Timer.delay(1);
    			
    			myRobot.tankDrive(.4, .4); //drive fwds towards goal
    			Timer.delay(3);
    			
    			myRobot.tankDrive(0, 0);
    			myShooter.shoot(.3);
    			
    		}
    		else if(chosenAuto.equals("roughterrain"))
    		{
    			myRobot.tankDrive(.65, .65);
    			Timer.delay(4);
    			myRobot.tankDrive(0, 0);
    		}
    		running = false;
    	}
    	myRobot.setSafetyEnabled(true);
    }
    
    final int IN_BUTTON = 2;
    final int OUT_BUTTON = 3;
    final int DOWN_BUTTON = 2;
    final int UP_BUTTON = 3;
    final double LONG_SPEED = 1.0;
    
    Thread shooterThread = new Thread()
    		{
    public void run()
    {
    	if(rightStick.getTrigger())
		{
			myShooter.shoot(.3);
		}
    	else if(leftStick.getTrigger())
    	{
    		myShooter.shoot(.3);
    	}
		else if(rightStick.getRawButton(4))
		{
			myShooter.counter();
		}
		else if(rightStick.getRawButton(5))
		{
			myShooter.clockwise();
		}
		else
		{
			myShooter.stop();
		}
    	}
    };

    Thread intakeThread = new Thread()
    		{
    	public void run()
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
    		};
    		
    		/*Thread armThread = new Thread()
    				{
    			public void run()
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
    				};*/
    				
    				
    Thread driveThread = new Thread()
    {
    	public void run()
    	{
    		myRobot.tankDrive(leftStick.getY(),rightStick.getY());
    	}
    };
    				
    public void disabled()
    {
    	myRobot.setSafetyEnabled(true);
    	myRobot.tankDrive(0, 0);
    	myIntake.stop();
    	myArm.stop();
    	myShooter.stop();
    }
    
    
    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl()
    {
    	
    	myRobot.setSafetyEnabled(true);
    	
    	while(isEnabled())
    	{
    		driveThread.run();
            intakeThread.run();
            //armThread.run();
            shooterThread.run();
    	}
        
            
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}

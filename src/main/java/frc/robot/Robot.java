// Copyright (c) FIRST and other WPILib contributors. 
// Open Source Software; you can modify and/or share it under the terms of 
// the WPILib BSD license file in the root directory of this project. 

 
 

package frc.robot; 

import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; 
import edu.wpi.first.wpilibj.XboxController; 
import edu.wpi.first.wpilibj.TimedRobot; 
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
 

/** 
 * The VM is configured to automatically run this class, and to call the functions corresponding to 
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or 
 * the package after creating this project, you must also update the build.gradle file in the 
 * project. 
 */ 

public class Robot extends TimedRobot {

  public static XboxController mXboxController; 
  public static DifferentialDrive mDifferentialDrive; 

  private static WPI_TalonSRX frontRightDrive; 
  private static WPI_TalonSRX frontLeftDrive; 
  private static WPI_TalonSRX backRightDrive;
  private static WPI_TalonSRX backLeftDrive;
  private static WPI_TalonSRX arm;
  private static WPI_TalonSRX intake;

  private static MotorControllerGroup left;
  private static MotorControllerGroup right;


  /** 
   * This function is run when the robot is first started up and should be used for any 
   * initialization code. 
   */ 

  @Override 

  public void robotInit() {
    mXboxController = new XboxController(0);
    frontRightDrive = new WPI_TalonSRX(11);
    frontLeftDrive = new WPI_TalonSRX(13);
    backLeftDrive = new WPI_TalonSRX(12);
    backRightDrive = new WPI_TalonSRX(14);
    arm = new WPI_TalonSRX(21);
    intake = new WPI_TalonSRX(22);

    right = new MotorControllerGroup(backRightDrive, frontRightDrive);
    left = new MotorControllerGroup(backLeftDrive, frontLeftDrive);
    mDifferentialDrive = new DifferentialDrive(left, right);
  } 

 
 

  /** 
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics 
   * that you want ran during disabled, autonomous, teleoperated and test. 
   * 
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and 
   * SmartDashboard integrated updating. 
   */ 

  @Override 

  public void robotPeriodic() { 

  } 

 
 

  /** 
   * This autonomous (along with the chooser code above) shows how to select between different 
   * autonomous modes using the dashboard. The sendable chooser code works with the Java 
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and 
   * uncomment the getString line to get the auto name from the text box below the Gyro 
   * 
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure 
   * below with additional strings. If using the SendableChooser make sure to add them to the 
   * chooser code above as well. 
   */ 

  @Override 

  public void autonomousInit() {

  } 

 
 

  /** This function is called periodically during autonomous. */ 

  @Override 

  public void autonomousPeriodic() {

  } 

 
 

  /** This function is called once when teleop is enabled. */ 

  @Override 

  public void teleopInit() {} 

 
 

  /** This function is called periodically during operator control. */ 

  @Override 

  public void teleopPeriodic() {
    mDifferentialDrive.tankDrive(mXboxController.getLeftY(), mXboxController.getRightY());
    
    arm.set(mXboxController.getRightTriggerAxis()-mXboxController.getLeftTriggerAxis());
    
    if (mXboxController.getBButton() || mXboxController.getXButton()) { 
      intake.set(-1); //cubeout/conein
    } 
    else if (mXboxController.getYButton() || mXboxController.getAButton()) { 
      intake.set(1); //cubein/coneout 
    }
    else { 
      intake.set(0);
    }
    //cube in is clockwise 
  } 

 
 

  /** This function is called once when the robot is disabled. */ 

  @Override 

  public void disabledInit() {} 

 
 

  /** This function is called periodically when disabled. */ 

  @Override 

  public void disabledPeriodic() {} 

 
 

  /** This function is called once when test mode is enabled. */ 

  @Override 

  public void testInit() {} 

 
 

  /** This function is called periodically during test mode. */ 

  @Override 

  public void testPeriodic() {} 

 
 

  /** This function is called once when the robot is first started up. */ 

  @Override 

  public void simulationInit() {} 

 
 

  /** This function is called periodically whilst in simulation. */ 

  @Override 

  public void simulationPeriodic() {} 

} 

 
 

 
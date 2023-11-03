// Copyright (c) FIRST and other WPILib contributors. 
// Open Source Software; you can modify and/or share it under the terms of 
// the WPILib BSD license file in the root directory of this project. 

 
 

package frc.robot; 

import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; 
import edu.wpi.first.wpilibj.XboxController; 
import edu.wpi.first.wpilibj.TimedRobot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
 
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


/** 
 * The VM is configured to automatically run this class, and to call the functions corresponding to 
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or 
 * the package after creating this project, you must also update the build.gradle file in the 
 * project. 
 */ 

public class Robot extends TimedRobot {

  public static XboxController mXboxController; 
  public static XboxController mXboxController2;
  public static DifferentialDrive mDifferentialDrive; 

  private static CANSparkMax frontRightDrive; 
  private static CANSparkMax frontLeftDrive; 
  private static CANSparkMax backRightDrive;
  private static CANSparkMax backLeftDrive;
  private static CANSparkMax arm;
  private static CANSparkMax intake;

  private static MotorControllerGroup left;
  private static MotorControllerGroup right;

  private double autonStart;
  private static final String nothing = "do nothing";
  private static final String normal = "work as normal";
  private static final String cubeOnly = "just drop off cube";
  private String m_autoSelected;
  private final SendableChooser<String> m_Chooser = new SendableChooser<>();


  /** 
   * This function is run when the robot is first started up and should be used for any 
   * initialization code. 
   */ 

  @Override 

  public void robotInit() {
    mXboxController = new XboxController(0);
    mXboxController2 = new XboxController(1);
    frontRightDrive = new CANSparkMax(14, MotorType.kBrushed);
    frontLeftDrive = new CANSparkMax(13, MotorType.kBrushed);
    backLeftDrive = new CANSparkMax(11, MotorType.kBrushed);
    backRightDrive = new CANSparkMax(12, MotorType.kBrushed);
    arm = new CANSparkMax(21, MotorType.kBrushless);
    intake = new CANSparkMax(22, MotorType.kBrushless);

    right = new MotorControllerGroup(backRightDrive, frontRightDrive);
    right.setInverted(true);
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
    //System.out.println(arm.getEncoder().getPosition());
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
    autonStart = Timer.getFPGATimestamp();

    m_autoSelected = m_Chooser.getSelected();

    mDifferentialDrive.tankDrive(0, 0);
    arm.set(0);
    intake.set(0);
  } 

 
 

  /** This function is called periodically during autonomous. */ 

  @Override 

  public void autonomousPeriodic() {
    double autonElapsed = Timer.getFPGATimestamp()-autonStart;

    switch(cubeOnly) {
      case nothing:
        autoNothing();

      case normal:
        autoNormal(autonElapsed);

      case cubeOnly:
        autoCubeOnly(autonElapsed);
      }
    }
    
  /** This function is called once when teleop is enabled. */ 

  @Override 

  public void teleopInit() {} 

 
 

  /** This function is called periodically during operator control. */ 

  @Override 

  public void teleopPeriodic() {
    double leftSpeed = mXboxController.getLeftY()*Math.abs(mXboxController.getLeftY());
    double rightSpeed = mXboxController.getRightX()*Math.abs(mXboxController.getRightX());
    mDifferentialDrive.arcadeDrive(leftSpeed, rightSpeed);

    
    if (mXboxController.getLeftBumper() && !mXboxController.getRightBumper() && arm.getEncoder().getPosition() < 0.7) {
      arm.set(Constants.MotorSpeeds.armSpeed);
    }
    else if (mXboxController.getRightBumper() && !mXboxController.getLeftBumper() && arm.getEncoder().getPosition() > -30) {
      arm.set(-Constants.MotorSpeeds.armSpeed);
    }
    else {
      arm.set(0);
    }
    
    if (mXboxController2.getBButton() || mXboxController2.getXButton()) { 
      intake.set(-Constants.MotorSpeeds.intakeSpeed); //cubeout/conein
    } 
    else if (mXboxController2.getYButton() || mXboxController2.getAButton()) { 
      intake.set(Constants.MotorSpeeds.intakeSpeed); //cubein/coneout 
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


  public void autoNothing() {
    intake.set(0);
    arm.set(0);
    mDifferentialDrive.tankDrive(0,0);
  }

  public void autoNormal(double time) {
    // needs fine tuning
    
    if (time < 0.5) {
      mDifferentialDrive.tankDrive(0, 0);
      intake.set(0);
      arm.set(-(Constants.MotorSpeeds.armSpeed));
    }
    else if (time < 1.15) {
      arm.set(0);
      intake.set(0);
      mDifferentialDrive.tankDrive(0.8, 0.8);
    }
    else if (time < 1.4) {
      mDifferentialDrive.tankDrive(0, 0);
      arm.set(0);
      intake.set(-(Constants.MotorSpeeds.intakeSpeed));
    }
    else if (time < 2.5) {
      arm.set(0);
      intake.set(0);
      mDifferentialDrive.tankDrive(-0.8, -0.8);
    }
    else if (time < 3) {
      mDifferentialDrive.tankDrive(0, 0);
      intake.set(0);
      arm.set(Constants.MotorSpeeds.armSpeed);
    }
    else if (time < 3.5) {
      arm.set(0);
      intake.set(0);
      mDifferentialDrive.tankDrive(0.8, -0.7);
    }
    else if (time < 4.5) {
      arm.set(0);
      intake.set(0);
      mDifferentialDrive.tankDrive(-0.8, -0.8);
    }
    else {
      arm.set(0);
      intake.set(0);
      mDifferentialDrive.tankDrive(0, 0);
    }
  }

  public void autoCubeOnly(double time) {
    System.out.println(time);
    if (time < 1.8) {
      mDifferentialDrive.tankDrive(0, 0);
      intake.set(0);
      arm.set(-(Constants.MotorSpeeds.armSpeed));
    }
    else if (time < 2.4){
      arm.set(0);
      intake.set(0);
      mDifferentialDrive.tankDrive(-0.8,-0.8);
    }
    else if (time < 2.9) {
      mDifferentialDrive.tankDrive(0, 0);
      arm.set(0);
      intake.set(0);
    }
    else if (time < 3.9) {
      mDifferentialDrive.tankDrive(0, 0);
      arm.set(0);
      intake.set(-(Constants.MotorSpeeds.intakeSpeed));
    }
    else if (time < 5) {
      arm.set(0);
      intake.set(0);
      mDifferentialDrive.tankDrive(0.8, 0.8);
    }
    else {
      intake.set(0);
      arm.set(0);
      mDifferentialDrive.tankDrive(0,0);
    }
  }
}
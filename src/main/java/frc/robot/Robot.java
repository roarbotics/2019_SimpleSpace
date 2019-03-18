/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /*
   * These are the limits for the linear actuators. If you think these are wrong,
   * throw a System.out.println(leftPot.value()) or rightPot.value() somewhere and
   * see where they really want to be, then change these.
   */
  int LEFT_MAX = 3000;
  int RIGHT_MAX = 3000;

  int LEFT_MIN = 500;
  int RIGHT_MIN = 500;

  /*
   * These are all of the drive motors. The values they are set are the CAN IDs.
   * You can check them in the Phoenix Tuner software, and use the light function
   * to see which one is connected where.
   */
  WPI_TalonSRX leftDriveMotor0 = new WPI_TalonSRX(1);
  WPI_TalonSRX leftDriveMotor1 = new WPI_TalonSRX(3);

  WPI_TalonSRX rightDriveMotor0 = new WPI_TalonSRX(0);
  WPI_TalonSRX rightDriveMotor1 = new WPI_TalonSRX(2);

  // Differential Drive, this is just for the arcade drive.
  DifferentialDrive drive;

  /*
   * These are the two linear actuators, you can use phoenix tuner again to see
   * which is connected where.
   */
  WPI_TalonSRX leftActuator = new WPI_TalonSRX(4);
  WPI_TalonSRX rightActuator = new WPI_TalonSRX(5);

  /*
   * Analog inputs, for pressure you need to use the equation on REV robotic's
   * website to get pressure from voltage The other two are for the linear
   * actuators, they just use the raw 12 bit value (0-4095)
   */
  AnalogInput pressure = new AnalogInput(0);
  AnalogInput leftPot = new AnalogInput(1);
  AnalogInput rightPot = new AnalogInput(2);

  /*
   * Solenoid for the pneumatic system,
   */
  DoubleSolenoid solenoid = new DoubleSolenoid(0, 1);

  // Joystick
  Joystick stick = new Joystick(0);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    // The differential drive only uses two motors, so the second motor of each side just follows the first.
    leftDriveMotor1.follow(leftDriveMotor0);
    rightDriveMotor1.follow(rightDriveMotor0);

    // Create the differential drive. You should never have to set a motor to a value with this.
    drive = new DifferentialDrive(leftDriveMotor0, rightDriveMotor0);

    // Configure current limits for the linear actuators
    // These will draw a ton of power if bottomed out so the 
    // current limits help stop that. Instead of 40 amps they
    // will draw like 2. Change these higher if they don't want
    // to move. (don't go above 10)
    leftActuator.configContinuousCurrentLimit(1);
    leftActuator.enableCurrentLimit(true);
    rightActuator.configContinuousCurrentLimit(2);
    rightActuator.enableCurrentLimit(true);
    
    // start camera
    CameraServer.getInstance().startAutomaticCapture();

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Aracade drive with stick. Change these axis to be what you want
    // Look at driver station for list of axis.
    drive.arcadeDrive(stick.getRawAxis(1), stick.getRawAxis(0));

    // linear actuator logic
    if (stick.getRawButton(4)) {
      // if below the max, move forward
      if (leftPot.getValue() < LEFT_MAX)
        leftActuator.set(.1);
      if (rightPot.getValue() < RIGHT_MAX)
        rightActuator.set(.1);
    } else if (stick.getRawButton(5)) {
      // if above the min, move backwards
      if (leftPot.getValue() > LEFT_MIN)
        leftActuator.set(-.1);
      if (rightPot.getValue() > RIGHT_MIN)
        rightActuator.set(-.1);
    }

    // pneumatic logic
    if (stick.getRawButton(1)) {
      // if button pushed, extend
      solenoid.set(DoubleSolenoid.Value.kForward);
    } else {
      // if not, dont
      solenoid.set(DoubleSolenoid.Value.kForward);
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    drive.arcadeDrive(stick.getRawAxis(1), stick.getRawAxis(0));

    if (stick.getRawButton(4)) {
      if (leftPot.getValue() < LEFT_MAX)
        leftActuator.set(.1);
      if (rightPot.getValue() < RIGHT_MAX)
        rightActuator.set(.1);
    } else if (stick.getRawButton(5)) {
      if (leftPot.getValue() > LEFT_MIN)
        leftActuator.set(-.1);
      if (rightPot.getValue() > RIGHT_MIN)
        rightActuator.set(-.1);
    }

    if (stick.getRawButton(1)) {
      solenoid.set(DoubleSolenoid.Value.kForward);
    } else {
      solenoid.set(DoubleSolenoid.Value.kForward);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

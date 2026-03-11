// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final Spark feeder = new Spark(2);
  private final Spark shooter1 = new Spark(0);
  private final Spark shooter2 = new Spark(1);

  private final VictorSPX m_leftLead = new VictorSPX(1);
  private final VictorSPX m_leftFollow = new VictorSPX(2);
  private final VictorSPX m_rightLead = new VictorSPX(3);
  private final VictorSPX m_rightFollow = new VictorSPX(4);
  
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(this::setLeft, this::setRight);
      
  private final PS4Controller m_stick = new PS4Controller(0);

  private void setLeft(double power) {
    m_leftLead.set(VictorSPXControlMode.PercentOutput, power);
  }
   private void setRight(double power) {
    m_rightLead.set(VictorSPXControlMode.PercentOutput, power);
  }

  /** Called once at the beginning of the robot program. */
  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftLead);
    SendableRegistry.addChild(m_robotDrive, m_rightLead);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightLead.setInverted(true);
    m_rightFollow.setInverted(true);
    m_rightFollow.follow(m_rightLead);
    m_leftFollow.follow(m_leftLead);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.

    m_robotDrive.arcadeDrive(-m_stick.getLeftY(), -m_stick.getLeftX());

    if(m_stick.getCircleButton()) {
      shooter1.set(-0.5);
      shooter2.set(-0.5);
      feeder.set(-0.5);
    } else{
      if (m_stick.getR1Button()) {
        feeder.set(0.8);
      } else {
        feeder.set(0.);
      }
      if (m_stick.getL1Button()) {
        shooter1.set(0.9);
        shooter2.set(0.9);
      } else {
        shooter1.set(0.);
        shooter2.set(0.);
      }
    }

  }
}

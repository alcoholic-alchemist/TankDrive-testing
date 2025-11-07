// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TestTalonFXSubsystem extends SubsystemBase {
  private final TalonFX kraken;
  private final TalonFXConfiguration configuration;
  /** Creates a new TestTaloxFXSubsystem. */
  public TestTalonFXSubsystem() {
    this.configuration = new TalonFXConfiguration();
    this.configuration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    kraken = new TalonFX(Constants.MotorConstants.krakenTestPracticeID);
    kraken.getConfigurator().apply(this.configuration);
  }

  public void krakenRunMotor(double speed) {
    kraken.set(speed);
  }

  public void krakenStopMotor() {
    kraken.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

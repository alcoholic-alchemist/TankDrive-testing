// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AttachmentSubsystem extends SubsystemBase {
  SparkMax attachmentSparkMax = new SparkMax(6, MotorType.kBrushless);
  SparkMaxConfig attachmentSparkMaxConfig = new SparkMaxConfig();
  private final double GEAR_RATIO = 8.1;

  RelativeEncoder encoder = attachmentSparkMax.getEncoder();
  /** Creates a new AttachmentSubsystem. */
  public AttachmentSubsystem() {
    attachmentSparkMaxConfig.inverted(false).idleMode(IdleMode.kBrake);
    attachmentSparkMax.configure(attachmentSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void turn(double betweenNeg1and1) {
    attachmentSparkMax.set(betweenNeg1and1);
  }

  public void stopMotor() {
    attachmentSparkMax.stopMotor();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

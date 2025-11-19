// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AttachmentSubsystem extends SubsystemBase {
  private final SparkMax attachmentSparkMax = new SparkMax(6, MotorType.kBrushless);
  private final SparkMaxConfig attachmentSparkMaxConfig = new SparkMaxConfig();
  private final double GEAR_RATIO = 8.1d;
  private SparkClosedLoopController loopController;

  /** Creates a new AttachmentSubsystem. */
  public AttachmentSubsystem() {
    attachmentSparkMaxConfig.inverted(false).idleMode(IdleMode.kBrake);
    attachmentSparkMaxConfig.encoder.positionConversionFactor(360/GEAR_RATIO);
    
    attachmentSparkMaxConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).p(Constants.SpinnerConstants.p).i(Constants.SpinnerConstants.i).d(Constants.SpinnerConstants.d).outputRange(0, .5);
    
    //.apply(
    //  new ClosedLoopConfig().p(Constants.SpinnerConstants.p).i(Constants.SpinnerConstants.i).d(Constants.SpinnerConstants.d).outputRange(0.25, 0.75).velocityFF(0));
    
    attachmentSparkMax.configure(attachmentSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    loopController = attachmentSparkMax.getClosedLoopController();

    attachmentSparkMax.getEncoder().setPosition(0);
  }

  public void turn(double betweenNeg1and1) {
    attachmentSparkMax.set(betweenNeg1and1);
    SmartDashboard.putNumber("Attatchment motor speed", betweenNeg1and1);
  }

  public void stopMotor() {
    attachmentSparkMax.stopMotor();
  }

  public void setPositionUsingEncoderAndPIDS(double setpoint) {
    attachmentSparkMax.getEncoder().setPosition(0);
    loopController.setReference(setpoint, ControlType.kPosition);
    SmartDashboard.putNumber("Setpoint", setpoint);
    //attachmentSparkMax.stopMotor();
  }

  public void zeroEncoder() {
    attachmentSparkMax.getEncoder().setPosition(0);
  }
  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Encoder Position", attachmentSparkMax.getEncoder().getPosition());
  }
}

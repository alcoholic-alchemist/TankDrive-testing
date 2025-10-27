// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.DriveMode;

public class DriveBaseSubsystem extends SubsystemBase {
  public DriveMode currentDriveMode = DriveMode.TANK;

  SparkMax leftFrontSparkMax = new SparkMax(7, MotorType.kBrushed);
  SparkMax leftBackSparkMax = new SparkMax(8, MotorType.kBrushed);
  
  SparkMaxConfig leftFrontSparkMaxConfig = new SparkMaxConfig();
  SparkMaxConfig leftBackSparkMaxConfig = new SparkMaxConfig();

  SparkMax rightFrontSparkMax = new SparkMax(9, MotorType.kBrushed);
  SparkMax rightBackSparkMax = new SparkMax(10, MotorType.kBrushed);
  
  SparkMaxConfig rightFrontSparkMaxConfig = new SparkMaxConfig();
  SparkMaxConfig rightBackSparkMaxConfig = new SparkMaxConfig();

  DifferentialDrive differentialDrive = new DifferentialDrive(leftFrontSparkMax, rightFrontSparkMax);
  
  public void driveTank(double valueLeft, double valueRight) {
    //differentialDrive.tankDrive(valueLeft, valueRight, true);
    leftFrontSparkMax.set(valueLeft);
    rightFrontSparkMax.set(-valueRight);
  }

  public void driveArcade(double xAxisSpeed, double zAxisRotation) {
   differentialDrive.arcadeDrive(xAxisSpeed, zAxisRotation, true);
  }

  public void stopMotors() {
    rightFrontSparkMax.stopMotor();
    rightBackSparkMax.stopMotor();
    leftFrontSparkMax.stopMotor();
    leftBackSparkMax.stopMotor();
  }

  public DriveBaseSubsystem() {rightFrontSparkMaxConfig.inverted(false).idleMode(IdleMode.kBrake);
    rightFrontSparkMax.configure(rightFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
    rightBackSparkMaxConfig.inverted(true).idleMode(IdleMode.kBrake).follow(9);
    rightBackSparkMax.configure(rightFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
    leftFrontSparkMaxConfig.inverted(false).idleMode(IdleMode.kBrake);
    leftFrontSparkMax.configure(leftFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    leftBackSparkMaxConfig.inverted(true).idleMode(IdleMode.kBrake).follow(7);
    leftBackSparkMax.configure(leftFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  } 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

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
import frc.robot.Constants;
import frc.robot.enums.DriveMode;

public class DriveBaseSubsystem extends SubsystemBase {
  public DriveMode currentDriveMode = DriveMode.TANK;

  SparkMax leftMasterSM;
  SparkMax leftFollowerSM;
  
  SparkMaxConfig leftMasterConfig = new SparkMaxConfig();
  SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();

  SparkMax rightMasterSM;
  SparkMax rightFollowerSM;
  
  SparkMaxConfig rightMasterConfig = new SparkMaxConfig();
  SparkMaxConfig rightFollowerConfig;

  DifferentialDrive differentialDrive;
  
  public void driveTank(double valueLeft, double valueRight) {
    //differentialDrive.tankDrive(valueLeft, valueRight, true);
    leftMasterSM.set(valueLeft);
    rightMasterSM.set(valueRight);
  }

  public void driveArcade(double xAxisSpeed, double zAxisRotation) {
   //differentialDrive.arcadeDrive(xAxisSpeed, zAxisRotation, true);
   leftMasterSM.set(xAxisSpeed + zAxisRotation);
   rightMasterSM.set(xAxisSpeed - zAxisRotation);
  }

  public void moveMotors(double valueLeft, double valueRight) {
    leftMasterSM.set(valueLeft);
    rightMasterSM.set(valueRight);
  }

  public void stopMotors() {
    rightMasterSM.stopMotor();
    rightFollowerSM.stopMotor();
    leftMasterSM.stopMotor();
    leftFollowerSM.stopMotor();
  }

  public DriveBaseSubsystem() {
    leftMasterConfig = new SparkMaxConfig();
    leftFollowerConfig = new SparkMaxConfig();

    rightMasterConfig = new SparkMaxConfig();
    rightFollowerConfig = new SparkMaxConfig();

    leftMasterSM = new SparkMax(Constants.MotorConstants.leftDriveMasterSM, MotorType.kBrushed);
    leftFollowerSM = new SparkMax(Constants.MotorConstants.leftDriveSlaveSM, MotorType.kBrushed);

    rightMasterSM = new SparkMax(Constants.MotorConstants.rightDriveMasterSM, MotorType.kBrushed);
    rightFollowerSM = new SparkMax(Constants.MotorConstants.rightDriveSlaveSM, MotorType.kBrushed);

    rightMasterConfig.inverted(true).idleMode(IdleMode.kBrake);
    rightMasterSM.configure(rightMasterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    rightFollowerConfig.idleMode(IdleMode.kBrake).follow(rightMasterSM);
    rightFollowerSM.configure(rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
    leftMasterConfig.inverted(false).idleMode(IdleMode.kBrake);
    leftMasterSM.configure(leftMasterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    leftFollowerConfig.idleMode(IdleMode.kBrake).follow(leftMasterSM);
    leftFollowerSM.configure(leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    //differentialDrive = new DifferentialDrive(leftMasterSM, rightMasterSM);

    /*rightFrontSparkMaxConfig.inverted(false).idleMode(IdleMode.kBrake);
    rightFrontSparkMax.configure(rightFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
    rightBackSparkMaxConfig.inverted(true).idleMode(IdleMode.kBrake).follow(9);
    rightBackSparkMax.configure(rightFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
    leftFrontSparkMaxConfig.inverted(false).idleMode(IdleMode.kBrake);
    leftFrontSparkMax.configure(leftFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    leftBackSparkMaxConfig.inverted(true).idleMode(IdleMode.kBrake).follow(7);
    leftBackSparkMax.configure(leftFrontSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);*/
  } 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

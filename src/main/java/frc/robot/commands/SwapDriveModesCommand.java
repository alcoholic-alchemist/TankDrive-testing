// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.enums.DriveMode;
import frc.robot.subsystems.DriveBaseSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SwapDriveModesCommand extends InstantCommand {
  private final DriveBaseSubsystem subsystem;
  public SwapDriveModesCommand(DriveBaseSubsystem subsystem) {
    this.subsystem = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (subsystem.currentDriveMode) {
      case TANK:
        subsystem.currentDriveMode = DriveMode.ARCADE_SPLIT;
        break;
      case ARCADE_SPLIT:
        subsystem.currentDriveMode = DriveMode.ARCADE_COMBINE;
        break;
      case ARCADE_COMBINE:
        subsystem.currentDriveMode = DriveMode.TANK;
        break;
      default:
        subsystem.currentDriveMode = DriveMode.TANK;
        break;
    }
  }
}

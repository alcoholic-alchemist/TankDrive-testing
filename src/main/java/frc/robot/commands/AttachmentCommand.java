// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AttachmentSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AttachmentCommand extends Command {
  public final AttachmentSubsystem attachmentSubsystem;
  public final DoubleSupplier speed;
  /** Creates a new AttachmentCommand. */
  public AttachmentCommand(AttachmentSubsystem subsystem, DoubleSupplier speed) {
    this.attachmentSubsystem = subsystem;
    this.speed = speed;
    addRequirements(this.attachmentSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Attempting to initialize attachment motor...");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    attachmentSubsystem.turn(speed.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    attachmentSubsystem.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

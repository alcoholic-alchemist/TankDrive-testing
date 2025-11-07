// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TestTalonFXSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class GetKrakenCommand extends Command {
  private final TestTalonFXSubsystem subsystem;
  private final DoubleSupplier speedSupplier;

  /** Creates a new GetKrakenCommand. */
  public GetKrakenCommand(TestTalonFXSubsystem subsystem, DoubleSupplier speedSupplier) {
    this.subsystem = subsystem;
    this.speedSupplier = speedSupplier;
    
    addRequirements(this.subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Lets get kraken...");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    subsystem.krakenRunMotor(speedSupplier.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

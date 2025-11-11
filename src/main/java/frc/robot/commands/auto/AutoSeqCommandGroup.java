// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AttachmentCommand;
import frc.robot.commands.DriveTankCommand;
import frc.robot.subsystems.AttachmentSubsystem;
import frc.robot.subsystems.DriveBaseSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more21
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoSeqCommandGroup extends SequentialCommandGroup {
  /** Creates a new AutoSeqCommandGroup. */
  public AutoSeqCommandGroup(DriveBaseSubsystem driveBaseSubsystem, AttachmentSubsystem attachmentSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveTankCommand(driveBaseSubsystem, () -> 0.3, () -> 0.3).withTimeout(1),
      new ParallelCommandGroup(
        new AttachmentCommand(attachmentSubsystem, () -> 0.75), new DriveTankCommand(driveBaseSubsystem, () -> 0.5, () -> -0.5)
      ).withTimeout(0.5)
    );
  }
}

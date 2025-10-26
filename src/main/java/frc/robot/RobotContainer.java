// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.AttachmentCommand;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveArcadeCommand;
import frc.robot.commands.DriveTankCommand;
import frc.robot.commands.SwapDriveModesCommand;
import frc.robot.enums.DriveMode;
import frc.robot.subsystems.AttachmentSubsystem;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  //Controllers (if switching to CommandJoystick breaks it revert to Joystick)
  //private final Joystick joystickLeft = new Joystick(4);
  //private final Joystick joystickRight = new Joystick(5);
  private final CommandJoystick joystickLeft;
  private final CommandJoystick joystickRight;

  //Triggers and Buttons
  //private final JoystickButton triggerButton;

  //Subsystems
  private final DriveBaseSubsystem driveBaseSubsystem;
  private final AttachmentSubsystem attachmentSubsystem;

  //Commands
  private final AttachmentCommand attachmentCommand;
  private final DriveTankCommand driveTankCommand;
  private final DriveArcadeCommand splitArcadeCommand; 
  private final DriveArcadeCommand combinedArcadeCommand;
  private final SwapDriveModesCommand swapDriveModesCommand;
  
  public RobotContainer() {
    //Initialize controllers
    joystickLeft = new CommandJoystick(4);
    joystickRight = new CommandJoystick(5);

    //Initialize triggers and buttons
    //triggerButton = new JoystickButton(joystickRight, 1);

    //Initialize subsystems
    driveBaseSubsystem = new DriveBaseSubsystem();
    attachmentSubsystem = new AttachmentSubsystem();

    //Initialize commands
    attachmentCommand = new AttachmentCommand(attachmentSubsystem, () -> 0.5);
    
    driveTankCommand = new DriveTankCommand(driveBaseSubsystem, 
    () -> MathUtil.applyDeadband(joystickLeft.getY(), 0.2), 
    () -> MathUtil.applyDeadband(joystickRight.getY(), 0.2));
    
    splitArcadeCommand = new DriveArcadeCommand(driveBaseSubsystem,
    () -> MathUtil.applyDeadband(joystickLeft.getY(), 0.2), 
    () -> MathUtil.applyDeadband(joystickRight.getX(), 0.2));
    
    combinedArcadeCommand = new DriveArcadeCommand(driveBaseSubsystem, 
    () -> MathUtil.applyDeadband(joystickLeft.getY(), 0.2), 
    () -> MathUtil.applyDeadband(joystickLeft.getX(), 0.2));

    swapDriveModesCommand = new SwapDriveModesCommand(driveBaseSubsystem);
   
    //Interior commands and subsystemX.setDefaultCommand(y)
    configureCommandsAndSubsystems();
    
    // Configure the trigger bindings
    configureBindingsAndTriggers();
  }

  private void configureBindingsAndTriggers() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`    
    //drivebase.setDefaultCommand(new DriveCommand(drivebase, () -> joystickLeft.getY(), () -> joystickRight.getY()));
    //drivebase.setDefaultCommand(new DriveCommand(drivebase, leftTrigger.tr, xboxController.rightTrigger(0.05, null));
    //drivebaseLeft.setDefaultCommand(new LeftDrive(drivebaseLeft, new XboxController(0).getLeftTriggerAxis()));
    //driveBaseSubsystem.setDefaultCommand(new DriveTankCommand(driveBaseSubsystem, () -> deadband(joystickLeft.getY(), 0.2), () -> deadband(joystickRight.getY(), 0.2)));
    
    joystickLeft.button(1).whileTrue(attachmentCommand);
    joystickLeft.button(4).onTrue(swapDriveModesCommand);
  }

  private void configureCommandsAndSubsystems() {
    //DefaultCommands
    attachmentSubsystem.setDefaultCommand(attachmentCommand);

    //driveBaseSubsystem.setDefaultCommand(driveTankCommand);
    driveBaseSubsystem.setDefaultCommand(
      new SelectCommand<>(
      Map.of(
        DriveMode.TANK, driveTankCommand, 
        DriveMode.ARCADE_COMBINE, combinedArcadeCommand,
        DriveMode.ARCADE_SPLIT, splitArcadeCommand),
      () -> driveBaseSubsystem.currentDriveMode));
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(new ExampleSubsystem());
  }

  @Deprecated //I know I just made this
  private static double deadband(double input, double threshold) {
    if (input > threshold || input < -threshold) {
      return input;
    } return 0.0;
  }

}

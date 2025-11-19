// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AttachmentCommand;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveArcadeCommand;
import frc.robot.commands.DriveTankCommand;
import frc.robot.commands.EncoderMoveCommand;
import frc.robot.commands.GetKrakenCommand;
import frc.robot.commands.SwapDriveModesCommand;
import frc.robot.commands.TurnAroundCommand;
import frc.robot.commands.auto.AutoSeqCommandGroup;
import frc.robot.enums.DriveMode;
import frc.robot.subsystems.AttachmentSubsystem;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.TestTalonFXSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  //Controllers
  private final CommandJoystick joystickLeft;
  private final CommandJoystick joystickRight;
  private final CommandXboxController xboxController;

  //Triggers and Buttons
  private final Trigger rightJoystickTrigger;
  private final Trigger rightThumbButton;
  private final Trigger right3Button;
  private final Trigger right4Button;
  private final Trigger right5Button;

  private final Trigger leftJoystickTrigger;
  private final Trigger leftThumbButton;
  private final Trigger left3Button;
  private final Trigger left4Button;
  private final Trigger left5Button;

  //Subsystems
  private final DriveBaseSubsystem driveBaseSubsystem;
  private final AttachmentSubsystem attachmentSubsystem;
  private final TestTalonFXSubsystem testKrakenSubsystem;

  //Commands
  private final AttachmentCommand attachmentCommand50;
  private final AttachmentCommand attachmentCommand75;
  private final DriveTankCommand driveTankCommand;
  private final DriveArcadeCommand splitArcadeCommand; 
  private final DriveArcadeCommand combinedArcadeCommand;
  private final SwapDriveModesCommand swapDriveModesCommand;
  private final TurnAroundCommand turn180DegreesCommand;
  private final GetKrakenCommand testKrakenCommand;

  ParallelDeadlineGroup turn180Group;

  //DIO
  private final DigitalInput breakBeamSensor = new DigitalInput(0);
  
  public RobotContainer() {
    //Initialize controllers
    joystickLeft = new CommandJoystick(Constants.ControllerConstants.leftJoystickID);
    joystickRight = new CommandJoystick(Constants.ControllerConstants.rightJoystickID);
    xboxController = new CommandXboxController(Constants.ControllerConstants.xboxControllerID);

    //Initialize triggers and buttons
    rightJoystickTrigger = joystickRight.button(1);
    rightThumbButton = joystickRight.button(2);
    right3Button = joystickRight.button(3);
    right4Button = joystickRight.button(4);
    right5Button = joystickRight.button(5);

    leftJoystickTrigger = joystickLeft.button(1);
    leftThumbButton = joystickLeft.button(2);
    left3Button = joystickLeft.button(3);
    left4Button = joystickLeft.button(4);
    left5Button = joystickLeft.button(5);

    //Initialize subsystems
    driveBaseSubsystem = new DriveBaseSubsystem();
    attachmentSubsystem = new AttachmentSubsystem();
    testKrakenSubsystem = new TestTalonFXSubsystem();

    //Initialize commands
    //replace getLeftTrigger with 0.5 if neccesary
    attachmentCommand50 = new AttachmentCommand(attachmentSubsystem, () -> 0.5);
    attachmentCommand75 = new AttachmentCommand(attachmentSubsystem, () -> 0.75);
    
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

    turn180DegreesCommand = new TurnAroundCommand(driveBaseSubsystem);
   // turn180Group = new ParallelDeadlineGroup(new WaitCommand(0.5), turn180DegreesCommand);

    testKrakenCommand = new GetKrakenCommand(testKrakenSubsystem, () -> 0.5);
   
    //Interior commands and subsystemX.setDefaultCommand(y)
    configureCommandsAndSubsystems();
    
    // Configure the trigger bindings
    configureBindingsAndTriggers();
  }

  private void configureBindingsAndTriggers() {
    rightJoystickTrigger.whileTrue(attachmentCommand50);
    leftJoystickTrigger.whileTrue(attachmentCommand75);
    right4Button.toggleOnTrue(attachmentCommand50);
    right5Button.toggleOnTrue(attachmentCommand75);
    left3Button.onTrue(turn180DegreesCommand.withTimeout(0.5));
    right3Button.whileTrue(testKrakenCommand);
    //left4Button.onTrue(new EncoderMoveCommand(attachmentSubsystem));
    left4Button.onTrue(new InstantCommand(() -> attachmentSubsystem.setPositionUsingEncoderAndPIDS(180), attachmentSubsystem));

    //Two Alternate Ways to Swap Drive Mode Test
    leftThumbButton.onTrue(swapDriveModesCommand);
    xboxController.a().onTrue(swapDriveModesCommand);

    new Trigger(() -> breakBeamSensor.get()).whileFalse(attachmentCommand50);
  }

  private void configureCommandsAndSubsystems() {
    //DefaultCommands
    //attachmentSubsystem.setDefaultCommand(attachmentCommand50); //I think this was the issue...

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
    return new AutoSeqCommandGroup(driveBaseSubsystem, attachmentSubsystem);
  }

  @Deprecated //I know I just made this
  private static double deadband(double input, double threshold) {
    if (input > threshold || input < -threshold) {
      return input;
    } return 0.0;
  }

}

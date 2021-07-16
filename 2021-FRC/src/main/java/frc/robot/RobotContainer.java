package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.helpers.FMSManager;

public class RobotContainer {
  SendableChooser<Command> autoChooser = new SendableChooser<>();

  // Classes
  public final FMSManager fms = new FMSManager(DriverStation.getInstance());

  // Controllers
  public final XboxController driverOne = new XboxController(DriveControllers.DriverOne);
  public final XboxController driverTwo = new XboxController(DriveControllers.DriverTwo);

  // SubSystems
  public final subLimeLight limelight = new subLimeLight();
  public final subDrive drive = new subDrive();
  public final subIndexer indexer = new subIndexer();
  public final subIntake intake = new subIntake();
  public final subArm arm = new subArm();
  public final subShooter shooter = new subShooter(limelight);
  public final subTurret turret = new subTurret(limelight);
  public final subClimb climb = new subClimb();
  public final subLeveler leveler = new subLeveler();
  //public final subLED led = new subLED(indexer, shooter, turret, fms);

  public RobotContainer() {
    AddAutoCommands();
    DriverOneFunctions();
    DriverTwoFunctions();
  }
  private void AddAutoCommands(){
    autoChooser.setDefaultOption("Move", new Autonomous_Move(drive, indexer, shooter, turret));  
    autoChooser.addOption("Manual Shoot, Move", new Autonomous_ManualShootMove(drive, indexer, shooter, turret)); 
    autoChooser.addOption("Shoot, Move", new Autonomous_ShootMove(drive, indexer, shooter, turret));  
    autoChooser.addOption("Shoot, Push, Move", new Autonomous_ShootPushMove(drive, indexer, shooter, turret));  
    autoChooser.addOption("Shoot, Move & Collect & Shoot", new Autonomous_ShootMoveCollectMoveShoot(drive, indexer, shooter, turret, intake, arm));
    SmartDashboard.putData("Autonomous", autoChooser);
  }
  private void DriverOneFunctions() {    
    // Default Commands
    drive.setDefaultCommand(new RunCommand(() -> drive.tankDrive(driverOne), drive));
    intake.setDefaultCommand(new RunCommand(() -> intake.TeleOp(driverOne), intake));

    // Leveler
    new JoystickButton(driverOne, Button.kBack.value)
      .whileHeld(new cmdLeveler_TeleOp(leveler, -.8));
    new JoystickButton(driverOne, Button.kStart.value)
      .whileHeld(new cmdLeveler_TeleOp(leveler, .8));
    
    // Drive
    new JoystickButton(driverOne, Button.kB.value)
      .whileHeld(new cmdDrive_LockDrive(drive));

    // Arm
    new JoystickButton(driverOne, Button.kBumperLeft.value)
      .whileHeld(new cmdArm_Raise(arm));
    new JoystickButton(driverOne, Button.kBumperRight.value)
      .whileHeld(new cmdArm_Lower(arm));

    // Testing
    new JoystickButton(driverOne, Button.kX.value)
      .whileHeld(new cmdTurret_AutoAlign(turret));
    new JoystickButton(driverOne, Button.kY.value)
      .whileHeld(new cmdShooter_AutoSpin(shooter));
  }
  private void DriverTwoFunctions() {
    // Default Commands
    climb.setDefaultCommand(new RunCommand(() -> climb.TeleOp(driverTwo), climb));
    turret.setDefaultCommand(new RunCommand(() -> turret.TeleOp(driverTwo), turret));
    shooter.setDefaultCommand(new RunCommand(() -> shooter.TeleOp(driverTwo), shooter));
    
    // Indexer
    new JoystickButton(driverTwo, Button.kX.value)
      .whileHeld(new cmdIndexer_Forward(indexer));
    new JoystickButton(driverTwo, Button.kBack.value)
      .whileHeld(new cmdIndexer_Reverse(indexer));
    new JoystickButton(driverTwo, Button.kBumperLeft.value)
      .toggleWhenPressed(new cmdIndexer_Auto(indexer));

    // Shooter
    new JoystickButton(driverTwo, Button.kBumperRight.value)
      .whileHeld(new cmdAuto_Shoot(shooter, indexer, turret));
    new JoystickButton(driverTwo, Button.kA.value)
      .whileHeld(new cmdTurret_AutoAlign(turret));
    new JoystickButton(driverTwo, Button.kStart.value)
      .whileHeld(new cmdAuto_UnjamShooter(shooter, indexer));
  }  
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
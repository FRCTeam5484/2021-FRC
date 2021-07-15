package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subArm;
import frc.robot.subsystems.subDrive;
import frc.robot.subsystems.subIndexer;
import frc.robot.subsystems.subIntake;
import frc.robot.subsystems.subShooter;
import frc.robot.subsystems.subTurret;

public class Autonomous_ShootMoveCollectMoveShoot extends CommandBase {
  subDrive drive;
  subIndexer indexer;
  subShooter shooter;
  subTurret turret;
  subIntake intake;
  subArm arm;
  Timer time;
  public Autonomous_ShootMoveCollectMoveShoot(subDrive _drive, subIndexer _indexer, subShooter _shooter, subTurret _turret, subIntake _intake, subArm _arm) {
    drive = _drive;
    indexer = _indexer;
    shooter = _shooter;
    turret = _turret;
    intake = _intake;
    arm = _arm;
    addRequirements(drive);
    addRequirements(indexer);
    addRequirements(shooter);
    addRequirements(turret);
    addRequirements(intake);
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    time = new Timer();
    time.start();
  }

  @Override
  public void execute() {
    turret.Auto();
    if(turret.TurretReady())
    {
      shooter.Auto();
      if(shooter.ShooterReady())
      {
        indexer.Forward();
      }
    }
    arm.LowerArm();
    intake.Collect();
    if(time.get() > 4){
      drive.SimpleMove(0.48);
    }
    if(time.get() > 10){
      drive.SimpleMove(0);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stopDrive();
    shooter.Stop();
    indexer.Stop();
    turret.Stop();
    intake.Stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(time.get() > 14){
      return true;
    } else { return false; }
  }
}
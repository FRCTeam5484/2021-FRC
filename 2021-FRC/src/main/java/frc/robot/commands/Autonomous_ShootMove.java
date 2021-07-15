package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subDrive;
import frc.robot.subsystems.subIndexer;
import frc.robot.subsystems.subIntake;
import frc.robot.subsystems.subShooter;
import frc.robot.subsystems.subTurret;

public class Autonomous_ShootMove extends CommandBase {
  subDrive drive;
  subIndexer indexer;
  subShooter shooter;
  subTurret turret;
  subIntake intake;
  Timer time;
  public Autonomous_ShootMove(subDrive _drive, subIndexer _indexer, subShooter _shooter, subTurret _turret) {
    drive = _drive;
    indexer = _indexer;
    shooter = _shooter;
    turret = _turret;
    addRequirements(drive);
    addRequirements(indexer);
    addRequirements(shooter);
    addRequirements(turret);
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
    intake.Collect();
    if(time.get() > 4){
      drive.SimpleMove(0.48);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stopDrive();
    shooter.Stop();
    indexer.Stop();
    turret.Stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(time.get() > 6){
      return true;
    } else { return false; }
  }
}

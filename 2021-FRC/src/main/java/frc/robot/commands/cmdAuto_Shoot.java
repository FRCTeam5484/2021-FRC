package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subIndexer;
import frc.robot.subsystems.subShooter;
import frc.robot.subsystems.subTurret;

public class cmdAuto_Shoot extends CommandBase {
  subShooter shooter;
  subIndexer indexer;
  subTurret turret;
  Timer time = new Timer();
  public cmdAuto_Shoot(subShooter _shooter, subIndexer _indexer, subTurret _turret) {
    shooter = _shooter;
    indexer = _indexer;
    turret = _turret;
    addRequirements(shooter);
    addRequirements(indexer);
    addRequirements(turret);
  }

  @Override
  public void initialize() {
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
  }

  @Override
  public void end(boolean interrupted) {
    shooter.Stop();
    indexer.Stop();
    turret.Stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subIndexer;
import frc.robot.subsystems.subShooter;
import frc.robot.subsystems.subTurret;

public class cmdAuto_ShootManualFeed extends CommandBase {
  subShooter shooter;
  subIndexer indexer;
  subTurret turret;
  Timer time = new Timer();
  public cmdAuto_ShootManualFeed(subShooter _shooter, subTurret _turret) {
    shooter = _shooter;
    turret = _turret;
    addRequirements(shooter);
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
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.Stop();
    turret.Stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

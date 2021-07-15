package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subTurret;

public class cmdTurret_AutoAlign extends CommandBase {
  subTurret turret;
  public cmdTurret_AutoAlign(subTurret _turret) {
    turret = _turret;
    addRequirements(turret);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    turret.Auto();
  }

  @Override
  public void end(boolean interrupted) {
    turret.Stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

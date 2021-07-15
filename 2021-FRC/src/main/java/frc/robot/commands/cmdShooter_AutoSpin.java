package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subShooter;

public class cmdShooter_AutoSpin extends CommandBase {
  subShooter shooter;
  public cmdShooter_AutoSpin(subShooter _shooter) {
    shooter = _shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.Auto();
  }

  @Override
  public void end(boolean interrupted) {
    shooter.Stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

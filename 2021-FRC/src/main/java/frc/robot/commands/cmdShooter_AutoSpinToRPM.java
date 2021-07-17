package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subShooter;

public class cmdShooter_AutoSpinToRPM extends CommandBase {
  subShooter shooter;
  int rpm;
  public cmdShooter_AutoSpinToRPM(int _rpm, subShooter _shooter) {
    rpm = _rpm;
    shooter = _shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.Auto(rpm);
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

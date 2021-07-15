package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subLeveler;

public class cmdLeveler_TeleOp extends CommandBase {
  subLeveler leveler;
  double speed;
  public cmdLeveler_TeleOp(final subLeveler _leveler, final double _speed) {
    leveler = _leveler;
    speed = _speed;
    addRequirements(leveler);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    leveler.TeleOp(speed);
  }

  @Override
  public void end(boolean interrupted) {
    leveler.Stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

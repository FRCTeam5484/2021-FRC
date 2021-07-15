
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subShooter;

public class cmdTest_SpinToRPM extends CommandBase {
  subShooter shooter;
  public cmdTest_SpinToRPM(subShooter _shooter) {
    shooter = _shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.SpinToRPM();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

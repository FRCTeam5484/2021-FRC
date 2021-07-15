package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subIntake;

public class cmdIntake_Eject extends CommandBase {
  subIntake intake;
  public cmdIntake_Eject(subIntake _intake) {
    intake = _intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intake.Eject();
  }

  @Override
  public void end(boolean interrupted) {
    intake.Stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subArm;

public class cmdArm_Raise extends CommandBase {
  subArm arm;
  public cmdArm_Raise(subArm _arm) {
    arm = _arm;
    addRequirements(arm);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    arm.RaiseArm();
  }

  @Override
  public void end(boolean interrupted) {
    arm.StopArm();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

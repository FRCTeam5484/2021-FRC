package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subDrive;

public class cmdDrive_LockDrive extends CommandBase {
  subDrive drive;
  public cmdDrive_LockDrive(subDrive _drive) {
    drive = _drive;
    addRequirements(drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drive.DriveLock();
  }

  @Override
  public void end(boolean interrupted) {
    drive.DriveUnlock();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

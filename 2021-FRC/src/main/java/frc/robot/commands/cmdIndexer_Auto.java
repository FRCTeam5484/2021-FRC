package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subIndexer;

public class cmdIndexer_Auto extends CommandBase {
  subIndexer indexer;
  public cmdIndexer_Auto(subIndexer _indexer) {
    indexer = _indexer;
    addRequirements(indexer);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    indexer.AutoLoadIndexer();
  }

  @Override
  public void end(boolean interrupted) {
    indexer.Stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

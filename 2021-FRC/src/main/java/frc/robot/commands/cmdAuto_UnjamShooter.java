package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subIndexer;
import frc.robot.subsystems.subShooter;

public class cmdAuto_UnjamShooter extends CommandBase {
  subShooter shooter;
  subIndexer indexer;
  Timer time = new Timer();
  public cmdAuto_UnjamShooter(final subShooter _shooter, final subIndexer _Indexer) {
    shooter = _shooter;
    indexer = _Indexer;
    addRequirements(shooter);
    addRequirements(indexer);
  }

  @Override
  public void initialize() {
    time.start();
  }

  @Override
  public void execute() {
    indexer.Reverse();
    shooter.Reverse();
  }

  @Override
  public void end(final boolean interrupted) {
    indexer.Stop();
    shooter.Stop();
  }

  @Override
  public boolean isFinished() {
    if(time.get() < 1){
      return false;
    }
    else{
      return true;
    }
  }
}

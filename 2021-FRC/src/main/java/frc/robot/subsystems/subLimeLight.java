package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LimeLight;


public class subLimeLight extends SubsystemBase {
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tvHasTarget = table.getEntry("tv");
  private NetworkTableEntry tyVerticalOffset = table.getEntry("ty");
  private NetworkTableEntry txHorizontalOffset = table.getEntry("tx");
  private NetworkTableEntry ledMode = table.getEntry(("ledMode"));
  public NetworkTableEntry pipeline = table.getEntry("pipeline");

  public subLimeLight() {
    pipeline.setNumber(LimeLight.Pipeline.kDriveCamera);
  }

  @Override
  public void periodic() {
    tvHasTarget = table.getEntry("tv");
    txHorizontalOffset = table.getEntry("tx");
    tyVerticalOffset = table.getEntry("ty");

    SmartDashboard.putBoolean("LimeLight Has Target", HasValidTarget());
    SmartDashboard.putNumber("LimeLight Vertical", GetTY());
    SmartDashboard.putNumber("LimeLight Horizontal", GetTX());
    SmartDashboard.putNumber("LimeLight PIPELINE", pipeline.getDouble(0.0));
    SmartDashboard.putNumber("LimeLight LED MODE", ledMode.getDouble(0.0));
  }
  public boolean HasValidTarget(){
    return (tvHasTarget.getDouble(0.0) == 1.0 && -15 < tyVerticalOffset.getDouble(0.0) && tyVerticalOffset.getDouble(0.0) < 17) ? true : false;
  }

  public Double GetTY(){
    return tyVerticalOffset.getDouble(0.0);
  }

  public Double GetTX(){
    return txHorizontalOffset.getDouble(0.0);
  }

  public void SwitchToDriverMode(){
    pipeline.setNumber(LimeLight.Pipeline.kDriveCamera);
  }

  public void SwitchToTargetingMode(){
    pipeline.setNumber(LimeLight.Pipeline.kHighTarget);
  }

  public void TurnOffLED(){
    ledMode.setNumber(0);
  }

  public void TurnOnLED(){
    ledMode.setNumber(1);
  }
}

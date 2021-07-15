package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmSystem;

public class subArm extends SubsystemBase {
  public DutyCycleEncoder encoder;
  public VictorSP armMotor = new VictorSP(ArmSystem.MotorId);
  public subArm() {
    encoder = new DutyCycleEncoder(ArmSystem.EncoderId);
    encoder.setDistancePerRotation(1);
  }

  public void RaiseArm(){
    if(encoder.get() < 0.30){
      armMotor.set(-.7);
    }
    else{
      StopArm();
    }
  }
  public void LowerArm(){
    if(encoder.get() > .12){
      armMotor.set(.2);
    }
    else{
      StopArm();
    }    
  }
  public void StopArm(){
    armMotor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Arm Encoder", encoder.get());
    SmartDashboard.putNumber("Arm Power", armMotor.get());
  }
}

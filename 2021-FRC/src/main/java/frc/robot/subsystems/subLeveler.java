package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbSystem;

public class subLeveler extends SubsystemBase {
  public CANSparkMax levelerMotor = new CANSparkMax(ClimbSystem.LevelerMotorId, MotorType.kBrushless);
  
  public subLeveler() {
    SetMotorSettings();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Leveler Power", levelerMotor.get());
  }

  private void SetMotorSettings() {
    levelerMotor.restoreFactoryDefaults();
    levelerMotor.setSmartCurrentLimit(38);
    levelerMotor.setInverted(ClimbSystem.LevelerInverted);
    levelerMotor.setIdleMode(IdleMode.kBrake);
    levelerMotor.burnFlash();
  }

  public void TeleOp(final double speed) {
    levelerMotor.set(speed*ClimbSystem.LevelerMaxSpeed);
  }
  public void Stop(){
    levelerMotor.set(0);
  }  
}

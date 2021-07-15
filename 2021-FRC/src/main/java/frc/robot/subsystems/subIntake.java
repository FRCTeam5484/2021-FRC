package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeSystem;

public class subIntake extends SubsystemBase {
  private CANSparkMax intakeMotor = new CANSparkMax(IntakeSystem.SpinMotorId, MotorType.kBrushless);
  
  public subIntake() {
    SetMotorSettings();
  }

  private void SetMotorSettings() {
    intakeMotor.restoreFactoryDefaults();    
    intakeMotor.setSmartCurrentLimit(20);    
    intakeMotor.setInverted(IntakeSystem.SpinMotorInverted);
    intakeMotor.setIdleMode(IdleMode.kCoast);
    intakeMotor.burnFlash();
  }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Intake Power", intakeMotor.get());
  }
  
  public void TeleOp(XboxController driver){
    if(driver.getRawAxis(2) > .2) {
      Collect();
    }
    else if(driver.getRawAxis(3) > .2){
      Eject();
    }
    else{
      Stop();
    }
  }

  public void Collect(){
    intakeMotor.set(IntakeSystem.SpinSpeed);
  }

  public void Eject(){
    intakeMotor.set(-IntakeSystem.SpinSpeed);
  }

  public void Stop(){
    intakeMotor.set(0);
  }
}
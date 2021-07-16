package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TurretSystem;
import frc.robot.Constants.LimeLight;

public class subTurret extends SubsystemBase {
  private subLimeLight limelight;
  public CANSparkMax turretMotor = new CANSparkMax(TurretSystem.TurretMotorId, MotorType.kBrushless);
  public CANEncoder turretEncoder = new CANEncoder(turretMotor);

  public double TurretAutoCorrection = 0.0;
  private String turretMode = "TeleOp";

  public subTurret(subLimeLight _limelight) {
    limelight = _limelight;
    SetMotorSettings();
  }

  private void SetMotorSettings() {
    turretMotor.restoreFactoryDefaults();
    turretMotor.setSmartCurrentLimit(28);
    turretMotor.setInverted(TurretSystem.TurretMotorInverted);
    turretMotor.setIdleMode(IdleMode.kBrake);
    turretMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    turretMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    turretMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float)TurretSystem.TurretRightLimit);
    turretMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float)TurretSystem.TurretLeftLimit);
    turretMotor.burnFlash();
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("Turret Mode", turretMode);
    SmartDashboard.putNumber("Turret Encoder", turretEncoder.getPosition());
    SmartDashboard.putNumber("Turret Power", turretMotor.get());
    SmartDashboard.putBoolean("Turret Ready", TurretReady());
  }

  public void TeleOp(final XboxController driver) {
    limelight.SwitchToDriverMode();
    turretMode = "TeleOp";
    turretMotor.set(driver.getRawAxis(4) * TurretSystem.TurretMaxSpeed);
    
  }
  public void Auto() {
    limelight.SwitchToTargetingMode();
    turretMode = "Auto";
    if (limelight.HasValidTarget()) {
      turretMotor.set(TurretSystem.TurretMaxSpeed * (limelight.GetTX() * LimeLight.Parameters.STEER_K));
    }
  }

  public boolean TurretReady(){
    if (!limelight.HasValidTarget()) { return false; }
    return (Math.abs(LimeLight.Parameters.MAX_TURRET * (limelight.GetTX() * LimeLight.Parameters.STEER_K)) > .06) ? false : true;
  }

  public void Stop() {
    turretMotor.set(0);
  }

  public void ResetEncoder() {
    turretEncoder.setPosition(0);
  }
}
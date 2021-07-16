package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterSystem;
import frc.robot.helpers.Calculate;

public class subShooter extends SubsystemBase {
  private subLimeLight limelight;
  public CANSparkMax shooterLeftMasterMotor = new CANSparkMax(ShooterSystem.LeftMotorId, MotorType.kBrushless);
  public CANSparkMax shooterRightSlaveMotor = new CANSparkMax(ShooterSystem.RightMotorId, MotorType.kBrushless);
  public CANEncoder shooterLeftEncoder;
  public CANEncoder shooterRightEncoder;
  
  private CANPIDController shooterPIDController;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;

  private boolean ShooterReady = false;
  public String shooterMode = "TeleOp";

  public subShooter(subLimeLight _limelight) {
    limelight = _limelight;
    SetMotorSettings();
  }

  private void SetMotorSettings() {
    shooterLeftMasterMotor.restoreFactoryDefaults();
    shooterRightSlaveMotor.restoreFactoryDefaults();

    shooterLeftMasterMotor.setInverted(ShooterSystem.LeftMotorInverted);
    shooterRightSlaveMotor.follow(shooterLeftMasterMotor, true);

    shooterPIDController = shooterLeftMasterMotor.getPIDController();
    shooterLeftEncoder = shooterLeftMasterMotor.getEncoder();
    shooterRightEncoder = shooterRightSlaveMotor.getEncoder();

    kP = 0; 
    kI = 0.0000001;
    kD = 0.000001; 
    kIz = 0; 
    kFF = 0.00017; 
    kMaxOutput = 1; 
    kMinOutput = 0;
    maxRPM = 5700;

    shooterPIDController.setP(kP);
    shooterPIDController.setI(kI);
    shooterPIDController.setD(kD);
    shooterPIDController.setIZone(kIz);
    shooterPIDController.setFF(kFF);
    shooterPIDController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("Shooter P Gain", kP);
    SmartDashboard.putNumber("Shooter I Gain", kI);
    SmartDashboard.putNumber("Shooter D Gain", kD);
    SmartDashboard.putNumber("Shooter I Zone", kIz);
    SmartDashboard.putNumber("Shooter Feed Forward", kFF);
    SmartDashboard.putNumber("Shooter Max Output", kMaxOutput);
    SmartDashboard.putNumber("Shooter Min Output", kMinOutput);
    SmartDashboard.putNumber("Shooter RPM Target", 0);

    shooterLeftMasterMotor.burnFlash();
    shooterRightSlaveMotor.burnFlash();
  }
  @Override
  public void periodic() {
    SmartDashboard.putString("Shooter Mode", shooterMode);
    SmartDashboard.putNumber("Shooter RPM", shooterLeftEncoder.getVelocity());
    SmartDashboard.putNumber("Shooter Power", shooterLeftMasterMotor.get());
    SmartDashboard.putBoolean("Shooter Ready", ShooterReady);    
  }
  public Boolean ShooterReady(){
    return ShooterReady;
  }
  public void TeleOp(final XboxController driver) {
    shooterMode = "TeleOp";
    shooterLeftMasterMotor.set(driver.getRawAxis(3));
  }
  public void Auto(){
    double p = SmartDashboard.getNumber("Shooter P Gain", 0);
    double i = SmartDashboard.getNumber("Shooter I Gain", 0);
    double d = SmartDashboard.getNumber("Shooter D Gain", 0);
    double iz = SmartDashboard.getNumber("Shooter I Zone", 0);
    double ff = SmartDashboard.getNumber("Shooter Feed Forward", 0);
    double max = SmartDashboard.getNumber("Shooter Max Output", 0);
    double min = SmartDashboard.getNumber("Shooter Min Output", 0);

    if((p != kP)) { shooterPIDController.setP(p); kP = p; }
    if((i != kI)) { shooterPIDController.setI(i); kI = i; }
    if((d != kD)) { shooterPIDController.setD(d); kD = d; }
    if((iz != kIz)) { shooterPIDController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { shooterPIDController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { shooterPIDController.setOutputRange(min, max); kMinOutput = min; kMaxOutput = max; }
    
    shooterMode = "Auto";
    limelight.SwitchToTargetingMode();
    if (limelight.HasValidTarget()) {
      double rpm = 0;
      if(Calculate.isBetween(limelight.GetTY(), -6, -4.5)){ rpm = 4500; } // Behind Wheel
      else if(Calculate.isBetween(limelight.GetTY(), -4.5, -2)){ rpm = 4000; }
      else if(Calculate.isBetween(limelight.GetTY(), -2, 0.8)){ rpm = 3700; }
      else if(Calculate.isBetween(limelight.GetTY(), 0.8, 1.8)){ rpm = 3600; }
      else if(Calculate.isBetween(limelight.GetTY(), 1.8, 4)){ rpm = 3800; }
      else if(Calculate.isBetween(limelight.GetTY(), 4, 9)){ rpm = 3800; }
      else if(Calculate.isBetween(limelight.GetTY(), 9, 14)){ rpm = 4000; } // Intiation Line
      shooterPIDController.setReference(rpm, ControlType.kVelocity);
      ShooterReady = Calculate.isBetween(shooterLeftEncoder.getVelocity(), (rpm-100), (rpm+100));
    }
    else{ Stop(); ShooterReady = false; }
  }
  public void SpinToRPM(){
    double p = SmartDashboard.getNumber("Shooter P Gain", 0);
    double i = SmartDashboard.getNumber("Shooter I Gain", 0);
    double d = SmartDashboard.getNumber("Shooter D Gain", 0);
    double iz = SmartDashboard.getNumber("Shooter I Zone", 0);
    double ff = SmartDashboard.getNumber("Shooter Feed Forward", 0);
    double max = SmartDashboard.getNumber("Shooter Max Output", 0);
    double min = SmartDashboard.getNumber("Shooter Min Output", 0);
    double rpm = SmartDashboard.getNumber("Shooter RPM Target", 0);

    if((p != kP)) { shooterPIDController.setP(p); kP = p; }
    if((i != kI)) { shooterPIDController.setI(i); kI = i; }
    if((d != kD)) { shooterPIDController.setD(d); kD = d; }
    if((iz != kIz)) { shooterPIDController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { shooterPIDController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { shooterPIDController.setOutputRange(min, max); kMinOutput = min; kMaxOutput = max; }
    
    shooterMode = "Auto";
    limelight.SwitchToTargetingMode();
    shooterPIDController.setReference(rpm, ControlType.kVelocity);
    ShooterReady = Calculate.isBetween(shooterLeftEncoder.getVelocity(), (rpm-100), (rpm+100));
  }
  public void Reverse(){
    shooterMode = "TeleOp";
    shooterLeftMasterMotor.set(-.8);
    ShooterReady = false;
  }
  public void Stop() {
    shooterMode = "TeleOp";
    shooterLeftMasterMotor.set(0);
    ShooterReady = false;
  }
  public void Manual() {
    shooterLeftMasterMotor.set(.8);
  }
}

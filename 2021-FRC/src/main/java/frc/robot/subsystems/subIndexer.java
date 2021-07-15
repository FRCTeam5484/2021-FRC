package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerSystem;

public class subIndexer extends SubsystemBase {
  private CANSparkMax indexerMotor = new CANSparkMax(IndexerSystem.MotorId, MotorType.kBrushless);
  public DigitalInput ballSensor1 = new DigitalInput(IndexerSystem.BallSensor1);
  public DigitalInput ballSensor2 = new DigitalInput(IndexerSystem.BallSensor2);
  public DigitalInput ballSensor3 = new DigitalInput(IndexerSystem.BallSensor3);
  public DigitalInput ballSensor4 = new DigitalInput(IndexerSystem.BallSensor4);
  public DigitalInput ballSensor5 = new DigitalInput(IndexerSystem.BallSensor5);
  public DigitalInput ballIntake = new DigitalInput(IndexerSystem.BallSensorIntake);

  private CANEncoder indexerEncoder = new CANEncoder(indexerMotor);
  public String indexerMode = "TeleOp";

  public subIndexer() {
    SetMotorSettings();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Indexer Encoder", indexerEncoder.getPosition());
    SmartDashboard.putNumber("Indexer Power", indexerMotor.get());
    SmartDashboard.putString("Indexer Mode", indexerMode);
    SmartDashboard.putBoolean("Ball1", !ballSensor1.get());
    SmartDashboard.putBoolean("Ball2", !ballSensor2.get());
    SmartDashboard.putBoolean("Ball3", !ballSensor3.get());
    SmartDashboard.putBoolean("Ball4", !ballSensor4.get());
    SmartDashboard.putBoolean("Ball5", !ballSensor5.get());
    SmartDashboard.putBoolean("BallIntake", !ballIntake.get());
    SmartDashboard.putBoolean("Has Ball", HasABall());
  }

  private void SetMotorSettings() {
    indexerMotor.restoreFactoryDefaults();
    indexerMotor.setSmartCurrentLimit(38);
    indexerMotor.setInverted(IndexerSystem.MotorInverted);
    indexerMotor.setIdleMode(IdleMode.kCoast);
    indexerMotor.burnFlash();
  }

  public boolean HasABall(){
    return (!ballSensor1.get() || !ballSensor2.get() || !ballSensor3.get() || !ballSensor4.get() || !ballSensor5.get() || !ballIntake.get()) ? true : false;
  }

  public void TeleOp(final double speed) {
    indexerMode = "TeleOp";
    indexerMotor.set(speed*IndexerSystem.ManualSpeed);
  }
  public void Forward(){
    indexerMode = "Auto";
    indexerMotor.set(IndexerSystem.AutoSpeed);
  }
  public void Reverse() {
    indexerMode = "Auto";
    indexerMotor.set(-IndexerSystem.AutoSpeed);
  }
  public void Stop() {
    indexerMotor.set(0);
  } 
  public void AutoLoadIndexer() {
    indexerMode = "Auto";
    if (!ballIntake.get()) {
      if (!ballSensor5.get() && !ballSensor4.get() && !ballSensor3.get() && !ballSensor2.get() && !ballSensor1.get()) {
        Reverse();
      }
      else {
        Forward();
      }
    }
    else {
      if (!ballSensor1.get()) {
        if (!ballSensor2.get() && !ballSensor3.get() && !ballSensor4.get() && !ballSensor5.get()) {
          Stop();
        }
        else {
          Reverse();
        }
      }
      else if (!ballSensor2.get()) {
        if (!ballSensor3.get() && !ballSensor4.get() && !ballSensor5.get()) {
          Stop();
        }
        else {
          Reverse();
        }
      }
      else if (!ballSensor3.get()) {
        if (!ballSensor4.get() && !ballSensor5.get()) {
          Stop();
        }
        else {
          Reverse();
        }
      }
      else if (!ballSensor4.get()) {
        if (!ballSensor5.get()) {
          Stop();
        }
        else {
          Reverse();
        }
      }
      else if (!ballSensor5.get()) {
        Stop();
      }
    }
  }
}

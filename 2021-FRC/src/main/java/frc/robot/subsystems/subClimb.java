package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbSystem;

public class subClimb extends SubsystemBase {
  public CANSparkMax liftMasterMotor = new CANSparkMax(ClimbSystem.LeftMotorId, MotorType.kBrushless);
  public CANSparkMax liftSlaveMotor = new CANSparkMax(ClimbSystem.RightMotorId, MotorType.kBrushless);
  public CANEncoder masterEncoder = new CANEncoder(liftMasterMotor);
  public CANEncoder slaveEncoder = new CANEncoder(liftSlaveMotor);
  public String liftMode = "TeleOp";

  public subClimb() {
    SetMotorSettings();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Climb Encoder", slaveEncoder.getPosition());
    SmartDashboard.putNumber("Climb Power", liftMasterMotor.get());
  }

  private void SetMotorSettings() {
    liftMasterMotor.restoreFactoryDefaults();
    liftSlaveMotor.restoreFactoryDefaults();
    
    liftMasterMotor.setSmartCurrentLimit(38);
    liftSlaveMotor.setSmartCurrentLimit(38);
    
    liftMasterMotor.setInverted(ClimbSystem.LeftInverted);
    
    liftMasterMotor.setIdleMode(IdleMode.kBrake);
    liftSlaveMotor.setIdleMode(IdleMode.kBrake);
    
    liftSlaveMotor.follow(liftMasterMotor, true);

    liftMasterMotor.burnFlash();
    liftSlaveMotor.burnFlash();

  }

  public void TeleOp(XboxController driver){
    if(driver.getRawAxis(1) > .5 || driver.getRawAxis(1) < -.5){
      MoveLift(-driver.getRawAxis(1));
    } else { liftMasterMotor.stopMotor(); }
  }
  public void MoveLift(final double speed) {
    liftMode = "TeleOp";
    double safeSpeed = speed * ClimbSystem.ClimbMaxSpeed;
    if(safeSpeed < 0 && slaveEncoder.getPosition() > ClimbSystem.BottomLimit || safeSpeed > 0 && slaveEncoder.getPosition() < ClimbSystem.TopLimit){
      liftMasterMotor.set(safeSpeed);
    } else { liftMasterMotor.stopMotor(); }
  }
}

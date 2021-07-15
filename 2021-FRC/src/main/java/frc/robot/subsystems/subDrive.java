package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveSystem;

public class subDrive extends SubsystemBase {
  public CANSparkMax leftDriveMaster = new CANSparkMax(DriveSystem.LeftMasterMotorId, MotorType.kBrushless);
  public CANSparkMax leftDriveSlave = new CANSparkMax(DriveSystem.LeftSlaveMotorId, MotorType.kBrushless);
  public CANSparkMax rightDriveMaster = new CANSparkMax(DriveSystem.RightMasterMotorId, MotorType.kBrushless);
  public CANSparkMax rightDriveSlave = new CANSparkMax(DriveSystem.RightSlaveMotorId, MotorType.kBrushless);
  public DifferentialDrive driveTrain = new DifferentialDrive(leftDriveMaster, rightDriveMaster);
  public AHRS ahrs;

  private CANPIDController leftPIDController;
  private CANPIDController rightPIDController;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  public CANEncoder left1Encoder = new CANEncoder(leftDriveMaster);
  public CANEncoder left2Encoder = new CANEncoder(leftDriveSlave);
  public CANEncoder right1Encoder = new CANEncoder(rightDriveMaster);
  public CANEncoder right2Encoder = new CANEncoder(rightDriveSlave);
  public String driveMode = "TeleOp";

  public subDrive() {
    SetMotorSettings();
    SetPIDSettings();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Drive Left Power", leftDriveMaster.get());
    SmartDashboard.putNumber("Drive Right Power", rightDriveMaster.get());
    SmartDashboard.putNumber("Drive Left Encoder", left1Encoder.getPosition());
    SmartDashboard.putNumber("Drive Right Encoder", right1Encoder.getPosition());
  }

  private void SetMotorSettings() {
    leftDriveMaster.restoreFactoryDefaults();
    leftDriveSlave.restoreFactoryDefaults();

    leftDriveMaster.setSmartCurrentLimit(38);
    leftDriveSlave.setSmartCurrentLimit(38);

    leftDriveMaster.setInverted(DriveSystem.LeftDriveInverted);

    left1Encoder = leftDriveMaster.getEncoder();
    left2Encoder = leftDriveSlave.getEncoder();

    leftDriveMaster.setIdleMode(IdleMode.kCoast);
    leftDriveSlave.setIdleMode(IdleMode.kCoast);

    leftDriveSlave.follow(leftDriveMaster, false);

    leftDriveMaster.burnFlash();
    leftDriveSlave.burnFlash();

    rightDriveMaster.restoreFactoryDefaults();
    rightDriveSlave.restoreFactoryDefaults();

    rightDriveMaster.setSmartCurrentLimit(38);
    rightDriveSlave.setSmartCurrentLimit(38);

    rightDriveMaster.setInverted(DriveSystem.RightDriveInverted);

    right1Encoder = rightDriveMaster.getEncoder();
    right2Encoder = rightDriveSlave.getEncoder();

    rightDriveMaster.setIdleMode(IdleMode.kCoast);
    rightDriveSlave.setIdleMode(IdleMode.kCoast);

    rightDriveSlave.follow(rightDriveMaster, false);

    rightDriveMaster.burnFlash();
    rightDriveSlave.burnFlash();
  }

  private void SetPIDSettings() {
    leftPIDController = leftDriveMaster.getPIDController();
    rightPIDController = rightDriveMaster.getPIDController();

    kP = 0.1;
    kI = 1e-4;
    kD = 1;
    kIz = 0;
    kFF = .1;
    kMaxOutput = .5;
    kMinOutput = -.5;

    leftPIDController.setP(kP);
    leftPIDController.setI(kI);
    leftPIDController.setD(kD);
    leftPIDController.setIZone(kIz);
    leftPIDController.setFF(kFF);
    leftPIDController.setOutputRange(kMinOutput, kMaxOutput);

    rightPIDController.setP(kP);
    rightPIDController.setI(kI);
    rightPIDController.setD(kD);
    rightPIDController.setIZone(kIz);
    rightPIDController.setFF(kFF);
    rightPIDController.setOutputRange(kMinOutput, kMaxOutput);
  }

  public void DriveStraightForRotations(final double rotations) {
    driveMode = "Auto";
    leftPIDController.setReference(rotations, ControlType.kPosition);
    rightPIDController.setReference(-rotations, ControlType.kPosition);
    SmartDashboard.putNumber("Drive Set Point", rotations);
  }

  public void stopDrive() {
    driveMode = "TeleOp";
    driveTrain.tankDrive(0, 0);
  }

  public void DriveLock() {
    leftDriveMaster.stopMotor();
    rightDriveMaster.stopMotor();
    leftDriveMaster.setIdleMode(IdleMode.kBrake);
    leftDriveSlave.setIdleMode(IdleMode.kBrake);
    rightDriveMaster.setIdleMode(IdleMode.kBrake);
    rightDriveSlave.setIdleMode(IdleMode.kBrake);
  }

  public void DriveUnlock() {
    leftDriveMaster.setIdleMode(IdleMode.kCoast);
    leftDriveSlave.setIdleMode(IdleMode.kCoast);
    rightDriveMaster.setIdleMode(IdleMode.kCoast);
    rightDriveSlave.setIdleMode(IdleMode.kCoast);
  }

  public void tankDrive(final XboxController driver) {
    driveMode = "TeleOp";
    driveTrain.tankDrive(-driver.getY(Hand.kRight) * DriveSystem.ManualMaxSpeed, driver.getY(Hand.kLeft) * DriveSystem.ManualMaxSpeed);
    driveTrain.setDeadband(0.08);
  }
  public void tankDrive(final double left, final double right) {
    driveMode = "TeleOp";
    driveTrain.tankDrive(left * DriveSystem.ManualMaxSpeed, right * DriveSystem.ManualMaxSpeed);
    driveTrain.setDeadband(0.08);
  }
  public void ResetEncoders(){
    left1Encoder.setPosition(0);
    left2Encoder.setPosition(0);
    right1Encoder.setPosition(0);
    right2Encoder.setPosition(0);
  }
  public void DriveStraightUsingEncoders(){
    double error = left1Encoder.getPosition() - right1Encoder.getPosition();
    double turnPower = .04 * -error;
    driveTrain.arcadeDrive(DriveSystem.AutoMaxSpeed, turnPower, false);
  }
  public void SimpleMove(double speed){
    driveTrain.tankDrive(speed, -speed);
  }
}
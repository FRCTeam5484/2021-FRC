package frc.robot;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;

public final class Constants {
    private final static class HardwareId{
        // Spark Max
        private final static int DriveRightMaster = 1;
        private final static int DriveRightSlave = 2;
        private final static int DriveLeftMaster = 3;
        private final static int DriveLeftSlave = 4;
        private final static int ClimbLeft = 5;
        private final static int ClimbRight = 6;
        private final static int ShooterRight = 7;
        private final static int ShooterLeft = 8;
        private final static int ColorWheel = 9;
        private final static int Indexer = 10;
        private final static int Intake = 11;
        private final static int Turret = 12;
        private final static int Leveler = 13;
        // PWM
        private final static int Arm = 0;
        private final static int LEDLeftStrand = 1;
        private final static int LEDRightStrand = 2;
        private final static int LEDTurretStrand = 3;
        // DIO
        private final static int BallSensor1 = 0;
        private final static int BallSensor2 = 1;
        private final static int BallSensor3 = 2;
        private final static int BallSensor4 = 3;
        private final static int BallSensor5 = 4;
        private final static int BallSensorIntake = 5;
        private final static int RevHexEncoder = 6;
    }
    public final static class DriveSystem {
        // Ids
        public final static int RightMasterMotorId = HardwareId.DriveRightMaster;
        public final static int RightSlaveMotorId = HardwareId.DriveRightSlave;
        public final static int LeftMasterMotorId = HardwareId.DriveLeftMaster;
        public final static int LeftSlaveMotorId = HardwareId.DriveLeftSlave;
        // Inversion
        public final static boolean LeftDriveInverted = true;
        public final static boolean RightDriveInverted = false;
        // Speed
        public final static double ManualMaxSpeed = 0.85;
        public final static double AutoMaxSpeed = 0.3;
    }
    public final static class ClimbSystem {
        // Ids
        public final static int LeftMotorId = HardwareId.ClimbLeft;
        public final static int RightMotorId = HardwareId.ClimbRight;
        public final static int LevelerMotorId = HardwareId.Leveler;
        // Inversion
        public final static boolean LeftInverted = false;
        public final static boolean LevelerInverted = false;
        // Speed
        public final static double ClimbMaxSpeed = .8;
        public final static double LevelerMaxSpeed = .8;
        // Encoder Values
        public final static int TopLimit = 175;
        public final static int ClimbLimit = 25;
        public final static int BottomLimit = 1;
    }
    public final static class ShooterSystem {
        // Ids
        public final static int LeftMotorId = HardwareId.ShooterLeft;
        public final static int RightMotorId = HardwareId.ShooterRight;
        // Inversion
        public final static boolean LeftMotorInverted = true;
        // Speed
        public final static double ShootManualSpeed = .7;
    }
    public final static class TurretSystem {
        // Ids
        public final static int TurretMotorId = HardwareId.Turret;
        // Inversion
        public final static boolean TurretMotorInverted = false;
        // Speed
        public final static double TurretMaxSpeed = .35;
        // Turrent Limits
        public final static double TurretLeftLimit = -19;
        public final static double TurretRightLimit = 19;
    }
    public final static class IndexerSystem {
        // Ids
        public final static int MotorId = HardwareId.Indexer;
        // Inversion
        public final static boolean MotorInverted = true;
        // Speed
        public final static double ManualSpeed = .8;
        public final static double AutoSpeed = .7;
        // Sensors
        public final static int BallSensor1 = HardwareId.BallSensor1;
        public final static int BallSensor2 = HardwareId.BallSensor2;
        public final static int BallSensor3 = HardwareId.BallSensor3;
        public final static int BallSensor4 = HardwareId.BallSensor4;
        public final static int BallSensor5 = HardwareId.BallSensor5;
        public final static int BallSensorIntake = HardwareId.BallSensorIntake;
    }
    public final static class ColorWheelSystem {
        // Ids
        public final static int MotorId = HardwareId.ColorWheel;
        // Inversion
        public final static boolean MotorInverted = false;
        // Speed
        public final static double Speed = .5;
        // Encoder
        public final static double FourSpinValue = 50.0;
    }
    public final static class IntakeSystem {
        // Ids
        public final static int SpinMotorId = HardwareId.Intake;
        public final static int ArmMotorId = 0;
        // Inversion
        public final static boolean SpinMotorInverted = true;
        public final static boolean ArmMotorInverted = true;
        // Speed
        public final static double SpinSpeed = 1;
        public final static double ArmSpeed = .8;
    }
    public final static class ArmSystem{
        // Ids
        public final static int MotorId = HardwareId.Arm;
        // Encoder
        public final static int EncoderId = HardwareId.RevHexEncoder;
    }
    public final static class Colors {
        public final static Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        public final static Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        public final static Color kRedTarget = ColorMatch.makeColor(0.561, 0.231, 0.114);
        public final static Color kYellowTarget = ColorMatch.makeColor(0.361, 0.523, 0.113);
        public final static double kConfidence = .01;
    }
    public final static class LimeLight {
        public final static class Pipeline {
            public final static int kDriveCamera = 0;
            public final static int kHighTarget = 1;
        }
        public final static class Types {
            public final static String kledMode = "ledMode";
            public final static String kcamMode = "camMode";
            public final static String kpipeline = "pipeline";
            public final static String kstream = "stream";
            public final static String ksnapshot = "snapshot";
        }
        public final static class ledMode {
            public final static Number kDefault = 0;
            public final static Number kOn = 3;
            public final static Number kOff = 1;
            public final static Number kBlink = 2;
        }
        public final static class Parameters {
            public final static String kHasTarget = "tv";
            public final static String kHorizontalOffset = "tx";
            public final static String kVerticalOffset = "ty";
            public final static String kDistanceOffset = "ta";
            public final static double STEER_K = 0.06;
            public final static double MAX_TURRET = .6;
        }
    }
    public final static class LED {
        public final static class LiftStrand{
            public final static int LeftPort = HardwareId.LEDLeftStrand;
            public final static int RightPort = HardwareId.LEDRightStrand;
            public final static int Count = 20;
            public final static double Intesity = 0.8;
            public final static int timerInterval = 3000;
        }
        public final static class TurretStrand{
            public final static int Port = HardwareId.LEDTurretStrand;
            public final static int Count = 8;
            public final static double Intesity = 0.8;
            public final static int timerInterval = 3000;
        }
    }
    public final static class DriveControllers {
        public final static int DriverOne = 0;
        public final static int DriverTwo = 1;
        public final static int ButtonBox = 2;
    }
    public final static class NavX {
        public final static double kTurnP = 0;
        public final static double kTurnI = 0;
        public final static double kTurnD = 0;
        public final static boolean kGyroReversed = true;
        public static final double kMaxTurnRateDegPerS = 100;
        public static final double kMaxTurnAccelerationDegPerSSquared = 300;

        public static final double kTurnToleranceDeg = 5;
        public static final double kTurnRateToleranceDegPerS = 10;
    }
}

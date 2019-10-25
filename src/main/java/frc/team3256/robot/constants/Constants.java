package frc.team3256.robot.constants;

public class Constants {

    //PWM
    public static final int kLeftDriveMaster = 3; //0
    public static final int kLeftDriveSlave = 4; //1
    public static final int kRightDriveMaster = 5; //3
    public static final int kRightDriveSlave = 6; //4

    public static final int kPivot = 7;

    public static final int pcmOneId = 15;
    public static final int pcmTwoId = 0;

    public static final int rightEjectForward = 0;
    public static final int rightEjectBack = 2;
    public static final int leftEjectForward = 4;
    public static final int leftEjectBack = 3;
    public static final int leftPopForward = 3;
    public static final int leftPopBack = 2;
    public static final int rightPopForward = 7;
    public static final int rightPopBack = 6;

    public static final double kGearRatio = 6.4;
    public static final int kVelocityHighGearSlot = 1;
    public static final double kWheelDiameter = 4;
    public static final double kVelocityMaxRPM = 4150;
    public static final double kMaxAccel = 1000;
    public static final double kAllowedErr = 0;
    public static final double kAngularPowerScalar = 1.7; // 1.7
    public static final int kVelocityLowGearSlot = 0;
    public static final int pcmId = 15;

    public static final int kShifterForward = 1; // 6
    public static final int kShifterReverse = 6; // 1

    public static final double kVelocityLowGearP = 5e-6;
    public static final double kVelocityLowGearI = 1e-7;
    public static final double kVelocityLowGearD = 0;
    public static final double kVelocityLowGearIZone = 0;
    public static final double kVelocityLowGearF = 1e-5;

    public static final double kVelocityHighGearP = 5e-4;
    public static final double kVelocityHighGearI = 1E-9;
    public static final double kVelocityHighGearD = 0;
    public static final double kVelocityHighGearIZone = 0;
    public static final double kVelocityHighGearF = 1e-5;

    public static double quickStopAccumulator = 0.0;
    public static final double kQuickStopAlpha = 0.1;
    public static final double kQuickStopScalar = 2.0;
    public static final double kQuickTurnDeltaLimit = 2.0 / 1000.0 / 12.0 * 20.0;


}

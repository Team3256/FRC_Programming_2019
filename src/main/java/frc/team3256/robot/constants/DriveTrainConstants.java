package frc.team3256.robot.constants;

public class DriveTrainConstants {
    //Curvature Drive
    public static final double kQuickTurnDeltaLimit = 2.0 / 1000.0 / 12.0 * 20.0;
    //CAN
    public static final int kLeftDriveMaster = 3;
    public static final int kLeftDriveSlave = 4;
    public static final int kRightDriveMaster = 5;
    public static final int kRightDriveSlave = 6;

    public static final double kVelocityP = 8e-5;
    public static final double kVelocityI = 1e-6;
    public static final double kVelocityD = 0;
    public static final double kVelocityIZone = 0;
    public static final double kVelocityF = 1e-5;
    public static final double kVelocityMaxRPM = 4000;
    public static final double kMaxAccel = 1000;
    public static final double kAllowedErr = 0;
    public static final double kAngularPowerScalar = 1.7;

    public static final double kTurnP = 0.01;
    public static final double kTurnI = 0.00005;
    public static final double kTurnD = 0.001;

    public static final int kShifterForward = 1; // 6
    public static final int kShifterReverse = 6; // 1
    public static final int kHangerForward = 0; //TBD
    public static final int kHangerReverse = 0; //TBD

    public static final double kWheelDiameter = 4;
    public static final double kMagEncoderTicksTalon = 4096.0; //4096 = # of ticks per revolution | 4 X decoding for Talon SRX & Canifier only

    public static double quickStopAccumulator = 0.0;
    public static final double kQuickStopAlpha = 0.1;
    public static final double kQuickStopScalar = 2.0;

    public static final double kGearRatio = 6.4;

    //Pure Pursuit
    public static final double robotTrack = 27; //inches
    public static final double spacing = 6; //inches
    public static final double kA = 0.002; //0.002
    public static final double kP = 0.001; //0.01
    public static final double purePursuitB = 0.9;
    public static final double purePursuitA = 1 - purePursuitB;
    public static final double smoothingTolerance = 0.001;
    public static final double loopTime = 1.0 / 200.0; //how often Looper updates
    public static final double lookaheadDistance = 24;
    public static final double maxAccel = 25; //max robot acceleration
    public static final double maxVel = 60; //max robot velocity
    public static final double kV = 1 / maxVel; // 1/max robot speed
    public static final double maxVelk = 2; //generally between 1-5
}

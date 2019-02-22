package frc.team3256.robot.constants;

public class ElevatorConstants {
    public static final int kSparkMaxMaster = 1;
    public static final int kSparkMaxSlave = 2;

    public static final double kElevatorP = 0.00016; //0.1
    public static final double kElevatorI = 0; //1e-4
    public static final double kElevatorD = 0.0004; //1
    public static final double kElevatorF = 0.000156; //0
    public static final double kSmartMotionMaxVel = 2000;
    public static final double kSmartMotionMaxAccel = 1500;
    public static final double kDFilter = 0.25;
    public static final double kSmartMotionAllowedClosedLoopError = 0.02;
    public static final double kMinOutputVelocity = 0.0;
    public static final double kElevatorIz = 0; //0

    public static final double kDropPreset = 10.0;

    public static final double kPositionMax = 195;
    public static final double kPositionMin = 0;

    public static final double kPositionHighHatch = 0;
    public static final double kPositionMidHatch = 0;
    public static final double kPositionLowHatch = 0;
    public static final double kPositionHighCargo = 0;
    public static final double kPositionMidCargo = 0;
    public static final double kPositionLowCargo = 0;
    public static final double kPositionHomeCargo = 0;

    public static final double kElevatorMaxPosition = 195; // 195
    public static final double kElevatorMinPosition = 0; // 1

    public static final double kElevatorSpeed = 0.3;
}

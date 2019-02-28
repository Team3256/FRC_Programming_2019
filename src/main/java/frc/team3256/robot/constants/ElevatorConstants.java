package frc.team3256.robot.constants;

public class ElevatorConstants {
    public static final int kSparkMaxMaster = 1;
    public static final int kSparkMaxSlave = 2;


    public static final double kElevatorP = 0.00016; //0.00016
    public static final double kElevatorI = 0; //0
    public static final double kElevatorD = 0.0004; //0.0004
    public static final double kElevatorF = 0.000156; //0.000156
    public static final double kSmartMotionMaxVel = 5000; //2900
    public static final double kSmartMotionMaxAccel = 7000; //2200
    public static final double kDFilter = 0.25; //0.25
    public static final double kSmartMotionAllowedClosedLoopError = 0.05; //0.02
    public static final double kMinOutputVelocity = -kSmartMotionMaxVel;
    public static final double kElevatorIz = 0; //0

    public static final double kDropPreset = 10.0;

    public static final double kPositionMax = 195;
    public static final double kPositionMin = 0;

    public static final double kPositionHighHatch = 78;
    public static final double kPositionMidHatch = 50;
    public static final double kPositionLowHatch = 22;
    public static final double kPositionHighCargo = 75;
    public static final double kPositionMidCargo = 47;
    public static final double kPositionLowCargo = 19;
    public static final double kHookOffset = 5.0;
    public static final double kPositionHomeCargo = 0;
    public static final double kUnhookOffset = 3.5;
    public static final double kElevatorSpoolSize = 1.5;
    public static final double kElevatorGearRatio = 1.0 / 15.0;

    public static final double kElevatorMaxPosition = 195; // 195
    public static final double kElevatorMinPosition = 0; // 1
    private static final double kHookPositionOffset = -0; //has to be negative
    public static final double kHatchHumanPlayerPosition = 19.0 + kHookPositionOffset;

    public static final double kElevatorSpeed = 0.3;
    public static final int kHallEffectPort = 0;
}

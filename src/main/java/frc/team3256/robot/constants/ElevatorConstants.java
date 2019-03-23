package frc.team3256.robot.constants;

public class ElevatorConstants {
    public static final int kSparkMaxMaster = 1;
    public static final int kSparkMaxSlave = 2;

    public static final double kElevatorP = 0.00016; //0.00016
    public static final double kElevatorI = 0; //0
    public static final double kElevatorD = 0.0004; //0.0004
    public static final double kElevatorF = 0.000156; //0.000156
    public static final double kSmartMotionMaxVel = 7000; //2900
    public static final double kSmartMotionMaxAccel = 7000; //2200
    public static final double kDFilter = 0.25; //0.25
    public static final double kSmartMotionAllowedClosedLoopError = 0.05; //0.02
    public static final double kMinOutputVelocity = -kSmartMotionMaxVel;
    public static final double kElevatorIz = 0; //0

    public static final int kElevatorHoldPort = 0;
    public static final double kElevatorHoldP = 0.55;
    public static final double kElevatorHoldI = 0.002;
    public static final double kElevatorHoldD = 0.00002;
    public static final double kElevatorHoldIZone = 0;
    public static final double kElevatorHoldF = 0;

    public static final int kElevatorClosedLoopPort = 1;
    public static final double kElevatorClosedLoopP = 0.35;
    public static final double kElevatorClosedLoopI = 0.20;
    public static final double kElevatorClosedLoopD = 0.0;
    public static final double kElevatorClosedLoopIZone = 0;
    public static final double kElevatorClosedLoopF = 0.25;

    public static final int kElevatorClosedLoopIntakePort = 2;

    public static final double kDropPreset = 10.0;

    public static final double kPositionMax = 195;
    public static final double kPositionMin = 0;

    public static final double kUnhookOffset = -5.0; //has to be negative
    public static final double kElevatorOffset = 9.0; // 10.75 comp
    public static final double kHookOffset = 5.0;
    public static final double kHatchHumanPlayerPosition = 12.5; // 1
    private static final double kHatchPositionOffset = 0; //has to be negative
    public static final double kPositionHighHatch = 73 + kHatchPositionOffset;
    public static final double kPositionMidHatch = 46 + kHatchPositionOffset;
    public static final double kPositionLowHatch = 19 + kHatchPositionOffset;
    private static final double kCargoPositionOffset = -19;
    public static final double kPositionHighCargo = 83.5 + kCargoPositionOffset; // 83.5
    public static final double kPositionMidCargo = 55.5 + kCargoPositionOffset;
    public static final double kPositionLowCargo = 27.5 + kCargoPositionOffset;
    public static final double kPositionShip = 48 + kCargoPositionOffset;
    public static final double kPositionHang = 60;

    public static final double kElevatorSpoolSize = 1.5;
    public static final double kElevatorGearRatio = 1.0 / 15.0;

    public static final double kElevatorMaxPosition = 190; // 195
    public static final double kElevatorMinPosition = 0; // 1

    public static final double kElevatorSpeed = 0.3;
    public static final double kElevatorManualRPM = 7000;
    public static final int kHallEffectPort = 0;
}

package frc.team3256.robot.constants;

public class HatchConstants {
    public static final double kHatchP = 0.8;
    public static final double kHatchI = 0.0;
    public static final double kHatchD = 0.013;
    public static final double kHatchF = 0.00001;
    public static final double kHatchIz = 0.0;

    public static final int kHatchHoldPort = 0;
    public static final double kHatchHoldP = 2.60;
    public static final double kHatchHoldI = 0.004;
    public static final double kHatchHoldD = 0.0005;
    public static final double kHatchHoldIZone = 0;
    public static final double kHatchHoldF = 0.052;

    public static final int kHatchClosedLoopUpPort = 1;
    public static final double kHatchClosedLoopUpP = 0.5;
    public static final double kHatchClosedLoopUpI = 0.0005;
    public static final double kHatchClosedLoopUpD = 0.0;
    public static final double kHatchClosedLoopUpIZone = 0;
    public static final double kHatchClosedLoopUpF = 0.005;

    public static final int kHatchClosedLoopDownPort = 2;
    public static final double kHatchClosedLoopDownP = 0.5;
    public static final double kHatchClosedLoopDownI = 0.00002;
    public static final double kHatchClosedLoopDownD = 0.0;
    public static final double kHatchClosedLoopDownIZone = 0;
    public static final double kHatchClosedLoopDownF = 0.0;

    public static final double kHatchPivotSpeed = 0.5;

    public static final int kHatchPivotPort = 13;

    public static final int kHatchForwardChannel = 3;
    public static final int kHatchReverseChannel = 4;

    public static final int kRatchetForwardChannel = 5;
    public static final int kRatchetReverseChannel = 2;

    public static final double kSecureHatchTimeDelta = 2.0;
    public static final double kUnsecureHatchTimeDelta = 2.0;

    public static final int kHatchPingChannel = 0;
    public static final int kHatchEchoChannel = 0;

    public static final double kHatchAngleOffset = -91.5;

    public static final double kPositionExhaustCargo = -90;
    public static final double kPositionDeployHatch = -73;
    public static final double kPositionCargoIntake = -28;
    public static final double kPositionHang = 30;
    public static final double kHatchPivotGearRatio = 202.5/90.0; //motor rotations to actual rotations

    public static final double kHatchSensingRange = 3;
}

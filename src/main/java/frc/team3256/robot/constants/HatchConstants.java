package frc.team3256.robot.constants;

public class HatchConstants {
    public static final double kHatchP = 0.8;
    public static final double kHatchI = 0.0;
    public static final double kHatchD = 0.013;
    public static final double kHatchF = 0.00001;
    public static final double kHatchIz = 0.0;

    public static final double kHatchPivotSpeed = 0.75;

    public static final int kHatchPivotPort = 13;

    public static final int kHatchForwardChannel = 3;
    public static final int kHatchReverseChannel = 4;

    public static final int kRatchetForwardChannel = 5;
    public static final int kRatchetReverseChannel = 2;

    public static final int kHatchPingChannel = 0;
    public static final int kHatchEchoChannel = 0;

    public static final double kHatchAngleOffset = -94.5;

    public static final double kPositionStarting = -94.5;

    public static final double kPositionDeployHatch = -90;
    public static final double kPositionFoldIn = 0;
    public static final double kPositionCargoIntake = -35;
    public static final double kHatchPivotGearRatio = 202.5/90.0; //motor rotations to actual rotations

    public static final double kHatchSensingRange = 3;
}

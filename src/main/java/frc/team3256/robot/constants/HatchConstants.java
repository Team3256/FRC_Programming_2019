package frc.team3256.robot.constants;

public class HatchConstants {
    public static final double kHatchP = 0.7;
    public static final double kHatchI = 0.00011;
    public static final double kHatchD = 0.0007;
    public static final double kHatchF = 0;
    public static final double kHatchIz = 0;

    public static final double kHatchPivotSpeed = 0.2;

    public static final int kHatchPivotPort = 13;

    public static final int kHatchForwardChannel = 3;
    public static final int kHatchReverseChannel = 4;

    public static final int kRatchetForwardChannel = 5;
    public static final int kRatchetReverseChannel = 2;

    public static final double kPositionFloorIntakeHatch = 0;
    public static final double kPositionDeployHatch = -90;
    public static final double kPositionFoldIn = 0;
    public static final double kHatchPivotGearRatio = 27.0*60.0/42.0; //motor rotations to actual rotations
}

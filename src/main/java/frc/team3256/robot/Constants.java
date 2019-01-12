package frc.team3256.robot;

public class Constants {

    //Ports: No duplicates in each set
    //PWM
    public static final int kCargoIntakePort = 0;
    public static final int kHatchIntakePort = 0;
    public static final int kHatchPivotPort = 0;

    //Solenoids
    public static final int kDeployRightForward = 0;
    public static final int kDeployRightReverse = 0;
    public static final int kDeployLeftForward = 0;
    public static final int kDeployLeftReverse = 0;
    public static final int kDeployTopForward = 0;
    public static final int kDeployTopReverse = 0;
    public static final int kShifterForward = 0;
    public static final int kShifterReverse = 0;


    //CAN
    public static final int kLeftDriveMaster = 0;
    public static final int kLeftDriveSlave = 0;
    public static final int kRightDriveMaster = 0;
    public static final int kRightDriveSlave = 0;
    public static final int kElevatorMaster = 0;
    public static final int kElevatorSlave = 0;


    //DIO
    public static final int kHallEffectPort = 0;

    //Analog Inputs
    public static final int kSharpIR = 0;


    //Robot Constants
    //Units are in inches, seconds, or degrees                  Most Constants TBD
    public static final double kRobotTrack = 0;
    public static final double kScrubFactor = 1.0;
    public static final double kWheelDiameter = 0;
    public static final double kDriveEncoderScalingFactor = 0;
    public static final double kElevatorPulleyDiameter = 0*Math.PI;
    public static final double kElevatorGearRatio = 0;
    public static final double kElevatorMaxHeight = 0; //49.5
    public static final double kElevatorMinHeight = 0;
    public static final double kRampRate = 0.25;


    //Elevator Gains
    public static final int kElevatorHoldSlot = 0;
    public static final double kElevatorHoldP = 0;
    public static final double kElevatorHoldI = 0;
    public static final double kElevatorHoldD = 0;

    public static final int kElevatorMotionMagicUpSlot = 1;
    public static final double kElevatorMotionMagicUpF = 0;
    public static final double kElevatorMotionMagicUpP = 0;
    public static final double kElevatorMotionMagicUpI = 0;
    public static final double kElevatorMotionMagicUpD = 0;

    public static final int kElevatorMotionMagicDownSlot = 2;
    public static final double kElevatorMotionMagicDownF = 0;
    public static final double kElevatorMotionMagicDownP = 0;
    public static final double kElevatorMotionMagicDownI = 0;
    public static final double kElevatorMotionMagicDownD = 0;


    //Presets:
    public static final double kIntakeSharpIRMaxVoltage = 0;
    public static final double kIntakeSharpIRMinVoltage = 0;

    public static final double kCargoIntakePower = 0;
    public static final double kHatchIntakePower = 0;
    public static final double kCargoExhaustPower = 0;
    public static final double kPivotUpPower = 0;
    public static final double kPivotDownPower = 0;

    public static final double kElevatorUpManualPower = 0;
    public static final double kElevatorDownManualPower = -0;
    public static final double kElevatorUpSlowPower = 0;
    public static final double kDropPreset = 10.0;
    public static final double kHighHatchPreset = 0;
    public static final double kMidHatchPreset = 0;
    public static final double kLowHatchPreset = 0;
    public static final double kHighCargoPreset = 0;
    public static final double kMidCargoPreset = 0;
    public static final double kLowCargoPreset = 0;

    public static final double kBottomHomeHeight = 0;
    public static final double kCompHomeHeight = 0;
    public static final double kCompBottomHomeHeight = 0;
    public static final double kTopHomeHeight = 0;
    public static final double kElevatorHomingUpTime = 0;

    public static final double kElevatorTolerance = 0;

    public static final double kElevatorMaxUpVoltage = 0;
    public static final double kElevatorMaxDownVoltage = 0;
    public static final double kElevatorMinHoldVoltage = 0;

    public static final double kHangPower = 0;

    public static final double kElevatorMaxVelocityUp = 0;
    public static final double kElevatorMaxAccelerationUp = 0;

    public static final double kElevatorMaxVelocityDown = 0;
    public static final double kElevatorMaxAccelerationDown = 0;
}

package frc.team3256.robot.operations;

public class Constants {

    //Ports: No duplicates in each set

    //Solenoids
    public static final int kDeployRightForward = 0; //TBD
    public static final int kDeployRightReverse = 1; //TBD
    public static final int kDeployLeftForward = 2; //TBD
    public static final int kDeployLeftReverse = 3; //TBD
    public static final int kDeployTopForward = 4; //TBD
    public static final int kDeployTopReverse = 5; //TBD
    public static final int kShifterForward = 1; //TBD
    public static final int kShifterReverse = 6; //TBD
    public static final int kHangerLeftForward = 0; //TBD
    public static final int kHangerLeftReverse = 0; //TBD
    public static final int kHangerRightForward = 0; //TBD
    public static final int kHangerRightReverse = 0; //TBD
    public static final int kRatchetPivotForward = 0; //TBD
    public static final int kRatchetPivotReverse = 0; //TBD

    //Pure Pursuit

    public static final double robotTrack = 27; //inches
    public static final double spacing = 6; //inches
    public static final double kA = 0.002; //0.002
    public static final double kP = 0; //0.01
    public static final double a = 1;
    public static final double b = 0.78;
    public static final double tolerance = 0.001;
    public static final double loopTime = 1.0/200.0; //how often Looper updates
    public static final double maxAccel = 85; //max robot acceleration
    public static final double maxVel = 60; //max robot velocity
    public static final double kV = 1/maxVel; // 1/max robot speed
    public static final double maxVelk = 1; //generally between 1-5

    public static final double leftVelkP = 0;
    public static final double leftVelkI = 0;
    public static final double leftVelkD = 0;


    public static final double rightVelkP = 0;
    public static final double rightVelkI = 0;
    public static final double rightVelkD = 0;

    //Curvature Drive

    public static double quickStopAccumulator = 0.0; //temporary curv. drive
    public static double kQuickStopAlpha = 0.1;
    public static double kQuickStopScalar = 2.0;
    public static final double kQuickTurnDeltaLimit = 2.0/1000.0/12.0*20.0;

    //CAN
    public static final int kLeftDriveMaster = 4;
    public static final int kLeftDriveSlave = 5;
    public static final int kRightDriveMaster = 3;
    public static final int kRightDriveSlave = 2;
    public static final int kHangDriveMaster = 0;
    public static final int kHangDriveSlave = 0;
    public static final int kCargoIntakePort = 0; //TBD
    public static final int kCargoPivotPort = 0; //TBD
    public static final int kCargoScoreLeftPort = 0; //TBD
    public static final int kCargoScoreRightPort = 0; //TBD
    public static final int kElevatorMaster = 0;
    public static final int kElevatorSlaveOne = 0;
    public static final int kElevatorSlaveTwo = 0;
    public static final int kElevatorSlaveThree = 0;
    public static final int khatchPivotPort = 0;


    //DIO
    public static final int kHallEffectPort = 0;

    //Analog Inputs
    public static final int kSharpIR = 0;


    //Robot Constants
    //Units are in inches, seconds, or degrees                  Most Constants TBD
    public static final double kRobotTrack = 0;
    public static final double kScrubFactor = 1.0;
    public static final double kWheelDiameter = 4;
    public static final double kDriveEncoderScalingFactor = 2.0*60.0/24.0; //3*60/24
    public static final double kHatchPivotGearRatio = 1; //TBD
    public static final double kElevatorPulleyDiameter = 0*Math.PI;
    public static final double kElevatorGearRatio = 0;
    public static final double kElevatorMaxHeight = 0; //49.5
    public static final double kElevatorMinHeight = 0;
    public static final double kRampRate = 0.25;
    public static final double kMagEncoderTicksTalon = 4096.0; //4096 = # of ticks per revolution | 4 X decoding for Talon SRX & Canifier only


    //Elevator Gains
    public static final int kElevatorHoldSlot = 0;
    public static final double kElevatorHoldP = 0;
    public static final double kElevatorHoldI = 0;
    public static final double kElevatorHoldD = 0;
    public static final double kElevatorHoldF = 0;
    public static final double kElevatorHoldIz = 0;

    public static final int kElevatorUpSlot = 0;
    public static final double kElevatorUpP = 0;
    public static final double kElevatorUpI = 0;
    public static final double kElevatorUpD = 0;
    public static final double kElevatorUpF = 0;
    public static final double kElevatorUpIz = 0;

    public static final int kElevatorDownSlot = 0;
    public static final double kElevatorDownP = 0;
    public static final double kElevatorDownI = 0;
    public static final double kElevatorDownD = 0;
    public static final double kElevatorDownF = 0;
    public static final double kElevatorDownIz = 0;

    //Cargo Pivot Gains
    public static final int kCargoPivotUpSlot = 0;
    public static final double kCargoPivotUpP = 0;
    public static final double kCargoPivotUpI = 0;
    public static final double kCargoPivotUpD = 0;
    public static final double kCargoPivotUpF = 0;
    public static final double kCargoPivotUpIz = 0;

    public static final int kCargoPivotDownSlot = 0;
    public static final double kCargoPivotDownP = 0;
    public static final double kCargoPivotDownI = 0;
    public static final double kCargoPivotDownD = 0;
    public static final double kCargoPivotDownF = 0;
    public static final double kCargoPivotDownIz = 0;

    //Hatch Pivot Gains
    public static final int kHatchPivotUpSlot = 0;
    public static final double kHatchPivotUpP = 0;
    public static final double kHatchPivotUpI = 0;
    public static final double kHatchPivotUpD = 0;
    public static final double kHatchPivotUpF = 0;
    public static final double kHatchPivotUpIz = 0;

    public static final int kHatchPivotDownSlot = 0;
    public static final double kHatchPivotDownP = 0;
    public static final double kHatchPivotDownI = 0;
    public static final double kHatchPivotDownD = 0;
    public static final double kHatchPivotDownF = 0;
    public static final double kHatchPivotDownIz = 0;

    //Presets:
    public static final double kIntakeSharpIRMaxVoltage = 0;
    public static final double kIntakeSharpIRMinVoltage = 0;
    public static final double kElevatorMinOutput = 0;
    public static final double kElevatorMaxOutput = 0;
    public static final double kCargoPivotMinOutput = 0;
    public static final double kCargoPivotMaxOutput = 0;


    //Hatch
    public static final double kHatchPivotUpPower = 0;
    public static final double kHatchPivotDownPower = 0;
    public static final double kHatchPivotTolerance = 1.0; //measured in degrees
    public static final double kHatchPivotFloorIntakePreset = 0; //measured in degrees
    public static final double kHatchPivotDeployPreset = 0; //measured in degrees


    //Cargo
    public static final double kCargoIntakePower = 0;
    public static final double kCargoExhaustPower = 0;
    public static final double kCargoScorePower = 0;
    public static final double kCargoPivotUpPower = 0;
    public static final double kCargoPivotDownPower = 0;
    public static final double kCargoPivotFloorPreset = 0;
    public static final double kCargoPivotClearancePreset = 0;
    public static final double kCargoPivotTransferPreset = 0;
    public static final double kCargoPivotFoldInPreset = 0;


    //Elevator
    public static final double kElevatorUpManualPower = 0;
    public static final double kElevatorDownManualPower = -0;
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

package frc.team3256.robot.operations;

public class Constants {
	public static final double kGearRatio = 6.6 / 1.0; //motor rotations to wheel rotations
	//Ports: No duplicates in each set

	//Solenoids
	public static final int kDeployHatchForward = 0; //TBD
	public static final int kDeployHatchReverse = 0; //TBD
	public static final int kShifterForward = 5; //TBD
	public static final int kShifterReverse = 2; //TBD
	public static final int kHangerForward = 0; //TBD
	public static final int kHangerReverse = 0; //TBD
	public static final int kRatchetPivotForward = 0; //TBD
	public static final int kRatchetPivotReverse = 0; //TBD

	//Pure Pursuit
	public static final double robotTrack = 27; //inches
	public static final double spacing = 6; //inches
	public static final double kA = 0.002; //0.002
	public static final double kP = 0.001; //0.01
	public static final double b = 0.9;
	public static final double a = 1 - b;
	public static final double tolerance = 0.001;
	public static final double loopTime = 1.0 / 200.0; //how often Looper updates
	public static final double lookaheadDistance = 21;
	public static final double maxAccel = 100; //max robot acceleration
	public static final double maxVel = 276; //max robot velocity
	public static final double kV = 1 / maxVel; // 1/max robot speed
	public static final double maxVelk = 3; //generally between 1-5

	//Curvature Drive
	public static final double kQuickTurnDeltaLimit = 2.0 / 1000.0 / 12.0 * 20.0;
	//CAN
	public static final int kLeftDriveMaster = 2;
	public static final int kLeftDriveSlave = 3;
	public static final int kRightDriveMaster = 4;
	public static final int kRightDriveSlave = 6;
	public static final int kLeftHangDrive = 0;
	public static final int kRightHangDrive = 0;
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
	public static final double kDriveEncoderScalingFactor = 2.12 * 60.0 / 24.0; //3*60/24
	public static final double kHatchPivotGearRatio = 1; //TBD
	public static final double kElevatorPulleyDiameter = 0 * Math.PI;
	public static final double kElevatorGearRatio = 0;
	public static final double kElevatorMaxHeight = 0; //49.5
	public static final double kElevatorMinHeight = 0;
	public static final double kRampRate = 0.25;
	public static final double kMagEncoderTicksTalon = 4096.0; //4096 = # of ticks per revolution | 4 X decoding for Talon SRX & Canifier only
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
	public static final double kCargoScorePower = 0.5;
	public static final double kCargoPivotUpPower = -0.5;
	public static final double kCargoPivotFloorPreset = 0;
	public static final double kCargoPivotClearancePreset = 0;
	public static final double kCargoPivotTransferPreset = 0;
	public static final double kCargoPivotFoldInPreset = 0;
	//Elevator
	public static double quickStopAccumulator = 0.0; //temporary curv. drive
	public static double kQuickStopAlpha = 0.1;
	public static double kQuickStopScalar = 2.0;
}

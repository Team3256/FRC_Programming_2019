package frc.team3256.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.auto.*;
import frc.team3256.robot.subsystems.*;
import frc.team3256.robot.teleop.TeleopUpdater;
import frc.team3256.robot.teleop.control.XboxDriverController;
import frc.team3256.warriorlib.auto.AutoModeExecuter;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitTracker;
import frc.team3256.warriorlib.loop.Looper;
import frc.team3256.warriorlib.subsystem.DriveTrainBase;
import frc.team3256.warriorlib.subsystem.SubsystemManager;

public class Robot extends TimedRobot {

//	// Subsystems
	private SubsystemManager subsystemManager;
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Elevator elevator = Elevator.getInstance();
	private Pivot pivot = Pivot.getInstance();
	private Hanger hanger = Hanger.getInstance();
	private CargoIntake intake = CargoIntake.getInstance();

	private RobotCompressor robotCompressor = RobotCompressor.getInstance();

	// Pure Pursuit
	private PurePursuitTracker purePursuitTracker = PurePursuitTracker.getInstance();
	private PoseEstimator poseEstimator;

	// Loopers
	private Looper enabledLooper, poseEstimatorLooper;
	private TeleopUpdater teleopUpdater;

	//Auto Teleop control
	private AutoModeExecuter autoModeExecuter;
	private boolean maintainAutoExecution = true;

	/**
	 * This function is called when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		subsystemManager = new SubsystemManager();
		DriveTrainBase.setDriveTrain(driveTrain);

		Paths.initialize();

		enabledLooper = new Looper(1 / 200D);
		// Reset sensors
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
		pivot.zeroSensors();

		enabledLooper.addLoops(driveTrain, pivot, elevator, intake);

		poseEstimatorLooper = new Looper(1 / 50D);
		poseEstimator = PoseEstimator.getInstance();
		poseEstimatorLooper.addLoops(poseEstimator);
		poseEstimatorLooper.start();

		// Default SmartDashboard
		SmartDashboard.putBoolean("visionEnabled", true);
		SmartDashboard.putBoolean("autoEnabled", true);
		SmartDashboard.putString("ControlScheme", "Cargo");

		// Pneumatics
		robotCompressor.turnOn();
		pivot.releaseBrake();
		pivot.setHatchArm(false);

		teleopUpdater = TeleopUpdater.getInstance();

		subsystemManager.addSubsystems(driveTrain, elevator, intake, pivot, robotCompressor);
	}

	/**
	 * This function is called when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		enabledLooper.stop();

		driveTrain.setCoastMode();
		driveTrain.setHighGear(true);

		pivot.releaseBrake();
		pivot.setHatchArm(false);

		//elevator.runZeroPower();
		//cargoIntake.setIntakePower(0);
		driveTrain.runZeroPower();
		robotCompressor.turnOff();
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
		poseEstimator.reset();

		elevator.setWantedState(Elevator.WantedState.WANTS_TO_HOLD);
		pivot.setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_POS);
		intake.setWantedState(CargoIntake.WantedState.WANTS_TO_STOP);
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use
	 * this for items like diagnostics that you want ran during disabled,
	 * autonomous/sandstorm, teleoperated and test.
	 *
	 * <p>This runs after the mode specific periodic functions, but before
	 * LiveWindow and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic(){
		SmartDashboard.putNumber("Pose X", poseEstimator.getPose().x);
		SmartDashboard.putNumber("Pose Y", poseEstimator.getPose().y);
	}

	/**
	 * This function is called when autonomous/sandstorm starts.
	 */
	@Override
	public void autonomousInit() {
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
		driveTrain.setBrakeMode();
		//hatchPivot.zeroSensors();

		poseEstimator.reset();
		purePursuitTracker.reset();
		enabledLooper.start();

		//SmartDashboard.putString("alliance", DriverStation.getInstance().getAlliance().name());

		if (SmartDashboard.getBoolean("autoEnabled", false)) {
			maintainAutoExecution = true;

			autoModeExecuter = new AutoModeExecuter();
			autoModeExecuter.setAutoMode(new AlignToTargetMode());
			autoModeExecuter.start();
		}
		else {
			maintainAutoExecution = false;
		}
	}

	/**
	 * This function is called periodically during autonomous/sandstorm.
	 */
	@Override
	public void autonomousPeriodic() {
		//basic logic below: keep executing auto until we disable it or it finishes, and don't allow it to be re-enabled
		if (!maintainAutoExecution) {
			teleopUpdater.update();
		} else if (XboxDriverController.getInstance().getShouldStopAuto() || autoModeExecuter.isFinished()) {
			maintainAutoExecution = false;
			if (!autoModeExecuter.isFinished()) {
				autoModeExecuter.stop();
				//make sure all our subsystems stop
				//elevator.runZeroPower();
				//driveTrain.setPowerClosedLoop(0, 0);
				//hatchPivot.setPositionDeploy();
			}
			enabledLooper.start();
			subsystemManager.outputToDashboard();
		}
	}

	/**
	 * This function is called when teleop starts.
	 */
	@Override
	public void teleopInit() {
		robotCompressor.turnOn();
		driveTrain.setBrakeMode();
		enabledLooper.start();
		driveTrain.resetEncoders();
		driveTrain.setGyroOffset(180.0);
		poseEstimator.resetPosition();
		poseEstimator.offsetPoseAngle(180.0);
		pivot.releaseBrake();
	}

	/**
	 * This function is called periodically during teleop.
	 */
	@Override
	public void teleopPeriodic() {
		teleopUpdater.update();
		subsystemManager.outputToDashboard();

//		poseEstimator.reset();
		//SmartDashboard.putNumber("Gyro", DriveTrain.getInstance().getAngle());
//		SmartDashboard.putNumber("hatchPivot", hatchPivot.getAngle());
//		SmartDashboard.putNumber("hatchPosition", hatchPivot.getEncoderValue());
//		SmartDashboard.putBoolean("hallEffect", elevator.getHallEffectTriggered());
//		double robotCenterToCamera = 14.0 - 5.5;
//		double cameraDistance = SmartDashboard.getNumber("visionDistance0", 0);
//		double cameraAngleFromTarget = SmartDashboard.getNumber("visionAngle0",0) * Math.PI/180.0;
//		double m = Math.cos(cameraAngleFromTarget) * cameraDistance;
//		double n = Math.sin(cameraAngleFromTarget) * cameraDistance;
//		double angleFromTarget = Math.atan(n / (m + robotCenterToCamera));
//		double distance = n / Math.sin(angleFromTarget);
//		angleFromTarget = angleFromTarget * 180.0 / Math.PI;
//
//		double absPosition = 90-driveTrain.getAngle();
//
//
//		double angleDelta = (absPosition - angleFromTarget) * Math.PI / 180.0;
//		double x = Math.cos(angleDelta) * distance;
//		double y = Math.sin(angleDelta) * distance;
//		SmartDashboard.putNumber("Target Y", y);
//		SmartDashboard.putNumber("Target X", x);
//		SmartDashboard.putNumber("Gyro Angle", driveTrain.getAngle());
//		SmartDashboard.putNumber("Robot angle", (absPosition));
//		SmartDashboard.putNumber("Corrected distance", distance);
//		SmartDashboard.putNumber("Corrected angle", angleFromTarget);
//		SmartDashboard.putNumber("Total angle", (absPosition - angleFromTarget));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
//		poseEstimatorLooper.start();
//		System.out.println("Pose: " + poseEstimator.getPose());
//		SmartDashboard.putString("Pose", poseEstimator.getPose().toString());
		//cargoIntake.setIntakePower(0.5);
//		SmartDashboard.putNumber("Pose X", poseEstimator.getPose().x);
//		SmartDashboard.putNumber("Pose Y", poseEstimator.getPose().y);
//		SmartDashboard.putNumber("right encoder", driveTrain.getRightDistance());
//		SmartDashboard.putNumber("left encoder", driveTrain.getLeftDistance());

		double distance = SmartDashboard.getNumber("visionDistance1", 0);
		double angleFromTarget = SmartDashboard.getNumber("visionAngle1",0);
		double absPosition = -driveTrain.getAngle() - 90;
		while (absPosition < -180)
			absPosition += 360;
		while (absPosition > 180)
			absPosition -= 360;
		absPosition = 180 - absPosition;
		double angleDelta = (absPosition - angleFromTarget) * Math.PI/180;
		double x = Math.cos(angleDelta) * distance;
		double y = Math.sin(angleDelta) * distance;

//		SmartDashboard.putNumber("Y", y);
//		SmartDashboard.putNumber("X", x);
//		SmartDashboard.putNumber("Gyro Angle", driveTrain.getAngle());
//        SmartDashboard.putNumber("Robot angle", (absPosition));
//        SmartDashboard.putNumber("Total angle", (absPosition - angleFromTarget));
	}

	@Override
	public void disabledPeriodic() {
		subsystemManager.outputToDashboard();
//		System.out.println("Pose: " + poseEstimator.getPose());
//		System.out.println("Gyro: " + driveTrain.getAngle());
//		poseEstimator.reset();
		//SmartDashboard.putNumber("Gyro", DriveTrain.getInstance().getAngle());
//		SmartDashboard.putNumber("hatchPivot", hatchPivot.getAngle());
//		SmartDashboard.putNumber("hatchPosition", hatchPivot.getEncoderValue());
//		SmartDashboard.putBoolean("hallEffect", elevator.getHallEffectTriggered());
//		double robotCenterToCamera = 14.0 - 5.5;
//		double cameraDistance = SmartDashboard.getNumber("visionDistance0", 0);
//		double cameraAngleFromTarget = SmartDashboard.getNumber("visionAngle0",0) * Math.PI/180.0;
//		double m = Math.cos(cameraAngleFromTarget) * cameraDistance;
//		double n = Math.sin(cameraAngleFromTarget) * cameraDistance;
//		double angleFromTarget = Math.atan(n / (m + robotCenterToCamera));
//		double distance = n / Math.sin(angleFromTarget);
//		angleFromTarget = angleFromTarget * 180.0 / Math.PI;
//
//		double absPosition = 270-driveTrain.getAngle();
//
//
//		double angleDelta = (absPosition - angleFromTarget) * Math.PI / 180.0;
//		double x = Math.cos(angleDelta) * distance;
//		double y = Math.sin(angleDelta) * distance;
//		SmartDashboard.putNumber("Target Y", y);
//		SmartDashboard.putNumber("Target X", x);
//		SmartDashboard.putNumber("Gyro Angle", driveTrain.getAngle());
//		SmartDashboard.putNumber("Robot angle", (absPosition));
//		SmartDashboard.putNumber("Corrected distance", distance);
//		SmartDashboard.putNumber("Corrected angle", angleFromTarget);
//		SmartDashboard.putNumber("Total angle", (absPosition - angleFromTarget));
	}
}
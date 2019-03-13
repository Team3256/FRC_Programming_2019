package frc.team3256.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.auto.*;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.robot.teleop.TeleopUpdater;
import frc.team3256.warriorlib.auto.AutoModeExecuter;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitTracker;
import frc.team3256.warriorlib.loop.Looper;
import frc.team3256.warriorlib.subsystem.DriveTrainBase;

public class Robot extends TimedRobot {

//	// Subsystems
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Elevator elevator = Elevator.getInstance();
	private CargoIntake cargoIntake = CargoIntake.getInstance();
	private HatchPivot hatchPivot = HatchPivot.getInstance();

	// Pure Pursuit
	private PurePursuitTracker purePursuitTracker = PurePursuitTracker.getInstance();
	private PoseEstimator poseEstimator;

	// Loopers
	private Looper teleopLooper, poseEstimatorLooper;
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
//		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//		camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 640, 360, 15);
		DriveTrainBase.setDriveTrain(driveTrain);

		Paths.initialize();

		teleopLooper = new Looper(1 / 200D);
		driveTrain.resetEncoders();
		driveTrain.resetGyro();

		teleopLooper.addLoops(driveTrain, cargoIntake, hatchPivot, elevator);

		poseEstimatorLooper = new Looper(1 / 50D);
		poseEstimator = PoseEstimator.getInstance();
		poseEstimatorLooper.addLoops(poseEstimator);
		poseEstimatorLooper.start();

		teleopUpdater = TeleopUpdater.getInstance();
		SmartDashboard.putBoolean("visionEnabled", true);
		SmartDashboard.putBoolean("autoEnabled", true);
		SmartDashboard.putString("ControlScheme", "Cargo");

		hatchPivot.zeroSensors();
	}

	/**
	 * This function is called when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		teleopLooper.stop();

		driveTrain.setCoastMode();
		driveTrain.setHighGear(true);

		elevator.runZeroPower();
		cargoIntake.setIntakePower(0);
		driveTrain.runZeroPower();
		hatchPivot.setPositionDeploy();
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

		//SmartDashboard.putString("alliance", DriverStation.getInstance().getAlliance().name());

		if (SmartDashboard.getBoolean("autoEnabled", false)) {
			maintainAutoExecution = true;
			teleopLooper.stop();

			autoModeExecuter = new AutoModeExecuter();
			autoModeExecuter.setAutoMode(new AlignToTargetMode());
			autoModeExecuter.start();
		}
		else {
			maintainAutoExecution = false;
			teleopLooper.start();
		}
	}

	/**
	 * This function is called periodically during autonomous/sandstorm.
	 */
	@Override
	public void autonomousPeriodic() {
		boolean stopAuto = TeleopUpdater.getInstance().getDriverController().getAButtonPressed();
		//basic logic below: keep executing auto until we disable it or it finishes, and don't allow it to be re-enabled
		if (!maintainAutoExecution) {
			teleopUpdater.update();
		} else if (stopAuto || autoModeExecuter.isFinished()) {
			maintainAutoExecution = false;
			if (!autoModeExecuter.isFinished()) {
				autoModeExecuter.stop();
				//make sure all our subsystems stop
				elevator.runZeroPower();
				cargoIntake.setIntakePower(0);
				driveTrain.setPowerClosedLoop(0, 0);
				hatchPivot.setPositionDeploy();
			}
			teleopLooper.start();
		}
	}

	/**
	 * This function is called when teleop starts.
	 */
	@Override
	public void teleopInit() {
		driveTrain.setBrakeMode();
		teleopLooper.start();
	}

	/**
	 * This function is called periodically during teleop.
	 */
	@Override
	public void teleopPeriodic() {
		teleopUpdater.update();
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
		SmartDashboard.putNumber("Y", y);
		SmartDashboard.putNumber("X", x);
		SmartDashboard.putNumber("Gyro Angle", driveTrain.getAngle());
        SmartDashboard.putNumber("Robot angle", (absPosition));
        SmartDashboard.putNumber("Total angle", (absPosition - angleFromTarget));
	}

	@Override
	public void disabledPeriodic() {
		System.out.println("Pose: " + poseEstimator.getPose());
		System.out.println("Gyro: " + driveTrain.getAngle());
		//SmartDashboard.putNumber("Gyro", DriveTrain.getInstance().getAngle());
//		SmartDashboard.putNumber("hatchPivot", hatchPivot.getAngle());
//		SmartDashboard.putNumber("hatchPosition", hatchPivot.getEncoderValue());
//		SmartDashboard.putBoolean("hallEffect", elevator.getHallEffectTriggered());
        double robotCenterToCamera = 14.0 - 5.5;
        double cameraDistance = SmartDashboard.getNumber("visionDistance0", 0);
        double cameraAngleFromTarget = SmartDashboard.getNumber("visionAngle0",0) * Math.PI/180.0;
        double m = Math.cos(cameraAngleFromTarget) * cameraDistance;
        double n = Math.sin(cameraAngleFromTarget) * cameraDistance;
        double angleFromTarget = Math.atan(n / (m + robotCenterToCamera));
        double distance = n / Math.sin(angleFromTarget);
        angleFromTarget = angleFromTarget * 180.0 / Math.PI;

        double absPosition = -driveTrain.getAngle() - 90;
        while (absPosition < -180)
            absPosition += 360;
        while (absPosition > 180)
            absPosition -= 360;
        absPosition = 180 - absPosition;


        double angleDelta = (absPosition - angleFromTarget) * Math.PI / 180.0;
        double x = Math.cos(angleDelta) * distance;
        double y = Math.sin(angleDelta) * distance;
        SmartDashboard.putNumber("Target Y (test mode)", y);
        SmartDashboard.putNumber("Target X (test mode)", x);
        SmartDashboard.putNumber("Gyro Angle", driveTrain.getAngle());
        SmartDashboard.putNumber("Robot angle", (absPosition));
        SmartDashboard.putNumber("Corrected distance", distance);
        SmartDashboard.putNumber("Corrected angle", angleFromTarget);
        SmartDashboard.putNumber("Total angle", (absPosition - angleFromTarget));
	}
}
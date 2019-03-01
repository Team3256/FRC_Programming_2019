package frc.team3256.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.auto.AutoTestMode;
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
	private boolean maintainAutoExecution = false;

	/**
	 * This function is called when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		DriveTrainBase.setDriveTrain(driveTrain);

		teleopLooper = new Looper(1 / 200D);
		driveTrain.resetEncoders();
		driveTrain.resetGyro();

		teleopLooper.addLoops(driveTrain, cargoIntake, hatchPivot, elevator);

		poseEstimatorLooper = new Looper(1 / 50D);
		poseEstimator = PoseEstimator.getInstance();
		poseEstimatorLooper.addLoops(poseEstimator);

		teleopUpdater = TeleopUpdater.getInstance();
		SmartDashboard.putBoolean("visionEnabled", false);
		SmartDashboard.putBoolean("autoEnabled", false);
	}

	/**
	 * This function is called when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		teleopLooper.stop();

		driveTrain.setCoastMode();
		driveTrain.setHighGear(true);
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

		SmartDashboard.putString("alliance", DriverStation.getInstance().getAlliance().name());

		if (SmartDashboard.getBoolean("autoEnabled", false)) {
			maintainAutoExecution = true;
			teleopLooper.stop();

			poseEstimatorLooper.start();

			autoModeExecuter = new AutoModeExecuter();
			autoModeExecuter.setAutoMode(new AutoTestMode());
			autoModeExecuter.start();
		}
		else {
			teleopLooper.start();
		}
	}

	/**
	 * This function is called periodically during autonomous/sandstorm.
	 */
	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Pose X", poseEstimator.getPose().x);
		SmartDashboard.putNumber("Pose Y", poseEstimator.getPose().y);
		SmartDashboard.putNumber("left enc", driveTrain.getLeftDistance());
		SmartDashboard.putNumber("right enc", driveTrain.getRightDistance());
		SmartDashboard.putNumber("angle", driveTrain.getAngle());

		boolean stopAuto = TeleopUpdater.getInstance().getDriverController().getAButtonPressed();
		//basic logic below: keep executing auto until we disable it or it finishes, and don't allow it to be re-enabled
		if (!maintainAutoExecution) {
			teleopUpdater.update();
		} else if (stopAuto || autoModeExecuter.isFinished()) {
			maintainAutoExecution = false;
			if (!autoModeExecuter.isFinished()) {
				autoModeExecuter.stop();
				//make sure all our subsystems stop
				elevator.moveToTarget();
				cargoIntake.setIntakePower(0);
				driveTrain.setOpenLoop(0, 0);
				hatchPivot.setHatchPivotPower(0);
			}
			poseEstimatorLooper.stop();
			teleopLooper.start();
		}
	}

	/**
	 * This function is called when teleop starts.
	 */
	@Override
	public void teleopInit() {
		driveTrain.setBrakeMode();
		poseEstimatorLooper.stop();
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
		poseEstimatorLooper.start();
		SmartDashboard.putString("Pose", poseEstimator.getPose().toString());
		cargoIntake.setIntakePower(0.5);
//		SmartDashboard.putNumber("Pose X", poseEstimator.getPose().x);
//		SmartDashboard.putNumber("Pose Y", poseEstimator.getPose().y);
//		SmartDashboard.putNumber("left_enc", driveTrain.getLeftDistance());
//		SmartDashboard.putNumber("right_enc", driveTrain.getRightDistance());
//		System.out.println("right encoder:" + driveTrain.getRightDistance());
//		System.out.println("left encoder:" + driveTrain.getLeftDistance());
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putNumber("angle", driveTrain.getAngle());
		SmartDashboard.putNumber("hatchPivot", hatchPivot.getAngle());
		SmartDashboard.putBoolean("hallEffect", elevator.getHallEffectTriggered());
	}
}
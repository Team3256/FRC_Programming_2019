package frc.team3256.robot;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.auto.AutoTestMode;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.subsystems.BallShooter;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.robot.subsystems.cargointake.CargoIntake;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.elevator.Elevator;
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
	private BallShooter ballShooter = BallShooter.getInstance();
	private HatchPivot hatchPivot = HatchPivot.getInstance();

	// Pure Pursuit
	private PurePursuitTracker purePursuitTracker;
	private PoseEstimator poseEstimator;

	// Loopers
	private Looper enabledLooper, poseEstimatorLooper;
	private TeleopUpdater teleopUpdater;

	private CANSparkMax leftMotor, rightMotor;
	private CANPIDController leftPIDController, rightPIDController;
	private CANEncoder leftEncoder, rightEncoder;

	private Compressor compressor;

	/**
	 * This function is called when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		enabledLooper = new Looper(1 / 200D);
		driveTrain.resetEncoders();
		driveTrain.resetGyro();

		enabledLooper.addLoops(driveTrain, cargoIntake, hatchPivot, ballShooter);

		DriveTrainBase.setDriveTrain(driveTrain);

		poseEstimatorLooper = new Looper(1 / 50D);
		poseEstimator = PoseEstimator.getInstance();
		poseEstimatorLooper.addLoops(poseEstimator);

		teleopUpdater = TeleopUpdater.getInstance();

		SmartDashboard.putBoolean("visionEnabled", false);

		/*
		PathGenerator pathGenerator = new PathGenerator(Constants.spacing, true);
		pathGenerator.addPoint(new Vector(0, 0));
		pathGenerator.addPoint(new Vector(0, 30));
		pathGenerator.addPoint(new Vector(70, 60));
		pathGenerator.addPoint(new Vector(70, 80));
		pathGenerator.addPoint(new Vector(70, 102));
		pathGenerator.setSmoothingParameters(Constants.a, Constants.b, Constants.tolerance);
		pathGenerator.setVelocities(Constants.maxVel, Constants.maxAccel, Constants.maxVelk);
		Path path = pathGenerator.generatePath();

		purePursuitTracker = PurePursuitTracker.getInstance();
		purePursuitTracker.setRobotTrack(Constants.robotTrack);
		//purePursuitTracker.setFeedbackMultiplier(Constants.kP);
		purePursuitTracker.setPaths(Collections.singletonList(path), Constants.lookaheadDistance);
		*/

		//Compressor compressor = new Compressor(15);
		//compressor.setClosedLoopControl(true);
	}

	/**
	 * This function is called when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		enabledLooper.stop();
		poseEstimatorLooper.stop();

		driveTrain.resetGyro();
		driveTrain.resetEncoders();
		driveTrain.setBrakeMode();

		poseEstimator.reset();
//		purePursuitTracker.reset();
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
		AutoModeExecuter autoModeExecuter = new AutoModeExecuter();
		autoModeExecuter.setAutoMode(new AutoTestMode());
		autoModeExecuter.start();

//		enabledLooper.stop();
//
//		driveTrain.setBrakeMode();
//		driveTrain.resetEncoders();
//		driveTrain.resetGyro();
//		poseEstimator.reset();
//		purePursuitTracker.reset();
//
//		poseEstimatorLooper.start();
//		//hatchPivot.zeroSensors();
//
//		AutoModeExecuter autoModeExecuter = new AutoModeExecuter();
//		autoModeExecuter.setAutoMode(new PurePursuitTestMode());
//		autoModeExecuter.start();
	}

	/**
	 * This function is called periodically during autonomous/sandstorm.
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called when teleop starts.
	 */
	@Override
	public void teleopInit() {
		enabledLooper.start();
		//elevator.setPosition(198);
		//poseEstimatorLooper.start();
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
	}

	@Override
	public void disabledPeriodic() {

	}
}
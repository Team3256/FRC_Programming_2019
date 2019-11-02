package frc.team3256.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3256.robot.action.RamseteTestAutoMode;
import frc.team3256.robot.odometry.PoseEstimatorRamsete;
import frc.team3256.robot.teleop.TeleopUpdater;
import frc.team3256.warriorlib.auto.AutoModeExecuter;
import frc.team3256.warriorlib.loop.Looper;
import frc.team3256.warriorlib.subsystem.DriveTrainBase;

public class Robot extends TimedRobot {

	private AutoModeExecuter autoModeExecuter;

//	// Subsystems
	private DriveTrain driveTrain = DriveTrain.getInstance();
	// Loopers
	private Looper driveTrainLooper, poseLooper;
	private PoseEstimatorRamsete poseEstimator;
	private TeleopUpdater teleopUpdater;

	private RamseteTestAutoMode r;


	/**
	 * This function is called when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
//		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//		camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 640, 360, 15);
		DriveTrainBase.setDriveTrain(driveTrain);

		poseLooper = new Looper(1/50D);
		driveTrainLooper = new Looper(1 / 300D);
		driveTrainLooper.addLoops(driveTrain);
		teleopUpdater = TeleopUpdater.getInstance();
		poseEstimator = PoseEstimatorRamsete.getInstance();
		poseLooper.addLoops(poseEstimator);
		poseLooper.start();
		poseEstimator.reset();
		driveTrain.resetEncoders();
		driveTrain.resetGyro();


		r = new RamseteTestAutoMode();
	}

	/**
	 * This function is called when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		driveTrainLooper.stop();

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
//		System.out.println("Angle: " + poseEstimator.getTheta());
	}

	/**
	 * This function is called when autonomous/sandstorm starts.
	 */
	@Override
	public void autonomousInit() {
		poseLooper.start();
		poseEstimator.reset();
		autoModeExecuter = new AutoModeExecuter();
		autoModeExecuter.setAutoMode(r);
		autoModeExecuter.start();

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
	}

	/**
	 * This function is called periodically during teleop.
	 */
	@Override
	public void teleopPeriodic() {
		teleopUpdater.update();
//		System.out.println("Left dist: " + driveTrain.getLeftDistance());
//		System.out.println("Right dist: " + driveTrain.getRightDistance());
		System.out.println("left encoder: "+driveTrain);
//		System.out.println("Pose: " + poseEstimator.getPoseX()+", "+poseEstimator.getPoseY());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	@Override
	public void disabledPeriodic() {
//		System.out.println("x: " + poseEstimator.getPoseX());
//		System.out.println("y: " + poseEstimator.getPoseY());
//		System.out.println("Angle: " + poseEstimator.getPoseTheta());

	}
}
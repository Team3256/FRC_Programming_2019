package frc.team3256.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3256.robot.auto.PurePursuitTestMode;
import frc.team3256.robot.operation.TeleopUpdater;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.AutoModeExecuter;
import frc.team3256.warriorlib.auto.purepursuit.*;
import frc.team3256.warriorlib.loop.Looper;
import frc.team3256.warriorlib.math.Vector;
import frc.team3256.warriorlib.subsystem.DriveTrainBase;

public class Robot extends TimedRobot {

	DriveTrain driveTrain = DriveTrain.getInstance();
	PurePursuitTracker purePursuitTracker;
	PoseEstimator poseEstimator;

	Looper enabledLooper, poseEstimatorLooper;
	TeleopUpdater teleopUpdater;

	/**
	 * This function is called when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		enabledLooper = new Looper(1 / 200D);
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
		enabledLooper.addLoops(driveTrain);

		DriveTrainBase.setDriveTrain(driveTrain);

		poseEstimatorLooper = new Looper(1 / 50D);
		poseEstimator = PoseEstimator.getInstance();
		poseEstimatorLooper.addLoops(poseEstimator);

		teleopUpdater = TeleopUpdater.getInstance();

		PathGenerator pathGenerator = new PathGenerator(Constants.spacing);
		pathGenerator.addPoint(new Vector(0, 0));
		pathGenerator.addPoint(new Vector(0, 30));
		pathGenerator.addPoint(new Vector(70, 60));
		pathGenerator.addPoint(new Vector(70, 80));
		pathGenerator.addPoint(new Vector(70, 103));
		pathGenerator.setSmoothingParameters(Constants.a, Constants.b, Constants.tolerance);
		pathGenerator.setVelocities(Constants.maxVel, Constants.maxAccel, Constants.maxVelk);
		Path path = pathGenerator.generatePath();

		purePursuitTracker = PurePursuitTracker.getInstance();
		purePursuitTracker.setRobotTrack(Constants.robotTrack);
		purePursuitTracker.setPath(path, Constants.lookaheadDistance);
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
		driveTrain.setCoastMode();

		poseEstimator.reset();
		purePursuitTracker.reset();
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
	public void robotPeriodic() {

	}

	/**
	 * This function is called when autonomous/sandstorm starts.
	 */
	@Override
	public void autonomousInit() {
		enabledLooper.stop();

		driveTrain.setBrakeMode();
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
		poseEstimator.reset();
		purePursuitTracker.reset();

		poseEstimatorLooper.start();

		AutoModeExecuter autoModeExecuter = new AutoModeExecuter();
		autoModeExecuter.setAutoMode(new PurePursuitTestMode());
		autoModeExecuter.start();
	}

	/**
	 * This function is called periodically during autonomous/sandstorm.
	 */
	@Override
	public void autonomousPeriodic() {
		/*
		System.out.println("Pose: " + poseEstimator.getPose());
		System.out.println("LEFT ENC " + driveTrain.getLeftDistance() + " RIGHT ENC " + driveTrain.getRightDistance());
		System.out.println("Angle: " + driveTrain.getAngle());

        System.out.println("left: " + driveTrain.getLeftDistance());
        System.out.println("right: " + driveTrain.getRightDistance());
        System.out.println("angle: " + driveTrain.getAngle());
        System.out.println("pose: " + poseEstimator.getPose());
        */
	}

	/**
	 * This function is called when teleop starts.
	 */
	@Override
	public void teleopInit() {
		enabledLooper.start();
		poseEstimatorLooper.stop();
	}

	/**
	 * This function is called periodically during teleop.
	 */
	@Override
	public void teleopPeriodic() {
		teleopUpdater.update();
		//System.out.println("left encoder: "+driveTrain.getLeftDistance());
		//System.out.println("right encoder: "+driveTrain.getRightDistance());
		//System.out.println("angle " + driveTrain.getGyro().getAngle());
		//System.out.println("Connected: " + driveTrain.getGyro().isConnected());

        /*
        System.out.println("right master: " + driveTrain.getRightDistance());
        System.out.println("left master: " + driveTrain.getLeftDistance());
        System.out.println();
        System.out.println("gyro: " + driveTrain.getAngle());
        //System.out.println("vel: " + driveTrain.getVelocity());*/
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		poseEstimatorLooper.start();
		System.out.println("pose: " + poseEstimator.getPose());
	}
}
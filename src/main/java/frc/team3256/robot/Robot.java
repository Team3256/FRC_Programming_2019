package frc.team3256.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3256.robot.teleop.TeleopUpdater;
import frc.team3256.warriorlib.auto.AutoModeExecuter;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitTracker;
import frc.team3256.warriorlib.loop.Looper;
import frc.team3256.warriorlib.subsystem.DriveTrainBase;

import java.io.PrintStream;

public class Robot extends TimedRobot {

//	// Subsystems
	private DriveTrain driveTrain = DriveTrain.getInstance();
	// Loopers
	private Looper teleopLooper;
	private TeleopUpdater teleopUpdater;


	/**
	 * This function is called when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
//		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//		camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 640, 360, 15);
		DriveTrainBase.setDriveTrain(driveTrain);

		teleopLooper = new Looper(1 / 200D);
		teleopLooper.addLoops(driveTrain);
		teleopUpdater = TeleopUpdater.getInstance();
	}

	/**
	 * This function is called when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		teleopLooper.stop();
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
package frc.team3256.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3256.robot.math.Vector;
import frc.team3256.robot.odometry.PoseEstimator;
import frc.team3256.robot.operation.TeleopUpdater;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.operations.DrivePower;
import frc.team3256.robot.path.Path;
import frc.team3256.robot.path.PurePursuitTracker;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.hardware.ADXRS453_Calibrator;
import frc.team3256.warriorlib.loop.Looper;

/**
 * Contains all main robot code.
 */
public class Robot extends TimedRobot {

    Path p;
    PurePursuitTracker purePursuitTracker;
    PoseEstimator poseEstimator = new PoseEstimator(new Vector(0,0));
    DriveTrain driveTrain = DriveTrain.getInstance();
    DrivePower drivePower;
    Looper enabledLooper;
    TeleopUpdater teleopUpdater;
    ADXRS453_Calibrator gyroCalibrator;

    /**
     * This function is called when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        enabledLooper = new Looper(1.0/200.0);
        enabledLooper.addLoops(driveTrain, poseEstimator);
        teleopUpdater = new TeleopUpdater();
        //driveTrain.getGyro().calibrate();
        gyroCalibrator = new ADXRS453_Calibrator(driveTrain.getGyro());
}

    /**
     * This function is called when the robot is disabled.
     */
    @Override
    public void disabledInit() {
        enabledLooper.stop();
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
        driveTrain.resetEncoders();
        p = new Path(Constants.a, Constants.b, Constants.tolerance);
        purePursuitTracker = new PurePursuitTracker(p, 20, 20);
        poseEstimator = PoseEstimator.getInstance();
        p.addSegment(new Vector(0,0), new Vector(0, 100));
        p.setTargetVelocities(Constants.maxVel, Constants.maxAccel, Constants.maxVelk);
        p.setCurvature();
        enabledLooper.start();
    }

    /**
     * This function is called periodically during autonomous/sandstorm.
     */
    @Override
    public void autonomousPeriodic() {
        drivePower = purePursuitTracker.update(poseEstimator.getPose(), driveTrain.getVelocity(), driveTrain.getAngle());
        driveTrain.setOpenLoop(drivePower.getLeft(), drivePower.getRight());
    }

    /**
     * This function is called when teleop starts.
     */
    @Override
    public void teleopInit() {
        enabledLooper.stop();
        enabledLooper.start();
    }


    /**
     * This function is called periodically during teleop.
     */
    @Override
    public void teleopPeriodic() {
        //teleopUpdater.update();
        System.out.println("left encoder: "+driveTrain.getLeftDistance());
        System.out.println("right encoder: "+driveTrain.getRightDistance());
        System.out.println("angle "+driveTrain.getAngle());
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }
}

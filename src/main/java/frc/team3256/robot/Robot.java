package frc.team3256.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3256.robot.math.Vector;
import frc.team3256.robot.odometry.PoseEstimator;
import frc.team3256.robot.odometry.PurePursuitLoop;
import frc.team3256.robot.operation.TeleopUpdater;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.path.Path;
import frc.team3256.robot.path.PurePursuitTracker;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.loop.Looper;

public class Robot extends TimedRobot {

    PoseEstimator poseEstimator;
    DriveTrain driveTrain = DriveTrain.getInstance();
    Looper enabledLooper, purePursuitLooper;
    TeleopUpdater teleopUpdater;
    Path p;
    PurePursuitTracker purePursuitTracker;
    PurePursuitLoop purePursuitLoop;

    /**
     * This function is called when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        enabledLooper = new Looper(1/200D);
        purePursuitLooper = new Looper(1/25D);
        poseEstimator = PoseEstimator.getInstance();
        enabledLooper.addLoops(driveTrain);
        purePursuitLoop = new PurePursuitLoop();
        purePursuitLooper.addLoops(poseEstimator, purePursuitLoop);
        teleopUpdater = new TeleopUpdater();

        driveTrain.resetEncoders();
        p = new Path(0,0,6, 0);
        p.addSegment(new Vector(0,0), new Vector(0, 60));
        p.addSegment(new Vector(0, 60), new Vector(60, 90));
        p.addLastPoint();
        p.setTargetVelocities(Constants.maxVel, Constants.maxAccel, Constants.maxVelk);
        purePursuitTracker = new PurePursuitTracker(p, 15);
        purePursuitLoop.initPurePursuitTracker(purePursuitTracker);
    }

    /**
     * This function is called when the robot is disabled.
     */
    @Override
    public void disabledInit() {
        enabledLooper.stop();
        purePursuitLooper.stop();
        poseEstimator.resetPose();
        driveTrain.resetGyro();
        driveTrain.resetEncoders();
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
        driveTrain.resetGyro();
        poseEstimator.resetPose();
        purePursuitTracker.lastClosestPt = 0;
        purePursuitLooper.start();
    }

    /**
     * This function is called periodically during autonomous/sandstorm.
     */
    @Override
    public void autonomousPeriodic() {
        /*
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
        //driveTrain.getGyro().reset();
        enabledLooper.start();
        purePursuitLooper.stop();
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
        purePursuitLooper.start();
        System.out.println("pose: " + poseEstimator.getPose());
    }
}
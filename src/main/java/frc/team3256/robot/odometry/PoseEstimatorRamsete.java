package frc.team3256.robot.odometry;

import frc.team3256.robot.DriveTrain;
import frc.team3256.robot.math.*;
import frc.team3256.warriorlib.loop.Loop;

public class PoseEstimatorRamsete implements Loop {
    private Twist velocity;
    private RigidTransform pose;
    private RigidTransform prevPose;
    private double prevLeftDist = 0;
    private double prevRightDist = 0;
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private static PoseEstimatorRamsete instance;

    private PoseEstimatorRamsete() {
        reset();
    }

    public static PoseEstimatorRamsete getInstance() {
        return instance == null ? instance = new PoseEstimatorRamsete() : instance;
    }

//    public Vector getPose() {
//        return new Vector(pose.getTranslation().getX(), pose.getTranslation().getY());
//    }

    public double getPoseX() {
        return pose.getTranslation().getX();
    }

    public double getPoseY() {
        return pose.getTranslation().getY();
    }

    public double getThetaDegrees() {
        return pose.getRotation().degrees();
    }

    public double getTheta() {
        return pose.getRotation().radians();
    }

    public Twist getVelocity() {
        return velocity;
    }

    public void resetPosition() {
        velocity = new Twist();
        pose = new RigidTransform(new Translation(), pose.getRotation());
        prevPose = new RigidTransform(new Translation(), pose.getRotation());
        prevLeftDist = 0;
        prevRightDist = 0;
    }

    public void offsetPoseAngle(double angle) {
        pose = new RigidTransform(pose.getTranslation(), pose.getRotation().rotate(Rotation.fromDegrees(angle)));
        prevPose = new RigidTransform(pose.getTranslation(), pose.getRotation().rotate(Rotation.fromDegrees(angle)));
    }

    /**
     * MUST BE CALLED IMMEDIATELY AFTER RESETTING DRIVETRAIN/GYRO!
     */
    public void reset() {
        velocity = new Twist();
        pose = new RigidTransform();
        prevPose = new RigidTransform();
        prevLeftDist = 0;
        prevRightDist = 0;
    }

    @Override
    public void init(double timestamp) {
        reset();
    }

    @Override
    public void update(double timestamp) {
        double leftDist = driveTrain.getLeftDistance();
        double rightDist = driveTrain.getRightDistance();
        double deltaLeftDist = leftDist - prevLeftDist;
        double deltaRightDist = rightDist - prevRightDist;
//        System.out.println("deltaLeft: "+deltaLeftDist);
//        System.out.println("deltaRight: "+deltaRightDist);
        Rotation deltaHeading = prevPose.getRotation().inverse().rotate(driveTrain.getRotationAngleUpdated());
        //Use encoders + gyro to determine our velocity
        velocity = Kinematics.forwardKinematics(deltaLeftDist, deltaRightDist, deltaHeading.radians());
        //use velocity to determine our pose
        pose = Kinematics.integrateForwardKinematics(prevPose, velocity);
        //update for next iteration
        prevLeftDist = leftDist;
        prevRightDist = rightDist;
        prevPose = pose;
    }

    @Override
    public void end(double timestamp) {

    }
}

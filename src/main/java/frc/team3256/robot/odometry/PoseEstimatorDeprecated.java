package frc.team3256.robot.odometry;


import frc.team3256.robot.DriveTrain;
import frc.team3256.robot.math.*;
import frc.team3256.warriorlib.loop.Loop;

/**
    This class should implement Loop and be added to the loops in Robot.java in the robot code so that the pose updates
    at the same time interval as the rest of the subsystems.
 */

public class PoseEstimatorDeprecated implements Loop {
    private Twist velocity;
    private RigidTransform pose;
    private RigidTransform prevPose;
    private double prevLeftDist = 0;
    private double prevRightDist = 0;
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private static PoseEstimatorDeprecated instance;

    private PoseEstimatorDeprecated() {
        reset();
    }

    public static PoseEstimatorDeprecated getInstance() {
        return instance == null ? instance = new PoseEstimatorDeprecated() : instance;
    }

    public double getPoseX() {
        return pose.getTranslation().getX();
    }

    public double getPoseY() {
        return pose.getTranslation().getY();
    }

    public double getPoseTheta() {
        return pose.getRotation().degrees();
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

    /**

    The placeholder below should be replaced by this line:

        Rotation deltaHeading = prevPose.getRotation().inverse().rotate(driveTrain.getRotationAngle());

    As part of this implementation, add the following methods to the DriveTrain.java class in subsystems:

          import com.ctre.phoenix.sensors.PigeonIMU;
          private PigeonIMU gyro;
          private double gyroOffset = 0;

          //In the constructor add:

          gyro = new PigeonIMU(14);
          gyro.setAccumZAngle(0, 0);
          gyro.setYaw(0, 0);

          //In the main body, as these methods:

          public double getAngle() {
            double[] ypr = new double[3];
            gyro.getYawPitchRoll(ypr);
            return ypr[0] + gyroOffset;
          }

          public Rotation getRotationAngle() {
            return Rotation.fromDegrees(getAngle() + 90);
          }

     */
    @Override
    public void update(double timestamp) {
        double leftDist = driveTrain.getLeftDistance(); //driveTrain.getLeftDistance()
        double rightDist = driveTrain.getRightDistance(); //driveTrain.getRightDistance()
        double deltaLeftDist = leftDist - prevLeftDist;
        double deltaRightDist = rightDist - prevRightDist;
        //the creation of a new Rotation below is a place holder - refer to implementation above
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

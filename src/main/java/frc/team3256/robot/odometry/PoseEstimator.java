package frc.team3256.robot.odometry;


import frc.team3256.robot.math.Vector;
import frc.team3256.robot.operations.Logger;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.loop.Loop;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class PoseEstimator extends SubsystemBase {

    Vector currPose;
    double lastAvgDistance;
    DriveTrain driveTrain = DriveTrain.getInstance();
    static PoseEstimator instance;
    Logger logger = new Logger("PoseEstimator");

    private PoseEstimator(Vector v) {
        currPose = new Vector(v);
        lastAvgDistance = driveTrain.getAverageDistance();
    }

    public Vector getPose() {
        return currPose;
    }

    public static PoseEstimator getInstance(){
        return instance == null ? instance = new PoseEstimator(new Vector(0,0)) : instance;
    }

    public void resetPose() {
        lastAvgDistance = 0;
        currPose = new Vector(0,0);
    }

    @Override
    public void init(double timestamp) {
        logger.moveTo("poseEstimator");
    }

    @Override
    public void update(double timestamp) {
        double distanceChange = driveTrain.getAverageDistance() - lastAvgDistance;
        double heading = Math.toRadians(driveTrain.getAngle())+(Math.PI/2);
        double updatedX = distanceChange * Math.cos(heading);
        double updatedY = distanceChange * Math.sin(heading);
        currPose = Vector.add(currPose, new Vector(updatedX, updatedY));
        logger.log("pose", currPose.toString());
        lastAvgDistance = driveTrain.getAverageDistance();
    }

    @Override
    public void end(double timestamp) {

    }

    @Override
    public void outputToDashboard() {

    }

    @Override
    public void zeroSensors() {

    }
}

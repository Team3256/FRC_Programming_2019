package frc.team3256.robot.odometry;


import frc.team3256.robot.math.Vector;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.loop.Loop;

public class PoseEstimator implements Loop {

    Vector currPos;
    DriveTrain driveTrain = DriveTrain.getInstance();
    static PoseEstimator instance;

    public PoseEstimator(Vector v) {
        currPos = new Vector(v);
    }

    public Vector getPose() {
        return currPos;
    }

    public static PoseEstimator getInstance(){
        return instance == null ? instance = new PoseEstimator(new Vector(0,0)) : instance;
    }

    @Override
    public void init(double timestamp) {

    }

    @Override
    public void update(double timestamp) {
        double distance = driveTrain.getAverageDistance();
        double heading = driveTrain.getAngle();
        double updatedX = distance * Math.cos(heading);
        double updatedY = distance * Math.sin(heading);
        currPos = new Vector(updatedX, updatedY);
    }

    @Override
    public void end(double timestamp) {

    }
}

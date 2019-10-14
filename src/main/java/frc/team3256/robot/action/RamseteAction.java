package frc.team3256.robot.action;

import control.DrivePower;
import ramsete.RamseteTracker;
import odometry.PoseEstimator;
import path.PathGenerator;

public class RamseteAction implements Action {

    private RamseteTracker ramseteTracker = new RamseteTracker();
    private PoseEstimator poseEstimator = PoseEstimator.getInstance();
    private PathGenerator pathGenerator;
    private String pathCSV;
    private double b, g;
    //private DriveTrain driveTrain = DriveTrain.getInstance();

    public RamseteAction(String pathCSV, double b, double g) {
        pathGenerator = new PathGenerator();
        this.pathCSV = pathCSV;
        this.b = b;
        this.g = g;
    }

    @Override
    public boolean isFinished() {
        return ramseteTracker.isFinished();
    }

    @Override
    public void update() {
        DrivePower drivePower = ramseteTracker.update(poseEstimator.getPoseX(), poseEstimator.getPoseY(), poseEstimator.getPoseTheta(), b, g);
        //drive.setHighGear(drivePower.getHighGear);
        //drivetrain.setVelocityClosedLoop(drivePower.getLeft(), drivePower.getRight());
    }

    @Override
    public void done() {
        //driveTrain.setVelocityClosedLoop(0, 0);
        ramseteTracker.reset();
    }

    @Override
    public void start() {
        ramseteTracker.reset();
        ramseteTracker.setPath(pathGenerator.generatePath(pathCSV));
    }
}

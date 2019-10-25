package frc.team3256.robot.action;

import frc.team3256.robot.DriveTrain;
import frc.team3256.robot.control.DrivePower;
import frc.team3256.robot.odometry.PoseEstimatorRamsete;
import frc.team3256.robot.path.PathGenerator;
import frc.team3256.robot.ramsete.RamseteTracker;
import frc.team3256.warriorlib.auto.action.Action;

public class RamseteAction implements Action {

    private RamseteTracker ramseteTracker = new RamseteTracker();
    private PoseEstimatorRamsete poseEstimator = PoseEstimatorRamsete.getInstance();
    private PathGenerator pathGenerator;
    private String pathCSV;
    private double b, g;
    private DriveTrain driveTrain = DriveTrain.getInstance();

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
        DrivePower drivePower = ramseteTracker.update(poseEstimator.getPoseX(), poseEstimator.getPoseY(), poseEstimator.getTheta(), b, g);
        driveTrain.setHighGear(drivePower.getHighGear());
        System.out.println("left target vel: " + drivePower.getLeft());
        System.out.println("right target vel: " + drivePower.getRight());
        //driveTrain.setVelocityClosedLoop(drivePower.getLeft() , drivePower.getRight());
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

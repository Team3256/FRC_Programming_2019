package frc.team3256.robot.path;

import frc.team3256.robot.odometry.PoseEstimator;
import frc.team3256.robot.operations.DrivePower;
import frc.team3256.robot.path.PurePursuitTracker;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.loop.Loop;

public class PurePursuitLoop implements Loop {

    DrivePower drivePower = new DrivePower(0,0, true);
    DriveTrain driveTrain = DriveTrain.getInstance();
    PurePursuitTracker purePursuitTracker;
    PoseEstimator poseEstimator = PoseEstimator.getInstance();

    public void initPurePursuitTracker(PurePursuitTracker tracker) {
        purePursuitTracker = tracker;
    }

    @Override
    public void init(double timestamp) {

    }

    @Override
    public void update(double timestamp) {
        drivePower = purePursuitTracker.update(poseEstimator.getPose(), driveTrain.getVelocity(), Math.toRadians(driveTrain.getAngle()) + (Math.PI/2) );
        driveTrain.setVelocityClosedLoop(drivePower.getLeft(), drivePower.getRight());
    }

    @Override
    public void end(double timestamp) {

    }
}

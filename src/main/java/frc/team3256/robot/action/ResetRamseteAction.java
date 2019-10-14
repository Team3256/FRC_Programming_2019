package frc.team3256.robot.action;

import odometry.PoseEstimator;

/**
 * Resets drive train and pose estimator
 */
public class ResetRamseteAction extends RunOnceAction {
    @Override
    public void runOnce() {
        //DriveTrain.getInstance().resetEncoders();
        //DriveTrain.getInstance().resetGyro();
        PoseEstimator.getInstance().reset();
    }
}

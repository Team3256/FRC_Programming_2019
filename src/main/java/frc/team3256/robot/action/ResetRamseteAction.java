package frc.team3256.robot.action;

import frc.team3256.robot.DriveTrain;
import frc.team3256.robot.odometry.PoseEstimator;
import frc.team3256.warriorlib.auto.action.RunOnceAction;

/**
 * Resets drive train and pose estimator
 */
public class ResetRamseteAction extends RunOnceAction {
    @Override
    public void runOnce() {
        DriveTrain.getInstance().resetEncoders();
        DriveTrain.getInstance().resetGyro();
        PoseEstimator.getInstance().reset();
    }
}
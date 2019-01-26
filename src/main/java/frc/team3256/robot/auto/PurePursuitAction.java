package frc.team3256.robot.auto;

import frc.team3256.robot.path.PurePursuitTracker;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.action.Action;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.control.DrivePower;

public class PurePursuitAction implements Action {
	private PurePursuitTracker purePursuitTracker;
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private PoseEstimator poseEstimator = PoseEstimator.getInstance(driveTrain);

	public PurePursuitAction(PurePursuitTracker purePursuitTracker) {
		this.purePursuitTracker = purePursuitTracker;
	}

	@Override
	public boolean isFinished() {
		return purePursuitTracker.isOnLastSegment();
	}

	@Override
	public void update() {
		DrivePower drivePower = purePursuitTracker.update(poseEstimator.getPose(), driveTrain.getVelocity(), Math.toRadians(driveTrain.getAngle()) + (Math.PI / 2));
		driveTrain.setVelocityClosedLoop(drivePower.getLeft(), drivePower.getRight());
	}

	@Override
	public void done() {
		driveTrain.setVelocityClosedLoop(0, 0);
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
	}

	@Override
	public void start() {
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
	}
}

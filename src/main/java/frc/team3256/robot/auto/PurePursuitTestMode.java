package frc.team3256.robot.auto;

import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitAction;
import frc.team3256.warriorlib.auto.purepursuit.ResetPursuitAction;

public class PurePursuitTestMode extends AutoModeBase {
	@Override
	protected void routine() throws AutoModeEndedException {
		System.out.println("STARTED PURE PURSUIT");
		runAction(new ResetPursuitAction());
		runAction(new PurePursuitAction());
		System.out.println("FINISHED PURE PURSUIT");
		System.out.println(PoseEstimator.getInstance().getPose());
	}
}

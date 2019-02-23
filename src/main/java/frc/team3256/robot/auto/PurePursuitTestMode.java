package frc.team3256.robot.auto;

import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.WaitAction;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitAction;
import frc.team3256.warriorlib.auto.purepursuit.ResetPursuitAction;

public class PurePursuitTestMode extends AutoModeBase {
	@Override
	protected void routine() throws AutoModeEndedException {
		System.out.println("STARTED PURE PURSUIT");
		HatchPivot.getInstance().setPositionDeploy();
		runAction(new WaitAction(1.7));
		runAction(new ResetPursuitAction());
		DriveTrain.getInstance().setHighGear(true);
		runAction(new PurePursuitAction(0));
		System.out.println("FINISHED PURE PURSUIT");
		System.out.println(PoseEstimator.getInstance().getPose());
	}
}
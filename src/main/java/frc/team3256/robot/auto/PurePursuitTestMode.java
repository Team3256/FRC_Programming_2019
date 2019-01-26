package frc.team3256.robot.auto;

import frc.team3256.robot.path.PurePursuitTracker;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;

public class PurePursuitTestMode extends AutoModeBase {
	private PurePursuitTracker purePursuitTracker;

	public PurePursuitTestMode(PurePursuitTracker purePursuitTracker) {
		this.purePursuitTracker = purePursuitTracker;
	}

	@Override
	protected void routine() throws AutoModeEndedException {
		System.out.println("STARTED PURE PURSUIT");
		runAction(new PurePursuitAction(purePursuitTracker));
		System.out.println("FINISHED PURE PURSUIT");
	}
}

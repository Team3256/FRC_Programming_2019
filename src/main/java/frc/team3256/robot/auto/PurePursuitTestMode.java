package frc.team3256.robot.auto;

import frc.team3256.robot.auto.action.TurnInPlaceAction;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.WaitAction;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitAction;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitTracker;
import frc.team3256.warriorlib.auto.purepursuit.ResetPursuitAction;

import java.util.Collections;

import static frc.team3256.robot.auto.Paths.getCenterRightDoubleCargoHatch;

public class PurePursuitTestMode extends AutoModeBase {
	@Override
	protected void routine() throws AutoModeEndedException {

		PurePursuitTracker purePursuitTracker = PurePursuitTracker.getInstance();
		purePursuitTracker.setRobotTrack(DriveTrainConstants.robotTrack);
		purePursuitTracker.setPaths(getCenterRightDoubleCargoHatch(), DriveTrainConstants.lookaheadDistance);

		System.out.println("STARTED PURE PURSUIT");
		HatchPivot.getInstance().setPositionDeploy();
		runAction(new WaitAction(1.0));
		runAction(new ResetPursuitAction());
		DriveTrain.getInstance().setHighGear(true);
		runAction(new PurePursuitAction(0));
		System.out.println("FINISHED PURE PURSUIT");
		HatchPivot.getInstance().deployHatch();
		runAction(new WaitAction(0.75));
		HatchPivot.getInstance().retractHatch();
		runAction(new WaitAction(0.50));
		runAction(new PurePursuitAction(1));
		runAction(new WaitAction(2.5));
		runAction(new TurnInPlaceAction(-90));
		System.out.println(PoseEstimator.getInstance().getPose());
	}
}
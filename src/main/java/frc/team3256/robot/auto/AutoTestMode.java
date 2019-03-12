package frc.team3256.robot.auto;

import frc.team3256.robot.auto.action.TurnInPlaceAction;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.WaitAction;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitAction;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitTracker;

import static frc.team3256.robot.auto.Paths.getBackwardsAutoPath;

public class AutoTestMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {

        PurePursuitTracker purePursuitTracker = PurePursuitTracker.getInstance();
        purePursuitTracker.setRobotTrack(DriveTrainConstants.robotTrack);
        purePursuitTracker.setPaths(getBackwardsAutoPath(), DriveTrainConstants.lookaheadDistance);

        //runAction(new ZeroElevatorAction());
        //runAction(new ParallelAction(Arrays.asList(new ZeroElevatorAction(), new ZeroHatchAction())));
        //HatchPivot.getInstance().setPositionDeploy();
//        runAction(new WaitAction(1.0))
        //runAction(new TurnInPlaceAction(90));

        runAction(new PurePursuitAction(0));
        runAction(new WaitAction(2));
        runAction(new PurePursuitAction(1));


    }
}

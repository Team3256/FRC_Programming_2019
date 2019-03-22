package frc.team3256.robot.auto;

import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.WaitAction;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitAction;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitTracker;
import frc.team3256.warriorlib.auto.purepursuit.ResetPursuitAction;

import static frc.team3256.robot.auto.Paths.getBaselineAutoPath;

public class BaselineAutoMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        PurePursuitTracker purePursuitTracker = PurePursuitTracker.getInstance();
        purePursuitTracker.setRobotTrack(DriveTrainConstants.robotTrack);
        purePursuitTracker.setPaths(getBaselineAutoPath(), DriveTrainConstants.lookaheadDistance);

        //HatchPivot.getInstance().setPositionDeploy();
        runAction(new WaitAction(0.5));
        runAction(new ResetPursuitAction());
        DriveTrain.getInstance().setHighGear(true);
        runAction(new PurePursuitAction(0));

        //runAction(new ZeroElevatorAction());
    }
}

package frc.team3256.robot.auto;

import frc.team3256.robot.auto.action.TurnInPlaceAction;
import frc.team3256.robot.auto.action.ZeroElevatorAction;
import frc.team3256.robot.auto.action.ZeroHatchAction;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.ParallelAction;
import frc.team3256.warriorlib.auto.action.WaitAction;

import java.util.Arrays;

public class AutoTestMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new ZeroElevatorAction());
        //runAction(new ParallelAction(Arrays.asList(new ZeroElevatorAction(), new ZeroHatchAction())));
//        HatchPivot.getInstance().setPositionDeploy();
//        runAction(new WaitAction(1.0));
//        runAction(new TurnInPlaceAction(90));
    }
}

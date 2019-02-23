package frc.team3256.robot.auto;

import frc.team3256.robot.auto.action.ZeroElevatorAction;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.ParallelAction;

import java.util.Arrays;

public class AutoTestMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new ParallelAction(Arrays.asList(new ZeroElevatorAction())));
    }
}

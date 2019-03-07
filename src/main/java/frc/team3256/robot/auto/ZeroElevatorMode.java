package frc.team3256.robot.auto;

import frc.team3256.robot.auto.action.ZeroElevatorAction;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;

public class ZeroElevatorMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new ZeroElevatorAction());
    }
}

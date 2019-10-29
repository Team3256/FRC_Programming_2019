package frc.team3256.robot.action;

import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.robot.action.*;

public class RamseteTestAutoMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new ResetRamseteAction());
        runAction(new RamseteAction("/home/lvuser/path120.pf1.csv", 2.0, .7));
    }
}

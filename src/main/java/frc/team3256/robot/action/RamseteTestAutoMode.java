package frc.team3256.robot.action;

import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.robot.action.*;

public class RamseteTestAutoMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new ResetRamseteAction());
        //b is like proportional gain...g is like dampening gain
        runAction(new RamseteAction("/home/lvuser/RightSpline.pf1.csv", 2.2, .55));
    }
}

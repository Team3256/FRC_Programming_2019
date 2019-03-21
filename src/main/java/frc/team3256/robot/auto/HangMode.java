package frc.team3256.robot.auto;

import frc.team3256.robot.auto.action.HangActuatorAction;
import frc.team3256.robot.auto.action.HangElevatorAction;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.Action;
import frc.team3256.warriorlib.auto.action.ParallelAction;

import java.util.ArrayList;
import java.util.List;

public class HangMode extends AutoModeBase {



    @Override
    protected void routine() throws AutoModeEndedException {
        List<Action> actions = new ArrayList<>();
        actions.add(new HangElevatorAction());
        actions.add(new HangActuatorAction());
        runAction(new ParallelAction(actions));
    }
}


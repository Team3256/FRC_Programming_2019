package frc.team3256.robot.auto.action;

import frc.team3256.robot.subsystems.Hanger;
import frc.team3256.warriorlib.auto.action.Action;

public class HangActuatorAction implements Action {

    private Hanger hanger = Hanger.getInstance();

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {

    }

    @Override
    public void start() {
        hanger.setWantedState(Hanger.WantedState.WANTS_TO_HANG);
    }
}

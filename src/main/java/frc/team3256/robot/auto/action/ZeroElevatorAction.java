package frc.team3256.robot.auto.action;

import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.warriorlib.auto.action.Action;

public class ZeroElevatorAction implements Action {
    private Elevator elevator = Elevator.getInstance();

    @Override
    public boolean isFinished() {
        return elevator.getHallEffectTriggered();
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {
        elevator.setOpenLoop(0);
        elevator.resetEncoder();
    }

    @Override
    public void start() {
        elevator.setOpenLoop(-0.1);
    }
}

package frc.team3256.robot.auto.action;

import frc.team3256.robot.subsystems.elevator.Elevator;
import frc.team3256.warriorlib.auto.action.Action;

public class ZeroElevatorAction implements Action {
    private Elevator elevator = Elevator.getInstance();

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void update() {
        //if (elevator.getVelocity() < 1)
    }

    @Override
    public void done() {

    }

    @Override
    public void start() {
        elevator.setOpenLoop(-0.25);
    }
}

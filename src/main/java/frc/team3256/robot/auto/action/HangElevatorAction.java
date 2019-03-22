package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.Timer;
import frc.team3256.warriorlib.auto.action.Action;

public class HangElevatorAction implements Action {
    //private Elevator elevator = Elevator.getInstance();
    private double timestamp;

    @Override
    public boolean isFinished() {
        //return elevator.getHallEffectTriggered() || Timer.getFPGATimestamp() - timestamp > 5;
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {
        //elevator.setOpenLoop(0);
        //elevator.resetEncoder();
    }

    @Override
    public void start() {
        //elevator.setOpenLoop(-0.15);
        timestamp = Timer.getFPGATimestamp();
    }
}

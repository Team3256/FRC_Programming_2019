package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.Timer;
import frc.team3256.warriorlib.auto.action.Action;

public class ZeroElevatorAction implements Action {
    //private Elevator elevator = Elevator.getInstance();
    private double timestamp;

    @Override
    public boolean isFinished() {
        return true;
        //return elevator.getHallEffectTriggered() || Timer.getFPGATimestamp() - timestamp > 5;
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
        //elevator.setOpenLoop(-0.3);
        timestamp = Timer.getFPGATimestamp();
    }
}

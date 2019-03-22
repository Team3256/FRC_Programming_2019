package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.Timer;
import frc.team3256.warriorlib.auto.action.Action;

public class ZeroHatchAction implements Action {
    //private HatchPivot hatchPivot = HatchPivot.getInstance();
    private double startTime = 0;

    @Override
    public boolean isFinished() {
        return true;
        //return hatchPivot.getCurrent() < 0.1;
    }

    @Override
    public void update() {
        if (Timer.getFPGATimestamp() - startTime >= 0.5) {
            //hatchPivot.setHatchPivotPower(0);
        }
    }

    @Override
    public void done() {
        //hatchPivot.zeroSensors();
        //hatchPivot.setBrake();
    }

    @Override
    public void start() {
        //hatchPivot.setCoast();
        //hatchPivot.setHatchPivotPower(0.4);
        startTime = Timer.getFPGATimestamp();
    }
}

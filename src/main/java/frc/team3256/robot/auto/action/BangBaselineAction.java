package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.Timer;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.action.Action;

public class BangBaselineAction implements Action {
    private double timestamp = Timer.getFPGATimestamp();

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - timestamp > 3.0;
    }

    @Override
    public void update() {
        System.out.println(Timer.getFPGATimestamp() - timestamp);
    }

    @Override
    public void done() {
        DriveTrain.getInstance().setOpenLoop(0, 0);
    }

    @Override
    public void start() {
        timestamp = Timer.getFPGATimestamp();
        DriveTrain.getInstance().setOpenLoop(0.3, 0.3);
    }
}

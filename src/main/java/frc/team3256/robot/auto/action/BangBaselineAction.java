package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.Timer;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.action.Action;

public class BangBaselineAction implements Action {
    private double timestamp = Timer.getFPGATimestamp();

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - timestamp > 0.7;
    }

    @Override
    public void update() {
        System.out.println(Timer.getFPGATimestamp() - timestamp);
    }

    @Override
    public void done() {
        DriveTrain.getInstance().setPowerClosedLoop(0, 0, true);
    }

    @Override
    public void start() {
        timestamp = Timer.getFPGATimestamp();
        DriveTrain.getInstance().setPowerClosedLoop(0.1, 0.1, true);
    }
}

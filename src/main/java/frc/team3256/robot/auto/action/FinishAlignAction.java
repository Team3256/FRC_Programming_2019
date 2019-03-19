package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.action.Action;

public class FinishAlignAction implements Action {
    private int cameraId;
    private double timestamp = Timer.getFPGATimestamp();
    private DriveTrain driveTrain = DriveTrain.getInstance();

    public FinishAlignAction(int cameraId) {
        this.cameraId = cameraId;
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - timestamp > 0.7;
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {
        driveTrain.setPowerClosedLoop(0, 0, true);
    }

    @Override
    public void start() {
        timestamp = Timer.getFPGATimestamp();
        double angle = SmartDashboard.getNumber("visionAngle" + cameraId, 0);
        if (angle > 0)
            driveTrain.setPowerClosedLoop(0.12, 0.08, true);
        else
            driveTrain.setPowerClosedLoop(0.08, 0.12, true);
    }
}

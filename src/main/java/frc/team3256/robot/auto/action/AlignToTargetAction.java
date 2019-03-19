package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.action.Action;

public class AlignToTargetAction implements Action {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private int cameraId;

    public AlignToTargetAction(int cameraId) {
        this.cameraId = cameraId;
    }

    @Override
    public boolean isFinished() {
        return SmartDashboard.getNumber("visionDistance" + cameraId, 0) <= 25;
    }

    @Override
    public void update() {
        double distance = SmartDashboard.getNumber("visionDistance" + cameraId, 0);
        double angle = SmartDashboard.getNumber("visionAngle" + cameraId, 0);

        double leftPower;
        double rightPower;

        if (distance < 40) {
            leftPower = 0.1 + angle / 400;
            rightPower = 0.1 - angle / 400;
        } else {
            leftPower = 0.2 + angle / 400;
            rightPower = 0.2 - angle / 400;
        }

        driveTrain.setPowerClosedLoop(leftPower, rightPower, true);
    }

    @Override
    public void done() {

    }

    @Override
    public void start() {

    }
}

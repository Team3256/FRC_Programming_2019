package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.robot.operations.PIDController;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.action.Action;

public class TurnInPlaceAction implements Action {
    private double targetAngle;
    private PIDController pidController;
    private DriveTrain driveTrain = DriveTrain.getInstance();

    /**
     * Turning right = negative angle lul
     */
    public TurnInPlaceAction(double angle) {
        this.targetAngle = driveTrain.getAngle() + angle;
        pidController = new PIDController(DriveTrainConstants.kTurnP, DriveTrainConstants.kTurnI, DriveTrainConstants.kTurnD);
    }

    @Override
    public boolean isFinished() {
        //return false;
        return Math.abs(targetAngle - driveTrain.getAngle()) <= 1.5;
    }

    @Override
    public void update() {
        double output = pidController.calculatePID(targetAngle, DriveTrain.getInstance().getAngle());
        driveTrain.setOpenLoop(-output, output);
        SmartDashboard.putNumber("output", output);
    }

    @Override
    public void done() {
        driveTrain.setOpenLoop(0, 0);
    }

    @Override
    public void start() {

    }
}

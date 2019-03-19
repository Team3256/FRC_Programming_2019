package frc.team3256.robot.auto.action;

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
        this(angle, DriveTrainConstants.kTurnP, DriveTrainConstants.kTurnI, DriveTrainConstants.kTurnD);
    }

    public TurnInPlaceAction(double angle, double kTurnP, double kTurnI, double kTurnD) {
        this.targetAngle = driveTrain.getAngle() + angle;
        pidController = new PIDController(kTurnP, kTurnI, kTurnD);

    }

    @Override
    public boolean isFinished() {
        //return false;
        System.out.println("Turn In Place FINISHED");
        return Math.abs(targetAngle - driveTrain.getAngle()) <= 1.5;
    }

    @Override
    public void update() {
        double output = pidController.calculatePID(targetAngle, DriveTrain.getInstance().getAngle());
        driveTrain.setPowerClosedLoop(-output, output, true);
        //SmartDashboard.putNumber("Turn In Place Output", output);
    }

    @Override
    public void done() {
        driveTrain.setPowerClosedLoop(0, 0, true);
    }

    @Override
    public void start() {

    }
}

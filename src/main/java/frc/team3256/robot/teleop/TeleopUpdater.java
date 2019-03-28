package frc.team3256.robot.teleop;

import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.robot.subsystems.NewElevator;
import frc.team3256.robot.subsystems.NewPivot;
import frc.team3256.robot.teleop.control.*;
import frc.team3256.warriorlib.control.DrivePower;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private IDriverController driverController = XboxDriverController.getInstance();
    private IManipulatorController manipulatorController = XboxManipulatorController.getInstance();
    private NewElevator mElevator = NewElevator.getInstance();
    private NewPivot mPivot = NewPivot.getInstance();
    private boolean prevIntakeHatch = false;
    private boolean prevOuttakeHatch = false;

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private boolean isManualControl = false;

    public void handleDrive() {
        driveTrain.setBrakeMode();
        DrivePower drivePower = DriveTrain.getInstance().betterCurvatureDrive(
                driverController.getThrottle() > 0.15 ? driverController.getThrottle() : 0.0,
                driverController.getTurn()*(driverController.getHighGear() ? 0.6 : 1.0),
                driverController.getQuickTurn(),
                driverController.getHighGear()
        );
        driveTrain.setHighGear(drivePower.getHighGear());
        driveTrain.setPowerOpenLoop(drivePower.getLeft(), drivePower.getRight());
    }

    public void update() {
        handleDrive();

        double elevatorThrottle = Math.abs(manipulatorController.getElevatorThrottle()) > 0.15 ? manipulatorController.getElevatorThrottle() : 0.0;
        double pivotThrottle = Math.abs(manipulatorController.getPivotThrottle()) > 0.15 ? manipulatorController.getPivotThrottle() : 0.0;

        boolean goToHighRocket = manipulatorController.goToHigh();
        boolean goToMidRocket = manipulatorController.goToMid();
        boolean goToLowRocket = manipulatorController.goToLow();
        boolean goToHome = manipulatorController.goToHome();
        boolean intakeHatch = manipulatorController.shouldHatchIntake();
        boolean outtakeHatch = manipulatorController.shouldHatchOuttake();

        if (elevatorThrottle > 0) {
            isManualControl = true;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MANUAL_UP);
        } else if (elevatorThrottle < 0) {
            isManualControl = true;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MANUAL_DOWN);
        } else if (goToHighRocket && mElevator.isHomed()) {
            isManualControl = false;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HIGH_CARGO);
        } else if (goToMidRocket && mElevator.isHomed()) {
            isManualControl = false;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MID_CARGO);
        } else if (goToLowRocket && mElevator.isHomed()) {
            isManualControl = false;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_LOW_CARGO);
        } else {
            if (isManualControl) {
                mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HOLD);
            } else if (mElevator.atClosedLoopTarget()) {
                mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HOLD);
            }

        }

        boolean shouldCargoIntake = manipulatorController.shouldCargoIntake();

        if (pivotThrottle > 0) {
            System.out.println("Up");
            isManualControl = true;
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_MANUAL_UP);
        } else if (pivotThrottle < 0) {
            isManualControl = true;
            System.out.println("Down");
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_MANUAL_DOWN);
        } else if (shouldCargoIntake) {
            isManualControl = false;
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_INTAKE_POS);
        } else {
            if (isManualControl) {
                mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_HOLD);
            } else {
                mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_POS);
            }
        }

        if (intakeHatch) {
            if (prevIntakeHatch) {
                mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_SECURE_HATCH);
                prevOuttakeHatch = false;
            }
            else {
                mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_HATCH);
                prevOuttakeHatch = true;
            }
        }
        else if (outtakeHatch) {
            if (prevOuttakeHatch) {
                mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_UNSECURE_HATCH);
                prevOuttakeHatch = false;
            }
            else {
                mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_HATCH);
                prevOuttakeHatch = true;
            }
        }
    }
}

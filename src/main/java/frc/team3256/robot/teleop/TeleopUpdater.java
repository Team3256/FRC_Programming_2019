package frc.team3256.robot.teleop;

import frc.team3256.robot.subsystems.*;
import frc.team3256.robot.teleop.control.*;
import frc.team3256.warriorlib.control.DrivePower;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private IDriverController driverController = XboxDriverController.getInstance();
    private IManipulatorController manipulatorController = XboxManipulatorController.getInstance();
    private NewElevator mElevator = NewElevator.getInstance();
    private NewPivot mPivot = NewPivot.getInstance();
    private NewCargoIntake mCargoIntake = NewCargoIntake.getInstance();
    private NewHanger mHanger = NewHanger.getInstance();

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private boolean isElevatorManualControl = false;
    private boolean isPivotManualControl = false;
    private boolean isClimbingMode = false;

    public void handleDrive() {
        driveTrain.setBrakeMode();
        DrivePower drivePower = DriveTrain.getInstance().betterCurvatureDrive(
                Math.abs(driverController.getThrottle()) > 0.15 ? driverController.getThrottle() : 0.0,
                driverController.getTurn()*(driverController.getHighGear() ? 0.6 : 1.0),
                driverController.getQuickTurn(),
                driverController.getHighGear()
        );
        driveTrain.setHighGear(drivePower.getHighGear());
        driveTrain.setPowerOpenLoop(drivePower.getLeft(), drivePower.getRight());
    }

    public void update() {
        handleDrive();

        if (manipulatorController.getShouldToggleClimb()) {
            isClimbingMode = !isClimbingMode;
            if (isClimbingMode) {
                mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_HANG);
                mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HANG);
            }
        }

        if (isClimbingMode) {
            return;
        }

        double elevatorThrottle = Math.abs(manipulatorController.getElevatorThrottle()) > 0.15 ? manipulatorController.getElevatorThrottle() : 0.0;
        double pivotThrottle = Math.abs(manipulatorController.getPivotThrottle()) > 0.15 ? manipulatorController.getPivotThrottle() : 0.0;

        boolean goToHighRocket = manipulatorController.goToHigh();
        boolean goToMidRocket = manipulatorController.goToMid();
        boolean goToLowRocket = manipulatorController.goToLow();
        boolean goToHome = manipulatorController.goToHome();
        boolean startIntakeHatch = manipulatorController.shouldHatchStartIntake();
        boolean finishIntakeHatch = manipulatorController.shouldHatchFinishIntake();
        boolean startOuttakeHatch = manipulatorController.shouldHatchStartOuttake();
        boolean finishOuttakeHatch = manipulatorController.shouldHatchFinishOuttake();

        if (elevatorThrottle > 0) {
            isElevatorManualControl = true;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MANUAL_UP);
        } else if (elevatorThrottle < 0) {
            isElevatorManualControl = true;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MANUAL_DOWN);
        } else if (goToHighRocket && mElevator.isHomed()) {
            isElevatorManualControl = false;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HIGH_CARGO);
        } else if (goToMidRocket && mElevator.isHomed()) {
            isElevatorManualControl = false;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MID_CARGO);
        } else if (goToLowRocket && mElevator.isHomed()) {
            isElevatorManualControl = false;
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_LOW_CARGO);
        } else {
            if (isElevatorManualControl) {
                mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HOLD);
            } else if (mElevator.atClosedLoopTarget()) {
                mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HOLD);
            }
        }

        boolean shouldCargoIntake = manipulatorController.shouldCargoIntake();
        boolean shouldCargoOuttake = manipulatorController.shouldCargoOuttake();

        if (shouldCargoIntake) {
            System.out.println("cargo intake time");
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_INTAKE_POS);
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_INTAKE);
            isPivotManualControl = false;
        } else if (shouldCargoOuttake) {
            System.out.println("cargo intake time");
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_EXHAUST);
            isPivotManualControl = false;
        } else if (pivotThrottle > 0) {
            System.out.println("Up");
            isPivotManualControl = true;
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_MANUAL_UP);
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_STOP);
        } else if (pivotThrottle < 0) {
            isPivotManualControl = true;
            System.out.println("Down");
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_MANUAL_DOWN);
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_STOP);
        } else if (isPivotManualControl) {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_HOLD);
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_STOP);
        }
        else if (mPivot.atClosedLoopTarget()) {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_HOLD);
        } else if (startIntakeHatch) {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_HATCH);
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_START_INTAKE_HATCH);
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_STOP);
            isElevatorManualControl = false;
            isPivotManualControl = false;
        }
        else if (startOuttakeHatch) {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_HATCH);
            isElevatorManualControl = false;
            isPivotManualControl = false;
        } else if (finishIntakeHatch) {
            System.out.println("WANTS TO FINISH");
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_FINISH_INTAKE_HATCH);
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_ELEVATOR_WAIT);
            isElevatorManualControl = false;
            isPivotManualControl = false;
        } else if (finishOuttakeHatch) {
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_FINISH_OUTTAKE_HATCH);
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_ELEVATOR_WAIT);
            isElevatorManualControl = false;
            isPivotManualControl = false;
        } else {
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_STOP);
        }
    }
}

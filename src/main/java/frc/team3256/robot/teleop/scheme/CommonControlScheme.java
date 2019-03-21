package frc.team3256.robot.teleop.scheme;

import frc.team3256.robot.subsystems.NewCargoIntake;
import frc.team3256.robot.subsystems.NewElevator;
import frc.team3256.robot.subsystems.NewPivot;
import frc.team3256.warriorlib.control.XboxListenerBase;

public abstract class CommonControlScheme extends XboxListenerBase {
    protected NewElevator mElevator = NewElevator.getInstance();
    protected NewPivot mPivot = NewPivot.getInstance();
    protected NewCargoIntake mCargoIntake = NewCargoIntake.getInstance();

    private boolean rightTriggerWasPressed = false;

    // Move elevator
    @Override
    public void onLeftJoystick(double x, double y) {
        if (y > 0.25) {
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MANUAL_UP);
        } else if (y < -0.25){
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MANUAL_DOWN);
        } else {
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HOLD);
        }
    }

    // Move pivot (inverted)
    @Override
    public void onRightJoystick(double x, double y) {
        if (y > 0.25) {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_MANUAL_UP);
        } else if (y < -0.25){
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_MANUAL_DOWN);
        } else {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_HOLD);
        }
    }

    // Exhaust Cargo on hold
    @Override
    public void onLeftTrigger(double value) {
        if (value > 0.25)
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_EXHAUST);
        else mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_STOP);
    }

    // Intake Cargo on hold
    @Override
    public void onRightTrigger(double value) {
        System.out.println("Right trigger");
        if (value > 0.25 && !rightTriggerWasPressed) {
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_INTAKE);
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_INTAKE_POS);
            System.out.println("bruh moment");
            rightTriggerWasPressed = true;
        } else {
            mCargoIntake.setWantedState(NewCargoIntake.WantedState.WANTS_TO_STOP);
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_POS);
            rightTriggerWasPressed = false;
        }
    }

    @Override
    public void onLeftShoulderPressed() {
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_START_OUTTAKE_HATCH);
        mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_HATCH);
    }

    // Set position to unhook, then retract
    @Override
    public void onLeftShoulderReleased() {
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_FINISH_OUTTAKE_HATCH);
        mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_RETRACT_HATCH);
    }

    @Override
    public void onRightShoulderPressed() {
        mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_HATCH);
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_START_INTAKE_HATCH);
    }

    // Set the position to hook, then retract
    @Override
    public void onRightShoulderReleased() {
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_FINISH_INTAKE_HATCH);
        mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_ELEVATOR_WAIT);
    }

    @Override
    public void onSelectedPressed() {
        // Hang
    }
}
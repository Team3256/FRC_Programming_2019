package frc.team3256.robot.teleop.scheme;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.Hanger;
import frc.team3256.robot.subsystems.Pivot;
import frc.team3256.warriorlib.control.XboxListenerBase;

public abstract class CommonControlScheme extends XboxListenerBase {
    protected Elevator mElevator = Elevator.getInstance();
    protected Pivot mPivot = Pivot.getInstance();
    protected CargoIntake mCargoIntake = CargoIntake.getInstance();
    protected Hanger mHanger = Hanger.getInstance();

    private boolean rightTriggerWasPressed = false;

    // Move elevator
    @Override
    public void onLeftJoystick(double x, double y) {
        if (y > 0.25) {
            mElevator.setWantedState(Elevator.WantedState.WANTS_TO_MANUAL_UP);
        } else if (y < -0.25){
            mElevator.setWantedState(Elevator.WantedState.WANTS_TO_MANUAL_DOWN);
        } else {
            mElevator.setWantedState(Elevator.WantedState.WANTS_TO_HOLD);
        }
    }

    // Move pivot (inverted)
    @Override
    public void onRightJoystick(double x, double y) {
        if (y > 0.25) {
            mPivot.setWantedState(Pivot.WantedState.WANTS_TO_MANUAL_UP);
        } else if (y < -0.25){
            mPivot.setWantedState(Pivot.WantedState.WANTS_TO_MANUAL_DOWN);
        } else {
            mPivot.setWantedState(Pivot.WantedState.WANTS_TO_HOLD);
        }
    }

    // Exhaust Cargo on hold
    @Override
    public void onLeftTrigger(double value) {
        if (value > 0.25)
            mCargoIntake.setWantedState(CargoIntake.WantedState.WANTS_TO_EXHAUST);
        else mCargoIntake.setWantedState(CargoIntake.WantedState.WANTS_TO_STOP);
    }

    // Intake Cargo on hold
    @Override
    public void onRightTrigger(double value) {
        System.out.println("Right trigger");
        if (value > 0.25 && !rightTriggerWasPressed) {
            mCargoIntake.setWantedState(CargoIntake.WantedState.WANTS_TO_INTAKE);
            rightTriggerWasPressed = true;
        } else {
            mCargoIntake.setWantedState(CargoIntake.WantedState.WANTS_TO_STOP);
            mPivot.setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_POS);
            rightTriggerWasPressed = false;
        }
    }

    @Override
    public void onLeftShoulderPressed() {
        mElevator.setWantedState(Elevator.WantedState.WANTS_TO_START_OUTTAKE_HATCH);
        mPivot.setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_HATCH);
    }

    // Set position to unhook, then retract
    @Override
    public void onLeftShoulderReleased() {
        mElevator.setWantedState(Elevator.WantedState.WANTS_TO_FINISH_OUTTAKE_HATCH);
        mPivot.setWantedState(Pivot.WantedState.WANTS_TO_RETRACT_HATCH);
    }

    @Override
    public void onRightShoulderPressed() {
        mPivot.setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_HATCH);
        mElevator.setWantedState(Elevator.WantedState.WANTS_TO_START_INTAKE_HATCH);
    }

    // Set the position to hook, then retract
    @Override
    public void onRightShoulderReleased() {
        mElevator.setWantedState(Elevator.WantedState.WANTS_TO_FINISH_INTAKE_HATCH);
        mPivot.setWantedState(Pivot.WantedState.WANTS_TO_ELEVATOR_WAIT);
    }

    @Override
    public void onSelectedPressed() {
        if (mHanger.getHangerState() == Hanger.HangerState.HANGING) {
            mHanger.setWantedState(Hanger.WantedState.WANTS_TO_RETRACT);
        } else {
            mHanger.setWantedState(Hanger.WantedState.WANTS_TO_HANG);
        }
    }

    @Override
    public void onUpDpadPressed() {
        mPivot.zeroSensors();
    }

    @Override
    public void onDownDpadPressed() {
        mPivot.setWantedState(Pivot.WantedState.WANTS_TO_HANG);
    }

    @Override
    public void onRightDpadPressed() {
        mPivot.engageBrake();
    }

    @Override
    public void onLeftDpadPressed() {
        mPivot.releaseBrake();
    }
}
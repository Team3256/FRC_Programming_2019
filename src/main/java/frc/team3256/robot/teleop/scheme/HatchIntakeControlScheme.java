package frc.team3256.robot.teleop.scheme;

import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.teleop.TeleopUpdater;

public class HatchIntakeControlScheme extends CommonControlScheme {
    private Elevator mElevator = Elevator.getInstance();
    @Override
    public void onAPressed() {
        //elevator.setPositionLowCargo();
        mElevator.setWantedState(Elevator.WantedState.WANTS_TO_LOW_HATCH);
    }

    @Override
    public void onBPressed() {
        //elevator.setPositionShip();
        //mElevator.setWantedState(Elevator.WantedState.WANTS_TO_HOME);
    }

    // Home elevator
    @Override
    public void onXPressed() {
        //elevator.setPositionMidCargo();
        mElevator.setWantedState(Elevator.WantedState.WANTS_TO_MID_HATCH);
    }

    @Override
    public void onYPressed() {
        //elevator.setPositionHighCargo();
        mElevator.setWantedState(Elevator.WantedState.WANTS_TO_HIGH_HATCH);
    }

    @Override
    public void onAReleased() {

    }

    @Override
    public void onBReleased() {

    }

    @Override
    public void onXReleased() {

    }

    @Override
    public void onYReleased() {

    }

    @Override
    public void onLeftDpadReleased() {

    }

    @Override
    public void onRightDpadReleased() {

    }

    @Override
    public void onUpDpadReleased() {
    }

    @Override
    public void onDownDpadReleased() {

    }

    @Override
    public void onStartPressed() {
        if (getController() != null) {
            getController().setRumbleForDuration(1.0, 300);
        }
        TeleopUpdater.getInstance().changeToCargoControlScheme();
    }

    @Override
    public void onSelectedReleased() {

    }

    @Override
    public void onStartReleased() {
    }

    @Override
    public void onLeftJoystickPressed() {

    }

    @Override
    public void onRightJoystickPressed() {

    }

    @Override
    public void onLeftJoystickReleased() {

    }

    @Override
    public void onRightJoystickReleased() {

    }
}
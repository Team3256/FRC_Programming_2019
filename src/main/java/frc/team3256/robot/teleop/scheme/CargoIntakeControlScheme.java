package frc.team3256.robot.teleop.scheme;

import frc.team3256.robot.subsystems.NewElevator;
import frc.team3256.robot.teleop.TeleopUpdater;

public class CargoIntakeControlScheme extends CommonControlScheme {
    private NewElevator mElevator = NewElevator.getInstance();
    @Override
    public void onAPressed() {
        //elevator.setPositionLowCargo();
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_LOW_CARGO);
    }

    @Override
    public void onBPressed() {
        //elevator.setPositionShip();
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_CARGO_SHIP);
    }

    // Home elevator
    @Override
    public void onXPressed() {
        //elevator.setPositionMidCargo();
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MID_CARGO);
    }

    @Override
    public void onYPressed() {
        //elevator.setPositionHighCargo();
        mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HIGH_CARGO);
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
    public void onLeftDpadPressed() {

    }

    @Override
    public void onRightDpadPressed() {

    }

    @Override
    public void onUpDpadPressed() {

    }

    @Override
    public void onDownDpadPressed() {

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
//        if (getController() != null) {
//            getController().setRumbleForDuration(1.0, 300);
//        }
        TeleopUpdater.getInstance().changeToHatchControlScheme();
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
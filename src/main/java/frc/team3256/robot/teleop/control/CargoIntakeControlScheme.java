package frc.team3256.robot.teleop.control;

import frc.team3256.robot.teleop.TeleopUpdater;


public class CargoIntakeControlScheme extends CommonControlScheme {
    @Override
    public void onAPressed() {
        elevator.setPositionLowCargo();
    }

    @Override
    public void onBPressed() {
        elevator.setPositionShip();
    }

    // Home elevator
    @Override
    public void onXPressed() {
        elevator.setPositionMidCargo();
    }

    @Override
    public void onYPressed() {
        elevator.setPositionHighCargo();
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
        if (getController() != null) {
            getController().setRumbleForDuration(1.0, 300);
        }
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

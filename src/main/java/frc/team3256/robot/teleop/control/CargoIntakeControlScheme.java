package frc.team3256.robot.teleop.control;

import frc.team3256.robot.teleop.TeleopUpdater;


public class CargoIntakeControlScheme extends CommonControlScheme {
    @Override
    public void onAPressed() {
        elevator.setPositionLowCargo();
    }

    @Override
    public void onBPressed() {
        elevator.setPositionHome();
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
    public void onSelectedPressed() {
    }

    @Override
    public void onStartPressed() {
        getController().setRumbleForDuration(1.0, 300);
        TeleopUpdater.getInstance().changeToHatchControlScheme();
    }

    @Override
    public void onLeftShoulderPressed() {

    }

    @Override
    public void onRightShoulderPressed() {
    }

    @Override
    public void onLeftShoulderReleased() {

    }

    @Override
    public void onRightShoulderReleased() {

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

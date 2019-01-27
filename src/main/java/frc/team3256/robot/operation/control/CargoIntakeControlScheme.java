package frc.team3256.robot.operation.control;

import frc.team3256.robot.operation.TeleopUpdater;
import frc.team3256.robot.operation.XboxListenerBase;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.Elevator;

public class CargoIntakeControlScheme extends XboxListenerBase {
    private CargoIntake cargoIntake = CargoIntake.getInstance();
    private Elevator elevator = Elevator.getInstance();

    @Override
    public void onAPressed() {
        elevator.setLowCargoPosition();
    }

    @Override
    public void onBPressed() {
        elevator.setMidCargoPosition();
    }

    @Override
    public void onXPressed() {
        elevator.setPosition(0);
    }

    @Override
    public void onYPressed() {
        elevator.setHighCargoPosition();
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
        cargoIntake.setPivotFoldInPosition();
    }

    @Override
    public void onStartPressed() {
        TeleopUpdater.getInstance().changeToHatchControlScheme();
    }

    // Score Cargo
    @Override
    public void onLeftShoulderPressed() {
        cargoIntake.setScorePower(Constants.kCargoScorePower);
    }

    @Override
    public void onRightShoulderPressed() {

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
    public void onLeftShoulderReleased() {
        cargoIntake.setScorePower(0);
    }

    @Override
    public void onRightShoulderReleased() {

    }

    // Exhaust Cargo on hold
    @Override
    public void onLeftTrigger(double value) {
        if (value > 0.25) {
            cargoIntake.setIntakePower(-1);
        } else {
            cargoIntake.setIntakePower(1);
        }
    }

    // Intake Cargo on hold
    @Override
    public void onRightTrigger(double value) {
        if (value > 0.25) {
            cargoIntake.setIntakePower(1);
        } else {
            cargoIntake.setIntakePower(0);
        }
    }

    @Override
    public void onLeftJoystick(double x, double y) {
        if (y > 0.25) {
            elevator.setOpenLoop(Constants.kElevatorUpManualPower);
        }
        if (y < 0.25){
            elevator.setOpenLoop(Constants.kElevatorDownManualPower);
        }
    }

    // +Y: Move Pivot Up
    // -Y: Move Pivot Down
    @Override
    public void onRightJoyStick(double x, double y) {
        if (y > 0.25) {
            cargoIntake.setPivotPower(1);
        } else if (y < 0.25) {
            cargoIntake.setPivotPower(-1);
        } else {
            cargoIntake.setPivotPower(0);
        }
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

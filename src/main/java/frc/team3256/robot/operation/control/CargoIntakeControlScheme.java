package frc.team3256.robot.operation.control;

import frc.team3256.robot.operation.TeleopUpdater;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class CargoIntakeControlScheme extends XboxListenerBase {
    //private CargoIntake cargoIntake = CargoIntake.getInstance();
    //private Elevator elevator = Elevator.getInstance();

    @Override
    public void onAPressed() {
        //elevator.setLowCargoPosition();
        System.out.println("Set low cargo position");
    }

    @Override
    public void onBPressed() {
        System.out.println("Set mid cargo position");
        //elevator.setMidCargoPosition();
    }

    @Override
    public void onXPressed() {
        System.out.println("Home elevator");
        //elevator.setPosition(0);
    }

    @Override
    public void onYPressed() {
        System.out.println("Set high cargo position");
        //elevator.setHighCargoPosition();
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
        System.out.println("Cargo intake set fold in position");
        //cargoIntake.setPivotFoldInPosition();
    }

    @Override
    public void onStartPressed() {
        TeleopUpdater.getInstance().changeToHatchControlScheme();
    }

    // Score Cargo
    @Override
    public void onLeftShoulderPressed() {
        System.out.println("Score cargo");
        //cargoIntake.setScorePower(Constants.kCargoScorePower);
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
        System.out.println("Stop scoring cargo");
        //cargoIntake.setScorePower(0);
    }

    @Override
    public void onRightShoulderReleased() {

    }

    // Exhaust Cargo on hold
    @Override
    public void onLeftTrigger(double value) {
        if (value > 0.25) {
            System.out.println("Exhausting cargo");
            //cargoIntake.setIntakePower(-1);
        } else {
            //cargoIntake.setIntakePower(0);
        }
    }

    // Intake Cargo on hold
    @Override
    public void onRightTrigger(double value) {
        if (value > 0.25) {
            System.out.println("Intaking cargo");
            //cargoIntake.setIntakePower(1);
        } else {
            //cargoIntake.setIntakePower(0);
        }
    }

    @Override
    public void onLeftJoystick(double x, double y) {
        if (y > 0.25) {
            System.out.println("Moving elevator up manually");
            //elevator.setOpenLoop(Constants.kElevatorUpManualPower);
        }
        if (y < -0.25){
            System.out.println("Moving elevator down manually");
            //elevator.setOpenLoop(Constants.kElevatorDownManualPower);
        }
    }

    // +Y: Move Pivot Up
    // -Y: Move Pivot Down
    @Override
    public void onRightJoyStick(double x, double y) {
        if (y > 0.25) {
            System.out.println("Moving cargo pivot up manually");
            //cargoIntake.setPivotPower(1);
        } else if (y < -0.25) {
            System.out.println("Moving cargo pivot down manually");
            //cargoIntake.setPivotPower(-1);
        } else {
            //cargoIntake.setPivotPower(0);
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

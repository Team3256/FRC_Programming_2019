package frc.team3256.robot.operation.control;

import frc.team3256.robot.operation.TeleopUpdater;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.warriorlib.control.XboxControllerObserver;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class CargoIntakeControlScheme extends XboxListenerBase {
    //private CargoIntake cargoIntake = CargoIntake.getInstance();
    //private Elevator elevator = Elevator.getInstance();

    private double cargoPivotAccumulator = 0;
    private boolean intaking = false, exhausting = false;

    @Override
    public void onAPressed() {
//        elevator.setLowCargoPosition();
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
        getController().setRumble(1.0);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getController().setRumble(0);
        });
        thread.start();
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
//        if (value > 0.25 && !intaking) {
//            exhausting = true;
//            System.out.println("Exhausting cargo");
//            cargoIntake.setIntakePower(-0.4);
//        } else if (exhausting) {
//            cargoIntake.setIntakePower(0);
//            exhausting = false;
//        }
    }

    // Intake Cargo on hold
    @Override
    public void onRightTrigger(double value) {
//        if (value > 0.25 && !exhausting) {
//            intaking = true;
//            System.out.println("Intaking cargo");
//            cargoIntake.setIntakePower(0.4);
//        } else if (intaking) {
//            cargoIntake.setIntakePower(0);
//            intaking = false;
//        }
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
//        if (y > 0.25) {
//            System.out.println("Moving cargo pivot up manually");
//            cargoPivotAccumulator += 0.005;
//            cargoPivotAccumulator = Math.max(0, Math.min(.5, cargoPivotAccumulator));
//            cargoIntake.setPivotPower(-cargoPivotAccumulator);
//        } else if (y < -0.25) {
//            System.out.println("Moving cargo pivot down manually");
//            cargoPivotAccumulator += 0.005;
//            cargoPivotAccumulator = Math.max(0, Math.min(.5, cargoPivotAccumulator));
//            cargoIntake.setPivotPower(cargoPivotAccumulator);
//        } else {
//            cargoIntake.setPivotPower(0);
//            cargoPivotAccumulator -= 0.005;
//            cargoPivotAccumulator = Math.max(0, cargoPivotAccumulator);
//        }
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

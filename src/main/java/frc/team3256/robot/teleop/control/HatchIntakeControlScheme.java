package frc.team3256.robot.teleop.control;

import frc.team3256.robot.teleop.TeleopUpdater;

public class HatchIntakeControlScheme extends CommonControlScheme {
    @Override
    public void onAPressed() {
        elevator.setPositionLowHatch();
    }

    @Override
    public void onBPressed() {
        elevator.setPositionHome();
    }

    // Home elevator
    @Override
    public void onXPressed() {
        elevator.setPositionMidHatch();
    }

    @Override
    public void onYPressed() {
        elevator.setPositionHighHatch();
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
    public void onSelectedPressed() {

    }

    @Override
    public void onStartPressed() {
        Thread thread = new Thread(() -> {
            try {
                getController().setRumble(1.0);
                Thread.sleep(120);
                getController().setRumble(0);
                Thread.sleep(60);
                getController().setRumble(1.0);
                Thread.sleep(120);
                getController().setRumble(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        hatchPivot.setPositionDeploy();
        TeleopUpdater.getInstance().changeToCargoControlScheme();
    }

    @Override
    public void onSelectedReleased() {

    }

    @Override
    public void onStartReleased() {

    }

    @Override
    public void onLeftShoulderPressed() {
        hatchPivot.deployHatch();
    }

    @Override
    public void onRightShoulderPressed() {
        elevator.setPositionIntakeHatch();
        hatchPivot.deployHatch();
    }

    @Override
    public void onLeftShoulderReleased() {
        elevator.setPositionUnhookHatch();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hatchPivot.retractHatch();
        });
        thread.start();
    }

    @Override
    public void onRightShoulderReleased() {
        elevator.setPositionHookHatch();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hatchPivot.retractHatch();
        });
        thread.start();
    }

    @Override
    public void onLeftTrigger(double value) {
    }

    @Override
    public void onRightTrigger(double value) {
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

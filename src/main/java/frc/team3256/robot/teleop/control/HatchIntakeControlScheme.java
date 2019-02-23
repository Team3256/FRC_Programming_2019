package frc.team3256.robot.teleop.control;

import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.robot.teleop.TeleopUpdater;

import static frc.team3256.robot.constants.HatchConstants.kHatchPivotSpeed;

public class HatchIntakeControlScheme extends CommonControlScheme {
    private HatchPivot hatchPivot = HatchPivot.getInstance();

    @Override
    public void onAPressed() {
        elevator.setPositionLowHatch();
    }

    @Override
    public void onBPressed() {
        //elevator.setMidHatchPosition();
    }

    // Home elevator
    @Override
    public void onXPressed() {
        elevator.setPosition(0);
    }

    @Override
    public void onYPressed() {
        //elevator.setHighHatchPosition();
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
        hatchPivot.setPositionFloorIntake();
        System.out.println("INTAKING");
    }

    @Override
    public void onLeftShoulderReleased() {
        hatchPivot.retractHatch();
    }

    @Override
    public void onRightShoulderReleased() {
        hatchPivot.setPositionDeploy();
        System.out.println("DEPLOYING");
    }

    @Override
    public void onLeftTrigger(double value) {
    }

    @Override
    public void onRightTrigger(double value) {

    }

    @Override
    public void onRightJoystick(double x, double y) {
        if (y > 0.25) {
            hatchPivot.setHatchPivotPower(kHatchPivotSpeed);
        } else if (y < -0.25) {
            hatchPivot.setHatchPivotPower(-kHatchPivotSpeed);
        } else {
            hatchPivot.setHatchPivotPower(0);
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

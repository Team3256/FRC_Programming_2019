package frc.team3256.robot.teleop.control;

import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.Hanger;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.robot.teleop.TeleopUpdater;

public class ClimbControlScheme extends CommonControlScheme {
    Hanger hanger = Hanger.getInstance();
    CargoIntake cargoIntake = CargoIntake.getInstance();
    HatchPivot hatchPivot = HatchPivot.getInstance();

    @Override
    public void onAPressed() {
        hatchPivot.engageBrake();
    }

    @Override
    public void onBPressed() {
        hatchPivot.releaseBrake();
    }

    @Override
    public void onXPressed() {

    }

    @Override
    public void onYPressed() {

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
    }

    @Override
    public void onLeftTrigger(double val) {
        if (val > 0.25) {
            cargoIntake.climbDrive(-1.0);
        } else {
            cargoIntake.climbDrive(0.0);
        }
    }

    @Override
    public void onRightTrigger(double val) {
        if (val > 0.25) {
            cargoIntake.climbDrive(1.0);
        } else {
            cargoIntake.climbDrive(0.0);
        }
    }

    @Override
    public void onLeftShoulderPressed() {
        hanger.retract();
    }

    @Override
    public void onLeftShoulderReleased() {

    }

    @Override
    public void onRightShoulderPressed() {
        hanger.hang();
    }

    @Override
    public void onRightShoulderReleased() {

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

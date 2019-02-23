package frc.team3256.robot.teleop.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.BallShooter;
import frc.team3256.robot.subsystems.cargointake.CargoIntake;
import frc.team3256.robot.teleop.TeleopUpdater;

import static frc.team3256.robot.constants.BallShooterConstants.kShootSpeed;
import static frc.team3256.robot.constants.CargoConstants.kPivotSpeed;

public class CargoIntakeControlScheme extends CommonControlScheme {
    private CargoIntake cargoIntake = CargoIntake.getInstance();
    private BallShooter ballShooter = BallShooter.getInstance();

    private double cargoPivotAccumulator = 0;
    private boolean intaking = false, exhausting = false;

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
        getController().setRumbleForDuration(1.0, 300);
        TeleopUpdater.getInstance().changeToHatchControlScheme();
    }

    // Score Cargo
    @Override
    public void onLeftShoulderPressed() {
        ballShooter.setPower(-1);
    }

    @Override
    public void onRightShoulderPressed() {
        ballShooter.setPower(kShootSpeed);
    }

    @Override
    public void onLeftShoulderReleased() {
        System.out.println("Stop scoring cargo");
        ballShooter.setPower(0);
    }

    @Override
    public void onRightShoulderReleased() {
        ballShooter.setPower(0);
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
    // Exhaust Cargo on hold
    @Override
    public void onLeftTrigger(double value) {
        if (value > 0.25)
            cargoIntake.exhaust();
        else cargoIntake.setIntakePower(0);
    }

    // Intake Cargo on hold
    @Override
    public void onRightTrigger(double value) {
        if (value > 0.25)
            cargoIntake.intake();
        else cargoIntake.setIntakePower(0);
    }

    // +Y: Move Pivot Up
    // -Y: Move Pivot Down
    @Override
    public void onRightJoystick(double x, double y) {
        if (y > 0.25) {
            System.out.println("Moving cargo pivot up manually");
            SmartDashboard.putNumber("Cargo boi", 1);
            cargoIntake.setPivotPower(kPivotSpeed);
        } else if (y < -0.25) {
            SmartDashboard.putNumber("Cargo boi", -1);
            cargoIntake.setPivotPower(-kPivotSpeed);
        } else {
            SmartDashboard.putNumber("Cargo boi", 0);
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

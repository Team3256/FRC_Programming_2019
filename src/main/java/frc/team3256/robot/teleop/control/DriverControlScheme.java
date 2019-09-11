package frc.team3256.robot.teleop.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.*;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class DriverControlScheme extends XboxListenerBase {
    protected CargoIntake cargoIntake = CargoIntake.getInstance();
    protected HatchPivot hatchPivot = HatchPivot.getInstance();
    protected Hanger hanger = Hanger.getInstance();
    protected Elevator elevator = Elevator.getInstance();
    protected CargoIntakeStateBased cargo = CargoIntakeStateBased.getInstance();
    protected PivotStateBased pivot = PivotStateBased.getInstance();

    private boolean highGear = true;
    private boolean quickTurn = false;

    private double leftX = 0.0;
    private double leftY = 0.0;

    private double rightX = 0.0;
    private double rightY = 0.0;

    private boolean hatchPivotOut = false;

    public boolean isHighGear() {
        return false;
    }

    public boolean isQuickTurn() {
        return true;
    }

    public double getLeftX() {
        return leftX;
    }

    public double getLeftY() {
        return leftY;
    }

    public double getRightX() {
        return rightX;
    }

    public double getRightY() {
        return rightY;
    }

    @Override
    public void onAPressed() {

        pivot.setPosition(pivot.angleToSensorUnits(-87));
        elevator.setOpenLoop(-0.4);
        System.out.println("SLJFKLSDFJ A PRESSED");
    }

    @Override
    public void onBPressed() {
        if (!elevator.atTarget(39.5)) {
            elevator.elevatorSetPosition(39.5);
        }
    }

    @Override
    public void onXPressed() {
        elevator.setWantedState(Elevator.WantedState.WANTS_TO_HOME);
    }

    @Override
    public void onYPressed() {
        pivot.setPosition(pivot.angleToSensorUnits(-87));
        System.out.println("pivot angle "+pivot.getAngle());
        elevator.setOpenLoop(0.4);
        System.out.println("Y PRESSED SDKJFLDSK");
    }

    @Override
    public void onAReleased() {
        elevator.setOpenLoop(0.0);
        System.out.println("SDFJSDLFKJSD A RELEASED");
    }

    @Override
    public void onBReleased() {
        elevator.setOpenLoop(0.0);
    }

    @Override
    public void onXReleased() {
    }

    @Override
    public void onYReleased() {
        System.out.println("SDFJlskdfjlskdFJL Y RELEASED");
        elevator.setOpenLoop(0.0);
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

    }

    @Override
    public void onSelectedReleased() {

    }

    @Override
    public void onStartReleased() {

    }

    @Override
    public void onLeftShoulderPressed() {
        pivot.setPosition(pivot.angleToSensorUnits(-96));
        cargo.setIntakePower(0.0);
        //cargoIntake.exhaust();
        if (hatchPivotOut) {
            hatchPivot.retractHatch();
            hatchPivotOut = false;
        } else {
            hatchPivot.deployHatch();
            hatchPivotOut = true;
        }
    }

    @Override
    public void onLeftShoulderReleased() {
        //cargoIntake.setIntakePower(0);
    }

    @Override
    public void onRightShoulderPressed() {
        //hatchPivot.setPositionCargoIntake();
        //cargoIntake.intake();
        elevator.setOpenLoop(0.0);
    }

    @Override
    public void onRightShoulderReleased() {
        //cargoIntake.setIntakePower(0);
        //hatchPivot.setPositionDeploy();
        //pivot.setOpenLoop(0.0);
        //hatchPivot.engageBrake();
    }

    @Override
    public void onLeftTrigger(double value) {
//        highGear = value > 0.25;
//        SmartDashboard.putBoolean("HighGear", highGear);
        if(value > 0.25) {
            pivot.setPosition(pivot.angleToSensorUnits(-90));
            //System.out.println(pivot.getAngle());
            cargo.setIntakePower(0.6);
        }
        else {
            pivot.setPosition(pivot.angleToSensorUnits(-90));
            cargo.setIntakePower(0.0);
        }
    }

    @Override
    public void onRightTrigger(double value) {
//        quickTurn = value < 0.25;
        if(value > 0.25) {
            pivot.setPosition(pivot.angleToSensorUnits(-30));
            cargo.setIntakePower(-0.3);
        }
        else {
            pivot.setPosition(pivot.angleToSensorUnits(-87));
            cargo.setIntakePower(-0.1);
        }
    }

    @Override
    public void onLeftJoystick(double x, double y) {
        leftX = x;
        leftY = y;
    }

    @Override
    public void onRightJoystick(double x, double y) {
        rightX = x;
        rightY = y;
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

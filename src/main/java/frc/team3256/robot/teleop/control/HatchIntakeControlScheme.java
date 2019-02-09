package frc.team3256.robot.teleop.control;

import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.robot.teleop.TeleopUpdater;
import frc.team3256.warriorlib.control.XboxListenerBase;
import static frc.team3256.robot.constants.ElevatorConstants.kElevatorSpeed;
import static frc.team3256.robot.constants.HatchConstants.kHatchPivotSpeed;

public class HatchIntakeControlScheme extends XboxListenerBase {
    private HatchPivot hatchPivot = HatchPivot.getInstance();
    private Elevator elevator = Elevator.getInstance();

    @Override
    public void onAPressed() { //elevator.setLowHatchPosition();
         }

    @Override
    public void onBPressed() {
        //elevator.setMidHatchPosition();
    }

    // Home elevator
    @Override
    public void onXPressed() {
        //elevator.setPosition(0);
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
    public void onStartPressed() { TeleopUpdater.getInstance().changeToCargoControlScheme(); }

    @Override
    public void onSelectedReleased() {

    }

    @Override
    public void onStartReleased() {

    }

    @Override
    public void onLeftShoulderPressed() {

    }

    @Override
    public void onRightShoulderPressed() {
        //hatchPivot.setFloorIntakePosition();
    }

    @Override
    public void onLeftShoulderReleased() {
        //hatchPivot.setDeployPosition();
    }

    @Override
    public void onRightShoulderReleased() {

    }

    @Override
    public void onLeftTrigger(double value) {

    }

    @Override
    public void onRightTrigger(double value) {
        if(value > 0.25) {
            //hatchPivot.deployHatch();
        }
        else { //hatchPivot.closeHatch();
        }
    }

    // Move elevator manually
    @Override
    public void onLeftJoystick(double x, double y) {
        if (y > 0.25) {
            elevator.setOpenLoop(kElevatorSpeed);
        } else if (y < -0.25){
            elevator.setOpenLoop(-kElevatorSpeed);
        } else {
            elevator.setOpenLoop(0);
        }
    }

    @Override
    public void onRightJoyStick(double x, double y) {
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

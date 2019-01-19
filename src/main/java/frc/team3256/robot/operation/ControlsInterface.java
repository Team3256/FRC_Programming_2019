package frc.team3256.robot.operation;

public interface ControlsInterface {

    //--------------Driver-----------------

    //Y Axis: Throttle for drive
    double getThrottle();

    //X Axis: Turn for drive
    double getTurn();

    //Hold: Enables quick turn
    boolean getQuickTurn();

    //Hold: Shifts down to low gear (default is high gear)
    boolean getLowGear();

    boolean getHatchIntake();

    boolean scoreHatch();

    boolean pivotHatchUp();

    boolean pivotHatchDown();

    boolean getCargoIntake();

    boolean getCargoExhaust();

    boolean scoreCargo();

    boolean pivotCargoUp();

    boolean pivotCargoDown();

    boolean manualElevatorUp();

    boolean manualElevatorDown();

    boolean cargoPresetLow();

    boolean cargoPresetMid();

    boolean cargoPresetHigh();

    boolean hatchPresetLow();

    boolean hatchPresetMid();

    boolean hatchPresetHigh();

    boolean hang();
}

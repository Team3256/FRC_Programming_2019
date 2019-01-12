package frc.team3256.robot.operation;

public class DualLogitechConfig implements ControlsInterface {
    @Override
    public double getThrottle() {
        return 0;
    }

    @Override
    public double getTurn() {
        return 0;
    }

    @Override
    public boolean getQuickTurn() {
        return false;
    }

    @Override
    public boolean getLowGear() {
        return false;
    }

    @Override
    public boolean scoreCargo() {
        return false;
    }

    @Override
    public boolean scoreHatch() {
        return false;
    }

    @Override
    public boolean getCargoIntake() {
        return false;
    }

    @Override
    public boolean getHatchIntake() {
        return false;
    }

    @Override
    public boolean getCargoExhaust() {
        return false;
    }

    @Override
    public boolean manualElevatorUp() {
        return false;
    }

    @Override
    public boolean manualElevatorDown() {
        return false;
    }

    @Override
    public boolean cargoPresetLow() {
        return false;
    }

    @Override
    public boolean cargoPresetMid() {
        return false;
    }

    @Override
    public boolean cargoPresetHigh() {
        return false;
    }

    @Override
    public boolean hatchPresetLow() {
        return false;
    }

    @Override
    public boolean hatchPresetMid() {
        return false;
    }

    @Override
    public boolean hatchPresetHigh() {
        return false;
    }

    @Override
    public boolean hang() {
        return false;
    }
}

package frc.team3256.robot.operation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class DriveConfigImplementation implements ControlsInterface {

    XboxController driver = new XboxController(0);
    XboxController manipulator = new XboxController(1);



    @Override
    public double getThrottle() { return -driver.getRawAxis(1);}

    @Override
    public double getTurn() { return driver.getRawAxis(4);}

    @Override
    public boolean getQuickTurn() {
        if(driver.getRawAxis(3) > 0.2){
            return true;
        }
        return false;
    }

    @Override
    public boolean getLowGear() { return false; }

    @Override
    public boolean switchManipulatorControlMode() {
        manipulator.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
        manipulator.setRumble(GenericHID.RumbleType.kRightRumble, 1);
        return manipulator.getStartButton();
    }

    @Override
    public boolean switchDriverControlMode() {
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 1);
        return driver.getStartButton();
    }

    @Override
    public boolean getHatchIntake() { return driver.getAButton(); }

    @Override
    public boolean scoreHatch() { return false; }

    @Override
    public boolean manualHatchUp() { return false; }

    @Override
    public boolean manualHatchDown() { return false; }

    @Override
    public boolean hatchPivotFloorIntakePreset() {
        return false;
    }

    @Override
    public boolean hatchPivotDeployPreset() {
        return false;
    }

    @Override
    public boolean getCargoIntake() { return driver.getBButton(); }

    @Override
    public boolean getCargoExhaust() { return false; }

    @Override
    public boolean scoreCargo() { return false; }

    @Override
    public boolean pivotCargoUp() { return false; }

    @Override
    public boolean pivotCargoDown() { return false; }

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

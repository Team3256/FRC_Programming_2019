package frc.team3256.robot.operation;

import edu.wpi.first.wpilibj.XboxController;

public class DriveConfigImplementation implements ControlsInterface {

	XboxController driver = new XboxController(0);
	XboxController manipulator = new XboxController(1);

	@Override
	public double getThrottle() {
		return -driver.getRawAxis(1);
	}

	@Override
	public double getTurn() {
		return driver.getRawAxis(4);
	}

	@Override
	public boolean getQuickTurn() {
		return driver.getRawAxis(3) > 0.2;
	}

	@Override
	public boolean getLowGear() {
		return false;
	}

	@Override
	public boolean switchManipulatorControlMode() {
		return manipulator.getStartButtonPressed();
	}

	@Override
	public boolean switchDriverControlMode() {
		return driver.getStartButtonPressed();
	}

	@Override
	public boolean scoreHatch() {
		return false;
	}

	@Override
	public boolean manualHatchUp() {
		return false;
	}

	@Override
	public boolean manualHatchDown() {
		return false;
	}

	@Override
	public boolean hatchPivotFloorIntakePreset() {
		return false;
	}

	@Override
	public boolean hatchPivotDeployPreset() {
		return false;
	}

	@Override
	public boolean getCargoIntake() {
		return driver.getBButton();
	}

	@Override
	public boolean getCargoExhaust() {
		return false;
	}

	@Override
	public boolean scoreCargo() {
		return false;
	}

	@Override
	public boolean pivotCargoUp() {
		return false;
	}

	@Override
	public boolean pivotCargoDown() {
		return false;
	}

	@Override
	public boolean pivotCargoFloorPreset() {
		return false;
	}

	@Override
	public boolean pivotCargoClearancePreset() {
		return false;
	}

	@Override
	public boolean pivotCargoTransferPreset() {
		return false;
	}

	@Override
	public boolean pivotCargoFoldInPreset() {
		return false;
	}

	@Override
	public boolean manualElevatorUp() {
		return manipulator.getRawAxis(1) < -0.25;
	}

	@Override
	public boolean manualElevatorDown() {
		return manipulator.getRawAxis(1) > 0.25;
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
		return manipulator.getAButtonPressed();
	}

	@Override
	public boolean hatchPresetMid() {
		return manipulator.getBButtonPressed();
	}

	@Override
	public boolean hatchPresetHigh() {
		return manipulator.getYButtonPressed();
	}

	@Override
	public boolean hang() {
		return manipulator.getRawButtonPressed(6);
	}

	@Override
	public boolean retract() {
		return false;
	}

	@Override
	public double getHangDrive() {
		return -driver.getRawAxis(1);
	}
}

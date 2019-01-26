package frc.team3256.robot.operation;

import edu.wpi.first.wpilibj.GenericHID;
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
	public boolean scoreHatch() {
		return manipulator.getTriggerAxis(GenericHID.Hand.kRight) > 0.25;
	}

	@Override
	public boolean manualHatchUp() {
		return manipulator.getRawAxis(5) < -0.25;
	}

	@Override
	public boolean manualHatchDown() {
		return -manipulator.getRawAxis(5) > 0.25;
	}

	@Override
	public boolean hatchPivotFloorIntakePreset() {
		return manipulator.getBumper(GenericHID.Hand.kRight);
	}

	@Override
	public boolean hatchPivotDeployPreset() {
		return false;
	}

	@Override
	public boolean getCargoIntake() {
		return manipulator.getTriggerAxis(GenericHID.Hand.kRight) > 0.25;
	}

	@Override
	public boolean getCargoExhaust() {
		return manipulator.getTriggerAxis(GenericHID.Hand.kLeft) > 0.25;
	}

	@Override
	public boolean scoreCargo() {
		return manipulator.getBumper(GenericHID.Hand.kLeft);
	}

	@Override
	public boolean pivotCargoUp() {
		return manipulator.getRawAxis(5) < -0.25;
	}

	@Override
	public boolean pivotCargoDown() {
		return manipulator.getRawAxis(5) > 0.25;
	}

	@Override
	public boolean togglePivotCargoFloorPreset() {
		return manipulator.getBumper(GenericHID.Hand.kRight);
	}

	@Override
	public boolean togglePivotCargoTransferPreset() {
		return manipulator.getBumper(GenericHID.Hand.kRight);
	}

	@Override
	public boolean pivotCargoClearancePreset() {
		return false;
	}

	//Check if Xbox button is supported
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
		return manipulator.getAButtonPressed();
	}

	@Override
	public boolean cargoPresetMid() {
		return manipulator.getBButtonPressed();
	}

	@Override
	public boolean cargoPresetHigh() {
		return manipulator.getYButtonPressed();
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
		return manipulator.getBackButtonPressed();
	}

	@Override
	public boolean retract() {
		return false;
	}

	@Override
	public double getHangDriveThrottle() {
		return -driver.getRawAxis(1);
	}

	@Override
	public double getHangDriveTurn() {
		return driver.getRawAxis(4);
	}

	@Override
	public boolean getHangDriveQuickTurn() {
		return driver.getRawAxis(3) > 0.2;
	}
}

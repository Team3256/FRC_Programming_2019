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

	boolean switchManipulatorControlMode();

	boolean scoreHatch();

	boolean manualHatchUp();

	boolean manualHatchDown();

	boolean hatchPivotFloorIntakePreset();

	boolean getCargoIntake();

	boolean getCargoExhaust();

	boolean scoreCargo();

	boolean pivotCargoUp();

	boolean pivotCargoDown();

	boolean togglePivotCargoFloorPreset();

	boolean togglePivotCargoTransferPreset();

	boolean pivotCargoFoldInPreset();

	boolean manualElevatorUp();

	boolean manualElevatorDown();

	boolean homePreset();

	boolean cargoPresetLow();

	boolean cargoPresetMid();

	boolean cargoPresetHigh();

	boolean hatchPresetLow();

	boolean hatchPresetMid();

	boolean hatchPresetHigh();

	boolean hang();

	boolean retract();

	double getHangDriveThrottle();

	double getHangDriveTurn();

	boolean getHangDriveQuickTurn();
}

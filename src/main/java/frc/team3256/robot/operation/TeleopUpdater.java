package frc.team3256.robot.operation;

import frc.team3256.robot.subsystems.*;
import frc.team3256.warriorlib.control.DrivePower;

public class TeleopUpdater {

	boolean highGear = true;
	boolean hangHighGear = true;

	boolean prevCargoFloorPositionToggle = false;
	boolean prevCargoTransferPositionToggle = false;

	private DriveConfigImplementation controls = new DriveConfigImplementation();

	private HatchPivot m_hatch = HatchPivot.getInstance();
	private CargoIntake m_cargo = CargoIntake.getInstance();
	private Elevator m_elevator = Elevator.getInstance();
	private Hanger m_hanger = Hanger.getInstance();
	private DriveTrain m_drive = DriveTrain.getInstance();
	private boolean isHatchMode = true;


	public void update() {
		//Switch Control Modes
		System.out.println(isHatchMode ? "HATCH MODE" : "CARGO MODE");
		boolean switchManipulatorControlMode = controls.switchManipulatorControlMode();

		//Drive
		double throttle = controls.getThrottle();
		double turn = controls.getTurn();
		boolean quickTurn = controls.getQuickTurn();
		boolean shiftDown = controls.getLowGear();

		//Hatch
		boolean scoreHatch = controls.scoreHatch();
		boolean manualHatchUp = controls.manualHatchUp();
		boolean manualHatchDown = controls.manualHatchDown();
		boolean hatchFloorIntakePreset = controls.hatchPivotFloorIntakePreset();

		//Cargo
		boolean intakeCargo = controls.getCargoIntake();
		boolean exhaustCargo = controls.getCargoExhaust();
		boolean scoreCargo = controls.scoreCargo();
		boolean pivotCargoUp = controls.pivotCargoUp();
		boolean pivotCargoDown = controls.pivotCargoDown();
		boolean togglePivotCargoFloor = controls.togglePivotCargoFloorPreset();
		boolean togglePivotCargoTransfer = controls.togglePivotCargoTransferPreset();
		boolean pivotCargoFoldIn = controls.pivotCargoFoldInPreset();

		//Elevator
		boolean manualElevatorUp = controls.manualElevatorUp();
		boolean manualElevatorDown = controls.manualElevatorDown();
		boolean homePreset = controls.homePreset();
		boolean cargoPresetLow = controls.cargoPresetLow();
		boolean cargoPresetMid = controls.cargoPresetMid();
		boolean cargoPresetHigh = controls.cargoPresetHigh();
		boolean hatchPresetLow = controls.hatchPresetLow();
		boolean hatchPresetMid = controls.hatchPresetMid();
		boolean hatchPresetHigh = controls.hatchPresetHigh();

		//Hanger
		boolean hang = controls.hang();
		boolean retract = controls.retract();
		double hangDriveThrottle = controls.getHangDriveThrottle();
		double hangDriveTurn = controls.getHangDriveTurn();
		boolean hangDriveQuickTurn = controls.getHangDriveQuickTurn();

		//DriveTrain Subsystem
		m_drive.setBrakeMode();
		DrivePower drivePower = DriveTrain.curvatureDrive(throttle, turn, quickTurn, highGear/*!shiftDown*/);
		m_drive.setHighGear(drivePower.getHighGear());
		m_drive.setOpenLoop(drivePower.getLeft(), drivePower.getRight());

		if (m_drive.leftSlave.getOutputCurrent() > 10 || m_drive.leftSlave.getOutputCurrent() > 10)
			System.out.println("LEFT " + m_drive.leftSlave.getOutputCurrent() + " RIGHT " + m_drive.rightSlave.getOutputCurrent());

		//Switch between Hatch and Cargo Control Modes
		if (switchManipulatorControlMode) {
			isHatchMode = !isHatchMode;
		}

        //CargoIntake Subsystem
		if (!isHatchMode) {
			if (intakeCargo) {
				m_cargo.setRobotState(new CargoIntake.IntakingState());
			}
			else if (exhaustCargo) {
				m_cargo.setRobotState(new CargoIntake.ExhaustingState());
			}
			else if (scoreCargo) {
				m_cargo.setRobotState(new CargoIntake.ScoringState());
			}
			else if (pivotCargoUp) {
				m_cargo.setRobotState(new CargoIntake.ManualPivotUpState());
			}
			else if (pivotCargoDown) {
				m_cargo.setRobotState(new CargoIntake.ManualPivotDownState());
			}
			else if (togglePivotCargoFloor && !prevCargoTransferPositionToggle) {
				m_cargo.setRobotState(new CargoIntake.PivotFloorPresetState());
				prevCargoFloorPositionToggle = true;
				prevCargoTransferPositionToggle = false;
			}
			else if (togglePivotCargoTransfer && !prevCargoFloorPositionToggle) {
				m_cargo.setRobotState(new CargoIntake.PivotTransferPresetState());
				prevCargoTransferPositionToggle = true;
				prevCargoFloorPositionToggle = false;
			}
			else if (pivotCargoFoldIn) {
				m_cargo.setRobotState(new CargoIntake.PivotFoldInPresetState());
			}
			else m_cargo.setRobotState(new CargoIntake.PivotClearancePresetState());
		}


        //HatchPivot Subsystem
        if (isHatchMode) {
            if (scoreHatch) {
                m_hatch.setRobotState(new HatchPivot.DeployingState());
            }
            else if (manualHatchUp) {
                m_hatch.setRobotState(new HatchPivot.ManualPivotUpState());
            }
            else if (manualHatchDown) {
                m_hatch.setRobotState(new HatchPivot.ManualPivotDownState());
            }
            else if (hatchFloorIntakePreset) {
                m_hatch.setRobotState(new HatchPivot.PivotFloorIntakePreset());
            }
            else m_hatch.setRobotState(new HatchPivot.PivotDeployPreset());
        }

        //Elevator Subsystem
        if (manualElevatorUp) {
            m_elevator.setRobotState(new Elevator.ManualUpState());
        }
        else if (manualElevatorDown) {
            m_elevator.setRobotState(new Elevator.ManualDownState());
        }
        else if (homePreset){
        	m_elevator.setRobotState(new Elevator.HomePresetState());
		}
        else m_elevator.setRobotState(new Elevator.HoldState());

        //Cargo Mode Presets
        if (!isHatchMode) {
            if (cargoPresetHigh) {
                m_elevator.setRobotState(new Elevator.HighCargoPresetState());
            }
            else if (cargoPresetMid) {
                m_elevator.setRobotState(new Elevator.MidCargoPresetState());
            }
            else if (cargoPresetLow) {
                m_elevator.setRobotState(new Elevator.LowCargoPresetState());
            }
        }

        //Hatch Mode Presets
        if (isHatchMode) {
        	if (hatchPresetHigh) {
                m_elevator.setRobotState(new Elevator.HighHatchPresetState());
            }
            else if (hatchPresetMid) {
                m_elevator.setRobotState(new Elevator.MidHatchPresetState());
            }
            else if (hatchPresetLow) {
                m_elevator.setRobotState(new Elevator.LowHatchPresetState());
            }
        }

        //Hanger Subsystem
        if (hang){
            m_hatch.setRobotState(new HatchPivot.PivotFloorIntakePreset());
            m_hatch.setRobotState(new HatchPivot.RatchetState());
            m_elevator.setRobotState(new Elevator.LowHatchPresetState());
            m_hanger.setRobotState(new Hanger.HangState());
            DrivePower hangDrivePower = DriveTrain.hangCurvatureDrive(hangDriveThrottle, hangDriveTurn,
					hangDriveQuickTurn, hangHighGear);
			m_drive.setHighGear(hangDrivePower.getHighGear());
			m_drive.setHangDrive(hangDrivePower.getLeft(), hangDrivePower.getRight());
		}
        else if (retract) {
            m_hanger.setRobotState(new Hanger.RetractState());
        }

	}
}
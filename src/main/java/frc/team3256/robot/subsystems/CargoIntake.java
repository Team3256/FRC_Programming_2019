package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.*;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class CargoIntake extends SubsystemBase {

	private static CargoIntake instance;
	private WPI_TalonSRX cargoIntake, cargoScoreLeft, cargoScoreRight;
	private CANSparkMax cargoPivot;
	private CANPIDController cargoPID;
	private CANEncoder cargoEncoder;
	private RobotState robotState = new IdleState();

	private CargoIntake() {
		cargoIntake = TalonSRXUtil.generateGenericTalon(Constants.kCargoIntakePort);
		cargoScoreLeft = TalonSRXUtil.generateGenericTalon(Constants.kCargoScoreLeftPort);
		cargoScoreRight = TalonSRXUtil.generateGenericTalon(Constants.kCargoScoreRightPort);
		cargoPivot = SparkMAXUtil.generateGenericSparkMAX(Constants.kCargoPivotPort, CANSparkMaxLowLevel.MotorType.kBrushless);
		cargoPID = cargoPivot.getPIDController();
		cargoEncoder = cargoPivot.getEncoder();

		SparkMAXUtil.setBrakeMode(cargoPivot);

		SparkMAXUtil.setPIDGains(cargoPID, Constants.kCargoPivotUpSlot, Constants.kCargoPivotUpP, Constants.kCargoPivotUpI, Constants.kCargoPivotUpD, Constants.kCargoPivotUpF, Constants.kCargoPivotUpIz);

		SparkMAXUtil.setPIDGains(cargoPID, Constants.kCargoPivotDownSlot, Constants.kCargoPivotDownP, Constants.kCargoPivotDownI, Constants.kCargoPivotDownD, Constants.kCargoPivotDownF, Constants.kCargoPivotDownIz);

		cargoPID.setOutputRange(Constants.kCargoPivotMinOutput, Constants.kCargoPivotMaxOutput);
	}

	public static CargoIntake getInstance() {
		return instance == null ? instance = new CargoIntake() : instance;
	}

	@Override
	public void update(double timestamp) {
		RobotState newState = robotState.update();
		if (!robotState.equals(newState)) {
			System.out.println(String.format("(%s) STATE CHANGE: %s -> %s", getClass().getSimpleName(), robotState.getClass().getSimpleName(), newState.getClass().getSimpleName()));
		}
		robotState = newState;
	}

	public void setRobotState(RobotState state) {
		this.robotState = state;
	}

	public void setScore(double left, double right) {
		cargoScoreLeft.set(left);
		cargoScoreRight.set(right);
	}

	private void setPosition(double position, int slot) {
		cargoPID.setReference(position, ControlType.kPosition, slot);
	}

	private double getPosition() {
		return cargoEncoder.getPosition();
	}

	@Override
	public void outputToDashboard() {

	}

	@Override
	public void zeroSensors() {

	}

	@Override
	public void init(double timestamp) {
	}

	@Override
	public void end(double timestamp) {

	}

	public static class IntakingState extends RobotState {
		@Override
		public RobotState update() {
			CargoIntake.getInstance().cargoIntake.set(Constants.kCargoIntakePower);

			// Perhaps read sensor here and stop intaking

			return new IdleState();
		}
	}

	public static class ExhaustingState extends RobotState {
		@Override
		public RobotState update() {
			CargoIntake.getInstance().cargoIntake.set(Constants.kCargoExhaustPower);
			return new IdleState();
		}
	}

	public static class ScoringState extends RobotState {
		@Override
		public RobotState update() {
			CargoIntake.getInstance().setScore(Constants.kCargoScorePower, Constants.kCargoScorePower);
			return new IdleState();
		}
	}

	public static class ManualPivotUpState extends RobotState {
		@Override
		public RobotState update() {
			CargoIntake.getInstance().cargoPivot.set(Constants.kCargoPivotUpPower);
			return new IdleState();
		}
	}

	public static class ManualPivotDownState extends RobotState {
		@Override
		public RobotState update() {
			CargoIntake.getInstance().cargoPivot.set(Constants.kCargoPivotDownPower);
			return new IdleState();
		}
	}

	public static class PivotFloorPresetState extends RobotState {
		@Override
		public RobotState update() {
			if (CargoIntake.getInstance().getPosition() > Constants.kCargoPivotFloorPreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotFloorPreset, Constants.kCargoPivotDownSlot);
				return new IdleState();
			} else if (CargoIntake.getInstance().getPosition() < Constants.kCargoPivotFloorPreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotFloorPreset, Constants.kCargoPivotUpSlot);
				return new IdleState();
			} else
				return new IdleState();
		}
	}

	public static class PivotClearancePresetState extends RobotState {
		@Override
		public RobotState update() {
			if (CargoIntake.getInstance().getPosition() > Constants.kCargoPivotClearancePreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotClearancePreset, Constants.kCargoPivotDownSlot);
				return new IdleState();
			} else if (CargoIntake.getInstance().getPosition() < Constants.kCargoPivotClearancePreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotClearancePreset, Constants.kCargoPivotUpSlot);
				return new IdleState();
			} else
				return new IdleState();
		}
	}

	public static class PivotTransferPresetState extends RobotState {
		@Override
		public RobotState update() {
			if (CargoIntake.getInstance().getPosition() > Constants.kCargoPivotTransferPreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotTransferPreset, Constants.kCargoPivotDownSlot);
			} else if (CargoIntake.getInstance().getPosition() < Constants.kCargoPivotTransferPreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotTransferPreset, Constants.kCargoPivotUpSlot);
			}
			return new IdleState();
		}
	}

	public static class PivotFoldInPresetState extends RobotState {
		@Override
		public RobotState update() {
			if (CargoIntake.getInstance().getPosition() > Constants.kCargoPivotFoldInPreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotFoldInPreset, Constants.kCargoPivotDownSlot);
			} else if (CargoIntake.getInstance().getPosition() < Constants.kCargoPivotFoldInPreset) {
				CargoIntake.getInstance().setPosition(Constants.kCargoPivotFoldInPreset, Constants.kCargoPivotUpSlot);
			}
			return new IdleState();
		}
	}

	public static class IdleState extends RobotState {
		@Override
		public RobotState update() {
			CargoIntake.getInstance().cargoIntake.set(0);
			return new IdleState();
		}
	}
}

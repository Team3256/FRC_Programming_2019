package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.Spark;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class CargoIntake extends SubsystemBase {

	private static CargoIntake instance;
	private WPI_TalonSRX cargoIntake, cargoScoreLeft, cargoScoreRight;
	private CANSparkMax cargoPivot;
	private CANPIDController cargoPID;
	private CANEncoder cargoEncoder;

	private CargoIntake() {
		cargoIntake = TalonSRXUtil.generateGenericTalon(Constants.kCargoIntakePort);
		cargoScoreLeft = TalonSRXUtil.generateGenericTalon(Constants.kCargoScoreLeftPort);
		cargoScoreRight = TalonSRXUtil.generateGenericTalon(Constants.kCargoScoreRightPort);
		cargoPivot = SparkMAXUtil.generateGenericSparkMAX(Constants.kCargoPivotPort, CANSparkMaxLowLevel.MotorType.kBrushless);
		SparkMAXUtil.setBrakeMode(cargoPivot);
		cargoPID = cargoPivot.getPIDController();
		cargoEncoder = cargoPivot.getEncoder();

		SparkMAXUtil.setPIDGains(cargoPID, Constants.kCargoPivotUpSlot, Constants.kCargoPivotUpP, Constants.kCargoPivotUpI, Constants.kCargoPivotUpD, Constants.kCargoPivotUpF, Constants.kCargoPivotUpIz);

		SparkMAXUtil.setPIDGains(cargoPID, Constants.kCargoPivotDownSlot, Constants.kCargoPivotDownP, Constants.kCargoPivotDownI, Constants.kCargoPivotDownD, Constants.kCargoPivotDownF, Constants.kCargoPivotDownIz);

		cargoPID.setOutputRange(Constants.kCargoPivotMinOutput, Constants.kCargoPivotMaxOutput);
	}

	public static CargoIntake getInstance() {
		return instance == null ? instance = new CargoIntake() : instance;
	}

	@Override
	public void update(double timestamp) {
		System.out.println("Cargo Pivot Pos: " + getPosition());
//		if (getPosition() > Constants.kCargoPivotClearancePreset) {
//			cargoPivot.set(0);
//			setPosition(Constants.kCargoPivotClearancePreset);
//		} else if (getPosition() < Constants.kCargoPivotClearancePreset) {
//			cargoPivot.set(0);
//			setPosition(Constants.kCargoPivotClearancePreset);
//		}
	}

	public void setPositionFoldIn() {

	}

	public void setPositionElevatorExchange() {

	}

	public void setPositionElevatorClearance() {

	}

	public void setPositionFloorIntake() {

	}

	public void setIntakePower(double power) {
		cargoIntake.set(power);
	}

	public void setScorePower(double power) {
		cargoScoreLeft.set(power);
		cargoScoreRight.set(power);
	}

	public void setPivotPower(double power) {
		cargoPivot.set(power);
	}

	private void setPosition(double position) {
		cargoPID.setReference(position, ControlType.kPosition);
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

	public void setPivotFloorPosition() {
	}

	public void setPivotTransferPosition() {

	}

	public void setPivotFoldInPosition() {

	}
}

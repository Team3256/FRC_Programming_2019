package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.*;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.CargoConstants.*;

public class CargoIntake extends SubsystemBase {

	private static CargoIntake instance;
	private WPI_TalonSRX cargoIntake;
	private CANSparkMax cargoPivot;
	private CANPIDController cargoPID;
	private CANEncoder cargoEncoder;

	private CargoIntake() {
		cargoIntake = TalonSRXUtil.generateGenericTalon(kIntake);
		cargoPivot = SparkMAXUtil.generateGenericSparkMAX(kPivot, CANSparkMaxLowLevel.MotorType.kBrushless);
		SparkMAXUtil.setBrakeMode(cargoPivot);
		cargoPID = cargoPivot.getPIDController();
		cargoEncoder = cargoPivot.getEncoder();

		SparkMAXUtil.setPIDGains(cargoPID, 0, kPivotP, kPivotI, kPivotD, kPivotF, kPivotIz);
		cargoPID.setOutputRange(kPivotMinOutput, kPivotMaxOutput);
	}

	public static CargoIntake getInstance() {
		return instance == null ? instance = new CargoIntake() : instance;
	}

	@Override
	public void update(double timestamp) {
		System.out.println("Cargo Pivot Pos: " + getPosition());
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
		//cargoEncoder.setPosition(0);
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

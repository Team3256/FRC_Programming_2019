package frc.team3256.robot.subsystems;

import com.revrobotics.*;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class Elevator extends SubsystemBase {

	private static Elevator instance;
	private CANSparkMax master, slaveOne, slaveTwo, slaveThree;
	private CANPIDController masterPID;
	private CANEncoder masterEncoder;

	private Elevator() {
		master = SparkMAXUtil.generateGenericSparkMAX(Constants.kElevatorMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
		slaveOne = SparkMAXUtil.generateSlaveSparkMAX(Constants.kElevatorSlaveOne, CANSparkMaxLowLevel.MotorType.kBrushless, master);
		slaveTwo = SparkMAXUtil.generateSlaveSparkMAX(Constants.kElevatorSlaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless, master);
		slaveThree = SparkMAXUtil.generateSlaveSparkMAX(Constants.kElevatorSlaveThree, CANSparkMaxLowLevel.MotorType.kBrushless, master);
		masterPID = master.getPIDController();
		masterEncoder = master.getEncoder();

		SparkMAXUtil.setCoastMode(master, slaveOne, slaveTwo, slaveThree);

		SparkMAXUtil.setPIDGains(masterPID, Constants.kElevatorHoldSlot, Constants.kElevatorHoldP, Constants.kElevatorHoldI, Constants.kElevatorHoldD, Constants.kElevatorHoldF, Constants.kElevatorHoldIz);

		SparkMAXUtil.setPIDGains(masterPID, Constants.kElevatorUpSlot, Constants.kElevatorUpP, Constants.kElevatorUpI, Constants.kElevatorUpD, Constants.kElevatorUpF, Constants.kElevatorUpIz);

		SparkMAXUtil.setPIDGains(masterPID, Constants.kElevatorDownSlot, Constants.kElevatorDownP, Constants.kElevatorDownI, Constants.kElevatorDownD, Constants.kElevatorDownF, Constants.kElevatorDownIz);

		masterPID.setOutputRange(Constants.kElevatorMinOutput, Constants.kElevatorMaxOutput);
	}

	public static Elevator getInstance() {
		return instance == null ? instance = new Elevator() : instance;
	}

	public void setOpenLoop(double power) {
		master.set(power);
	}

	public void setPosition(double position) {
		masterPID.setReference(position, ControlType.kPosition);
	}

	private double getPosition() {
		return masterEncoder.getPosition();
	}

	public void setHighCargoPosition() {
		setPosition(Constants.kHighCargoPreset);
	}

	public void setMidCargoPosition() {
		setPosition(Constants.kMidCargoPreset);
	}

	public void setLowCargoPosition() {
		setPosition(Constants.kLowCargoPreset);
	}

	public void setHighHatchPosition() {
		setPosition(Constants.kHighHatchPreset);
	}

	public void setMidHatchPosition() {
		setPosition(Constants.kMidHatchPreset);
	}

	public void setLowHatchPosition() {
		setPosition(Constants.kLowHatchPreset);
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
	public void update(double timestamp) {
		if (getPosition() > Constants.kHighCargoPreset) {
			setPosition(Constants.kHighCargoPreset);
		} else if (getPosition() < Constants.kLowCargoPreset) {
			setPosition(Constants.kLowCargoPreset);
		}
	}

	@Override
	public void end(double timestamp) {

	}
}

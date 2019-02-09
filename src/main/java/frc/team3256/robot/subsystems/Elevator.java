package frc.team3256.robot.subsystems;

import com.revrobotics.*;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class Elevator extends SubsystemBase {

	private static Elevator instance;
	private CANSparkMax master, slaveOne, slaveTwo, slaveThree;
	private CANPIDController masterPID;
	private CANEncoder masterEncoder;

	private Elevator() {
		master = SparkMAXUtil.generateGenericSparkMAX(kSparkMaxMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
		slaveOne = SparkMAXUtil.generateSlaveSparkMAX(kSparkMaxSlaveOne, CANSparkMaxLowLevel.MotorType.kBrushless, master);
		slaveTwo = SparkMAXUtil.generateSlaveSparkMAX(kSparkMaxSlaveTwo, CANSparkMaxLowLevel.MotorType.kBrushless, master);
		slaveThree = SparkMAXUtil.generateSlaveSparkMAX(kSparkMaxSlaveThree, CANSparkMaxLowLevel.MotorType.kBrushless, master);

		masterPID = master.getPIDController();
		masterEncoder = master.getEncoder();

		SparkMAXUtil.setCoastMode(master, slaveOne, slaveTwo, slaveThree);

		SparkMAXUtil.setPIDGains(masterPID, 0, kElevatorP, kElevatorI, kElevatorD, kElevatorF, kElevatorIz);

		masterPID.setOutputRange(kElevatorMinOutput, kElevatorMaxOutput);
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

	public void setPositionHighCargo() {
		setPosition(kPositionHighCargo);
	}

	public void setPositionMidCargo() {
		setPosition(kPositionMidCargo);
	}

	public void setPositionLowCargo() {
		setPosition(kPositionLowCargo);
	}

	public void setPositionHighHatch() {
		setPosition(kPositionHighHatch);
	}

	public void setPositionMidHatch() {
		setPosition(kPositionMidHatch);
	}

	public void setPositionLowHatch() {
		setPosition(kPositionLowHatch);
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
//		if (getPosition() > kPositionHighCargo) {
//			setPosition(kPositionHighCargo);
//		} else if (getPosition() < kPositionLowCargo) {
//			setPosition(kPositionLowCargo);
//		}
	}

	@Override
	public void end(double timestamp) {

	}
}

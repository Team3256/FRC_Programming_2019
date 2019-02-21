package frc.team3256.robot.subsystems.elevator;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class Elevator extends SubsystemBase {

	private static Elevator instance;
	private CANSparkMax master, slave;
	private CANPIDController masterPID;
	private CANEncoder masterEncoder;

	private Elevator() {
		master = SparkMAXUtil.generateGenericSparkMAX(kSparkMaxMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        slave = SparkMAXUtil.generateGenericSparkMAX(kSparkMaxSlave, CANSparkMaxLowLevel.MotorType.kBrushless);

		master.setInverted(true);
        slave.follow(master, true);

		masterPID = master.getPIDController();
		masterEncoder = master.getEncoder();

		SparkMAXUtil.setBrakeMode(master, slave);

		SparkMAXUtil.setPIDGains(masterPID, 0, kElevatorP, kElevatorI, kElevatorD, kElevatorF, kElevatorIz);

		masterEncoder.setPosition(0);
	}

	public void resetEncoder() {
		masterEncoder.setPosition(0);
	}

	public static Elevator getInstance() {
		return instance == null ? instance = new Elevator() : instance;
	}

	public void setOpenLoop(double power) {
//		if ((power < 0 && masterEncoder.getPosition() <= 2) || (power > 0 && masterEncoder.getPosition() >= 195)) {
//			master.set(0);
//		} else {
//			master.set(power);
//		}
		master.set(power);
	}

	public void setPosition(double position) {
		masterPID.setReference(position, ControlType.kPosition);
	}

	public double getPosition() {
		return masterEncoder.getPosition();
	}

	@Override
	public void outputToDashboard() {
		SmartDashboard.putNumber("elevatorPositionMaster", getPosition());
		SmartDashboard.putNumber("elevatorPositionSlave", slave.getEncoder().getPosition());

		SmartDashboard.putNumber("elevatorCurrentMaster", master.getOutputCurrent());
		SmartDashboard.putNumber("elevatorCurrentSlave", slave.getOutputCurrent());
	}

	@Override
	public void zeroSensors() {
		masterEncoder.setPosition(0);
	}

	@Override
	public void init(double timestamp) {
		master.setIdleMode(CANSparkMax.IdleMode.kBrake);
		slave.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	@Override
	public void update(double timestamp) {
		this.outputToDashboard();
//		if (getPosition() > kPositionHighCargo) {
//			setPosition(kPositionHighCargo);
//		} else if (getPosition() < kPositionLowCargo) {
//			setPosition(kPositionLowCargo);
//		}
	}

	@Override
	public void end(double timestamp) {

	}

	public void closedLoopUp() {
		if (getPosition() > kElevatorMaxPosition) {
			setPosition(kElevatorMaxPosition);
		} else {
			setPosition(getPosition() + 0.05);
		}
	}

	public void closedLoopDown() {
		if (getPosition() < kElevatorMinPosition) {
			setPosition(0);
		} else {
			setPosition(getPosition() - 0.05);
		}
	}
}

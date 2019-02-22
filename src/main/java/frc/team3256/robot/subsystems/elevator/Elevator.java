package frc.team3256.robot.subsystems.elevator;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import javax.naming.ldap.Control;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class Elevator extends SubsystemBase {

	private static Elevator instance;
	private CANSparkMax master, slave;
	private CANPIDController masterPID;
	private CANEncoder masterEncoder;

	private DigitalInput hallEffect;
	private boolean homed = false;

	private Elevator() {
		hallEffect = new DigitalInput(Constants.kHallEffectPort);
		hallEffect.requestInterrupts(new InterruptHandlerFunction<Elevator>() {
			@Override
			public void interruptFired(int interruptAssertedMask, Elevator param) {
				System.out.println("HOMED");
				masterEncoder.setPosition(0);
				homed = true;
				master.set(0);
			}
		});
		hallEffect.setUpSourceEdge(false, true);
		hallEffect.enableInterrupts();

		master = SparkMAXUtil.generateGenericSparkMAX(kSparkMaxMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        slave = SparkMAXUtil.generateGenericSparkMAX(kSparkMaxSlave, CANSparkMaxLowLevel.MotorType.kBrushless);

		master.setInverted(true);
        slave.follow(master, true);

		masterPID = master.getPIDController();
		masterEncoder = master.getEncoder();

		SparkMAXUtil.setBrakeMode(master, slave);

		SparkMAXUtil.setPIDGains(masterPID, 0, kElevatorP, kElevatorI, kElevatorD, kElevatorF, kElevatorIz);
		SparkMAXUtil.setSmartMotionParams(masterPID, kMinOutputVelocity, kSmartMotionMaxVel, kSmartMotionMaxAccel, kSmartMotionAllowedClosedLoopError, 0);
		masterEncoder.setPosition(0);
	}

	public void resetEncoder() {
		masterEncoder.setPosition(0);
	}

	public static Elevator getInstance() {
		return instance == null ? instance = new Elevator() : instance;
	}

	public void setOpenLoop(double power) {
		master.set(power);
	}

	public void setPosition(double position) {
		//masterPID.setReference(position, ControlType.kPosition);
		masterPID.setReference(position, ControlType.kSmartMotion, 0,0);
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
		SmartDashboard.putBoolean("hallEffect", hallEffect.get());
	}

	public void zeroSensors() {
		master.setEncPosition(0);
	}

	public void init(double timestamp) {
		master.setIdleMode(CANSparkMax.IdleMode.kBrake);
		slave.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public void update(double timestamp) {
		this.outputToDashboard();
	}

	public void end(double timestamp) {

	}

	public void onUp() {
		if (getPosition() < kPositionMax) {
			setPosition(getPosition() + 0.5);
		} else {
			setPosition(kPositionMax);
		}
	}

	public void onDown() {
		if (getPosition() > kPositionMin) {
			setPosition(getPosition() - 0.5);
		} else {
			setPosition(kPositionMin);
		}
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
}

package frc.team3256.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class Elevator extends SubsystemBase {

	private static Elevator instance;
	private CANSparkMax master, slave;
	private CANPIDController masterPID;
	private CANEncoder masterEncoder;
	private double elevatorTarget = 6;

	private DigitalInput hallEffect;
	private boolean homed = false;

	private Elevator() {
		hallEffect = new DigitalInput(kHallEffectPort);

		master = SparkMAXUtil.generateGenericSparkMAX(kSparkMaxMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        slave = SparkMAXUtil.generateGenericSparkMAX(kSparkMaxSlave, CANSparkMaxLowLevel.MotorType.kBrushless);

		master.setInverted(true);
        slave.follow(master, true);

		masterPID = master.getPIDController();
		masterEncoder = master.getEncoder();

		SparkMAXUtil.setBrakeMode(master, slave);

		SparkMAXUtil.setPIDGains(masterPID, 0, kElevatorP, kElevatorI, kElevatorD, kElevatorF, kElevatorIz);
		SparkMAXUtil.setSmartMotionParams(masterPID, kMinOutputVelocity, kSmartMotionMaxVel, kSmartMotionMaxAccel, kSmartMotionAllowedClosedLoopError, 0);
        master.setEncPosition(0);
	}

	public void resetEncoder() {
        master.setEncPosition(0);
	}

	public static Elevator getInstance() {
		return instance == null ? instance = new Elevator() : instance;
	}

	public void setOpenLoop(double power) {
		master.set(power);
		slave.set(-power);
	}

	public void setPosition(double position) {
		//masterPID.setReference(position, ControlType.kPosition);
		elevatorTarget = 6;
        masterPID.setReference(position, ControlType.kSmartMotion, 0);
	}

	public void setPositionInches(double inches) {
		elevatorTarget = inches;
		masterPID.setReference(inchesToRotations(inches), ControlType.kSmartMotion);
	}

	public double getPosition() {
		return masterEncoder.getPosition();
	}

	@Override
	public void outputToDashboard() {
		SmartDashboard.putNumber("ElevatorPositionMaster", getPosition());
        SmartDashboard.putNumber("ElevatorSpeed", master.getEncoder().getVelocity());
		SmartDashboard.putNumber("ElevatorPositionSlave", slave.getEncoder().getPosition());

		SmartDashboard.putNumber("ElevatorCurrent", master.getOutputCurrent());
		SmartDashboard.putNumber("ElevatorCurrentSlave", slave.getOutputCurrent());
		SmartDashboard.putBoolean("HallEffect", getHallEffectTriggered());
		SmartDashboard.putNumber("SpoolInches", rotationToInches(getPosition()));
    }

    public boolean getHallEffectTriggered() {
        return !hallEffect.get();
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
	
	public void setPositionHighCargo() {
		setPositionInches(kPositionHighCargo);
	}

	public void setPositionMidCargo() {
		setPositionInches(kPositionMidCargo);
	}

	public void setPositionLowCargo() {
		setPositionInches(kPositionLowCargo);
	}
	
	public void setPositionHighHatch() {
		setPositionInches(kPositionHighHatch);
	}

	public void setPositionMidHatch() {
		setPositionInches(kPositionMidHatch);
	}

	public void setPositionLowHatch() {
		setPositionInches(kPositionLowHatch);
	}

	public void setPositionIntakeHatch() {
		setPositionInches(kHatchHumanPlayerPosition);
	}

	public void setPositionHookHatch() {
		setPositionInches(elevatorTarget + kHookOffset);
	}

	public void setPositionUnhookHatch() {
		setPositionInches(elevatorTarget - kUnhookOffset);
	}

	public void setPositionHome() {
		setPosition(0);
	}

	public double rotationToInches(double rotations) {
		return (((rotations * kElevatorGearRatio) * kElevatorSpoolSize * Math.PI) + 6.0);
	}

	public double inchesToRotations(double inches) {
		return (inches - 6.0) / Math.PI / kElevatorGearRatio / kElevatorSpoolSize;
	}

	// Distances from ground
	// 19.0 - distance to center of hatch human player
	// 24.0 - distance to where we need to lift up to hook hatch on arm from human player
}

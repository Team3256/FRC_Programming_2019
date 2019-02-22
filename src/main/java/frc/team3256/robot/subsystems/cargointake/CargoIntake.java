package frc.team3256.robot.subsystems.cargointake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

	private double previousOutputCurrent = 0.0;

	private boolean canIntake = true;
	private double checkForBallAfter = 0.0;

	private CargoIntake() {
		cargoIntake = TalonSRXUtil.generateGenericTalon(kIntake);
		cargoIntake.setInverted(true);
		cargoPivot = SparkMAXUtil.generateGenericSparkMAX(kPivot, CANSparkMaxLowLevel.MotorType.kBrushless);
		SparkMAXUtil.setBrakeMode(cargoPivot);
		cargoPID = cargoPivot.getPIDController();
		cargoEncoder = cargoPivot.getEncoder();
		cargoEncoder.setPosition(0);

		SparkMAXUtil.setPIDGains(cargoPID, 0, kPivotP, kPivotI, kPivotD, kPivotF, kPivotIz);
		cargoPID.setOutputRange(kPivotMinOutput, kPivotMaxOutput);
	}

	@Override
	public void init(double timestamp) {
		SparkMAXUtil.setBrakeMode(cargoPivot);
		cargoEncoder.setPosition(0);
	}

	public static CargoIntake getInstance() {
		return instance == null ? instance = new CargoIntake() : instance;
	}

	public double getCargoEncoderPosition() {
	    return cargoEncoder.getPosition();
    }

	public void intake() {
		this.setIntakePower(kIntakeSpeed);
		checkForBallAfter = Timer.getFPGATimestamp() + 0.2;
	}

	public void exhaust() {
		this.setIntakePower(-kIntakeSpeed);
	}

	public void stop() {
		this.setIntakePower(0);
	}

	@Override
	public void update(double timestamp) {
	    SmartDashboard.putNumber("CargoEncoder", cargoEncoder.getPosition());
		SmartDashboard.putNumber("CargoOutputCurrent", cargoIntake.getOutputCurrent());
		SmartDashboard.putNumber("CargoBusVoltage", cargoIntake.getBusVoltage());
		SmartDashboard.putNumber("VoltageChange", (cargoIntake.getOutputCurrent() - previousOutputCurrent ) / (15 - 0));
		SmartDashboard.putNumber("CheckForBallAfter", checkForBallAfter);

		if (!canIntake) {
		    return;
        }

		if (checkForBallAfter != -1 && Timer.getFPGATimestamp() > checkForBallAfter && cargoIntake.getOutputCurrent() > 3.0 && cargoIntake.getOutputCurrent() < 5.0) {
			SmartDashboard.putBoolean("BallTime", true);
			this.stop();
			checkForBallAfter = -1;
		} else {
			SmartDashboard.putBoolean("BallTime", false);
		}

		previousOutputCurrent = cargoIntake.getOutputCurrent();
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
		cargoEncoder.setPosition(0);
	}

	@Override
	public void end(double timestamp) {
	    SparkMAXUtil.setCoastMode(cargoPivot);
	}

	public void setPivotFloorPosition() {
	}

	public void setPivotTransferPosition() {

	}

	public void setPivotFoldInPosition() {

	}

	public double getPivotCurrent() {
		return cargoPivot.getOutputCurrent();
	}

	public void setPivotBrake() {
		SparkMAXUtil.setBrakeMode(cargoPivot);
	}

	public void setPivotCoast() {
		SparkMAXUtil.setCoastMode(cargoPivot);
	}
}

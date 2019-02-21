package frc.team3256.robot.subsystems.cargointake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.*;
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
	private boolean intaking = false;
	private boolean exhausting = false;
	private double previousRequestedPower = 0.0;
	private double requestedIntakePower = 0.0;

	private boolean canIntake = true;

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

	public static CargoIntake getInstance() {
		return instance == null ? instance = new CargoIntake() : instance;
	}

	public double getCargoEncoderPosition() {
	    return cargoEncoder.getPosition();
    }

	public void intake() {
		if (!intaking) {
			intaking = true;
			requestedIntakePower = kIntakeSpeed;
			if (!this.checkForBall) {
				Thread thread = new Thread(() -> {
					try {
						Thread.sleep(500);
						this.checkForBall = true;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
				thread.start();
			}
		} else if (exhausting) {
			exhausting = false;
			requestedIntakePower = 0;
		}
	}

	public void stop() {
		intaking = false;
		exhausting = false;
		checkForBall = false;
		requestedIntakePower = 0;
	}

	public void exhaust() {
		if (!exhausting) {
			exhausting = true;
			requestedIntakePower = -1;
		}  else if (intaking) {
			intaking = false;
			stop();
		}
	}

	private boolean checkForBall = false;

	@Override
	public void update(double timestamp) {
	    SmartDashboard.putNumber("CargoEncoder", cargoEncoder.getPosition());
		SmartDashboard.putNumber("CargoOutputCurrent", cargoIntake.getOutputCurrent());
		SmartDashboard.putNumber("CargoBusVoltage", cargoIntake.getBusVoltage());
		SmartDashboard.putNumber("VoltageChange", (cargoIntake.getOutputCurrent() - previousOutputCurrent ) / (15 - 0));
		SmartDashboard.putBoolean("CheckForBall", checkForBall);

        SmartDashboard.putNumber("RequestedIntakePower", requestedIntakePower);
		if (!canIntake) {
		    return;
        }

		if (checkForBall && cargoIntake.getOutputCurrent() > 3.0 && cargoIntake.getOutputCurrent() < 5.0) {
			SmartDashboard.putBoolean("BallTime", true);
			this.stop();
			checkForBall = false;
		} else {
			SmartDashboard.putBoolean("BallTime", false);
		}

		previousOutputCurrent = cargoIntake.getOutputCurrent();
		cargoIntake.set(requestedIntakePower);
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
	public void init(double timestamp) {
        SparkMAXUtil.setBrakeMode(cargoPivot);
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

	public boolean isIntaking() {
		return intaking;
	}

	public boolean isExhausting() {
		return exhausting;
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

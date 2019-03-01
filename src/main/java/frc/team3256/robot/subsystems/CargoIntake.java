package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.CargoConstants.*;

public class CargoIntake extends SubsystemBase {

	private static CargoIntake instance;
	private WPI_TalonSRX cargoIntakeLeft, cargoIntakeRight;

	private double previousOutputCurrent = 0.0;

	private double checkForBallAfter = 0.0;

	private CargoIntake() {
		cargoIntakeLeft = TalonSRXUtil.generateGenericTalon(kIntake);
		cargoIntakeLeft.setInverted(false);

		cargoIntakeRight = TalonSRXUtil.generateGenericTalon(kIntakeSlave);
		cargoIntakeRight.setInverted(true);
	}

	@Override
	public void init(double timestamp) {
	}

	public static CargoIntake getInstance() {
		return instance == null ? instance = new CargoIntake() : instance;
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
		SmartDashboard.putNumber("CheckForBallAfter", checkForBallAfter);
		SmartDashboard.putNumber("CargoOutputCurrent", cargoIntakeLeft.getOutputCurrent());
		SmartDashboard.putNumber("CargoBusVoltage", cargoIntakeLeft.getBusVoltage());
		SmartDashboard.putNumber("CargoVoltageChange", (cargoIntakeLeft.getOutputCurrent() - previousOutputCurrent ) / (15 - 0));

		if (
				(checkForBallAfter != -1) &&
				(Timer.getFPGATimestamp() > checkForBallAfter) &&
				(cargoIntakeLeft.getOutputCurrent() > 1.0)
		) {
			SmartDashboard.putBoolean("BallTime", true);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					CargoIntake.getInstance().stop();
				}
			});
			thread.start();
			checkForBallAfter = -1;
		} else {
			SmartDashboard.putBoolean("BallTime", false);
		}

		previousOutputCurrent = cargoIntakeLeft.getOutputCurrent();
	}

	public void setIntakePower(double power) {
		cargoIntakeLeft.set(power);
		cargoIntakeRight.set(power);
	}

	@Override
	public void outputToDashboard() {

	}

	@Override
	public void zeroSensors() {
	}

	@Override
	public void end(double timestamp) {
	}
}

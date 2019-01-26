package frc.team3256.robot.operations;

public class PIDController {
	double kP, kI, kD, P, I, D, error, prevError, sumError;

	public PIDController(double kP, double kI, double kD) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}

	public void resetPID() {
		error = 0;
		prevError = 0;
		sumError = 0;
	}

	public double getError(double setpoint, double currentPosition) {
		return Math.abs(setpoint - currentPosition);
	}

	public double calculatePID(double setpoint, double currentPosition) {
		error = setpoint - currentPosition;
		sumError += error;

		P = error * kP;
		I = sumError * kI;
		D = (error - prevError) * kD;

		prevError = error;

		return P + I + D;
	}
}

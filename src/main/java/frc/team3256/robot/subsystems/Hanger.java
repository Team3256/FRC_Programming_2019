package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class Hanger extends SubsystemBase {
	private static Hanger instance;
	RobotState robotState = new IdleState();
	private DoubleSolenoid leftHang, rightHang;

	private Hanger() {
		leftHang = new DoubleSolenoid(Constants.kHangerLeftForward, Constants.kHangerLeftReverse);
		rightHang = new DoubleSolenoid(Constants.kHangerRightForward, Constants.kHangerRightReverse);
	}

	public static Hanger getInstance() {
		return instance == null ? instance = new Hanger() : instance;
	}

	@Override
	public void update(double timestamp) {
		frc.team3256.warriorlib.state.RobotState newState = robotState.update();
		if (!robotState.equals(newState)) {
			System.out.println(String.format("(%s) STATE CHANGE: %s -> %s", getClass().getSimpleName(), robotState.getClass().getSimpleName(), newState.getClass().getSimpleName()));
		}
		robotState = newState;
	}

	private void hang() {
		leftHang.set(DoubleSolenoid.Value.kForward);
		rightHang.set(DoubleSolenoid.Value.kForward);
	}

	private void retract() {
		leftHang.close();
		rightHang.close();
	}

	public void setRobotState(RobotState state) {
		this.robotState = state;
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
	public void end(double timestamp) {

	}

	public static class HangState extends RobotState {
		@Override
		public RobotState update() {
			Hanger.getInstance().hang();
			return new IdleState();
		}
	}

	public static class RetractState extends RobotState {

		@Override
		public RobotState update() {
			Hanger.getInstance().retract();
			return new IdleState();
		}
	}

	public static class IdleState extends RobotState {

		@Override
		public RobotState update() {
			return new IdleState();
		}
	}
}

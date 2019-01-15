package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class CargoIntake extends SubsystemBase {

    XboxController xboxController = new XboxController(0);

    private VictorSP cargoIntake;

    private double kIntakePower = Constants.kCargoIntakePower;
    private double kExhaustPower = Constants.kCargoExhaustPower;

    private CargoIntake() {
        cargoIntake = new VictorSP(Constants.kCargoIntakePort);
        cargoIntake.setInverted(false); //TBD
    }

    class IntakingState extends RobotState {
        @Override
        public RobotState update() {
            cargoIntake.set(kIntakePower);

            // Perhaps read sensor here and stop intaking

            return new UpdateControlsState();
        }
    }

    class ExhaustingState extends RobotState {
        @Override
        public RobotState update() {
            cargoIntake.set(kExhaustPower);
            return new UpdateControlsState();
        }
    }

    class IdleState extends RobotState {
        @Override
        public RobotState update() {
            cargoIntake.set(0);
            return new UpdateControlsState();
        }
    }

    class UpdateControlsState extends RobotState {
        @Override
        public RobotState update() {
            // Set new states based on controls from controller
            // e.g. on A held down, intake

            return new UpdateControlsState();
        }
    }

    private RobotState robotState = new IdleState();

    @Override
    public void update(double timestamp) {
        robotState = robotState.update();
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
}

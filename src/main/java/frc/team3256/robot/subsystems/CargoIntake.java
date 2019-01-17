package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import frc.team3256.robot.operation.DriveConfigImplementation;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class CargoIntake extends SubsystemBase{

    XboxController xboxController = new XboxController(0);

    private VictorSP cargoIntake;

    private double kIntakePower = Constants.kCargoIntakePower;
    private double kExhaustPower = Constants.kCargoExhaustPower;

    private static CargoIntake instance;
    public static CargoIntake getInstance () {return instance == null ? instance = new CargoIntake(): instance;}

    DriveConfigImplementation driveConfigImplementation = new DriveConfigImplementation();

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

            if(driveConfigImplementation.getCargoIntake()){
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                return new IntakingState();
            }
            else if (driveConfigImplementation.getCargoExhaust()){
                return new ExhaustingState();
            }
            else {return new IdleState();}
        }
    }

    private RobotState robotState = new UpdateControlsState();

    @Override
    public void update(double timestamp) {
        RobotState newState = robotState.update();
        if (!robotState.equals(newState)) {
            System.out.println(String.format(
                    "(%s) STATE CHANGE: %s -> %s",
                    getClass().getSimpleName(),
                    robotState.getClass().getSimpleName(),
                    newState.getClass().getSimpleName()
            ));
        }
        robotState = newState;
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

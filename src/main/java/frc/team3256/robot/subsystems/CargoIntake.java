package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class CargoIntake extends SubsystemBase{

    private WPI_TalonSRX cargoIntake, cargoScoreLeft, cargoScoreRight, cargoClearance;
    private CANSparkMax cargoPivot;

    private static CargoIntake instance;
    public static CargoIntake getInstance () {return instance == null ? instance = new CargoIntake(): instance;}

    private CargoIntake() {
        cargoIntake = TalonSRXUtil.generateGenericTalon(Constants.kCargoIntakePort);
        cargoScoreLeft = TalonSRXUtil.generateGenericTalon(Constants.kCargoScoreLeftPort);
        cargoScoreRight = TalonSRXUtil.generateGenericTalon(Constants.kCargoScoreRightPort);
        cargoClearance = TalonSRXUtil.generateGenericTalon(Constants.kCargoClearancePort);
        cargoPivot = new CANSparkMax(Constants.kCargoPivotPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        cargoIntake.setInverted(false); //TBD
    }

    public static class IntakingState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().cargoIntake.set(Constants.kCargoIntakePower);

            // Perhaps read sensor here and stop intaking

            return new IdleState();
        }
    }

    public static class ExhaustingState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().cargoIntake.set(Constants.kCargoExhaustPower);
            return new IdleState();
        }
    }

    public static class ScoringState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().setScore(Constants.kCargoScorePower, Constants.kCargoScorePower);
            return new IdleState();
        }
    }

    public static class PivotingUpState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().cargoPivot.set(Constants.kCargoPivotUpPower);
            return new IdleState();
        }
    }

    public static class PivotingDownState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().cargoPivot.set(Constants.kCargoPivotDownPower);
            return new IdleState();
        }
    }

    public static class ClearanceUpState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().cargoClearance.set(Constants.kCargoClearanceUpPower);
            return new IdleState();
        }
    }

    public static class ClearanceDownState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().cargoClearance.set(Constants.kCargoClearanceDownPower);
            return new IdleState();
        }
    }

    public static class IdleState extends RobotState {
        @Override
        public RobotState update() {
            CargoIntake.getInstance().cargoIntake.set(0);
            return new IdleState();
        }
    }

    private RobotState robotState = new IdleState();

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

    public void setRobotState(RobotState state){
        this.robotState = state;
    }

    public void setScore(double left, double right) {
        cargoScoreLeft.set(left);
        cargoScoreRight.set(right);
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

package frc.team3256.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class Elevator extends SubsystemBase {

    private DigitalInput hallEffect;
    private CANSparkMax master, slaveOne, slaveTwo, slaveThree;

    private static Elevator instance;
    public static Elevator getInstance(){
        return instance == null ? instance = new Elevator() : instance;
    }

    private Elevator(){
        hallEffect = new DigitalInput(Constants.kHallEffectPort);


    }

    public static class ManualUpState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class ManualDownState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class HighCargoPresetState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class MidCargoPresetState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class LowCargoPresetState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class HighHatchPresetState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class MidHatchPresetState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class LowHatchPresetState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    public static class HoldState extends RobotState{

        @Override
        public RobotState update() {
            return null;
        }
    }

    RobotState robotState = new HoldState();

    public void setRobotState(RobotState state){
        this.robotState = state;
    }


    //4096 = # of ticks per revolution | 4 X decoding for Talon SRX & Canifier only
    private double heightToSensorUnits(double inches) {
        return (inches/Constants.kElevatorPulleyDiameter*Constants.kMagEncoderTicksTalon*Constants.kElevatorGearRatio);
    }// MagEncoder Ticks are placeholder for now.

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
    public void update(double timestamp) {

    }

    @Override
    public void end(double timestamp) {

    }
}

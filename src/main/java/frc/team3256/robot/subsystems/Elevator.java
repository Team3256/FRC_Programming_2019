package frc.team3256.robot.subsystems;

import com.revrobotics.*;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class Elevator extends SubsystemBase {

    private CANSparkMax master, slaveOne, slaveTwo, slaveThree;
    private CANPIDController masterPID;
    private CANEncoder masterEncoder;

    private static Elevator instance;
    public static Elevator getInstance(){
        return instance == null ? instance = new Elevator() : instance;
    }

    private Elevator(){
        master = SparkMAXUtil.generateGenericSparkMAX(Constants.kElevatorMaster,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        slaveOne = SparkMAXUtil.generateSlaveSparkMAX(Constants.kElevatorSlaveOne,
                CANSparkMaxLowLevel.MotorType.kBrushless, master);
        slaveTwo = SparkMAXUtil.generateSlaveSparkMAX(Constants.kElevatorSlaveTwo,
                CANSparkMaxLowLevel.MotorType.kBrushless, master);
        slaveThree = SparkMAXUtil.generateSlaveSparkMAX(Constants.kElevatorSlaveThree,
                CANSparkMaxLowLevel.MotorType.kBrushless, master);
        masterPID = master.getPIDController();
        masterEncoder = master.getEncoder();

        SparkMAXUtil.setCoastMode(master, slaveOne, slaveTwo, slaveThree);

        SparkMAXUtil.setPIDGains(masterPID, Constants.kElevatorHoldSlot, Constants.kElevatorHoldP,
                Constants.kElevatorHoldI, Constants.kElevatorHoldD, Constants.kElevatorHoldF,
                Constants.kElevatorHoldIz);

        SparkMAXUtil.setPIDGains(masterPID, Constants.kElevatorUpSlot, Constants.kElevatorUpP,
                Constants.kElevatorUpI, Constants.kElevatorUpD, Constants.kElevatorUpF,
                Constants.kElevatorUpIz);

        SparkMAXUtil.setPIDGains(masterPID, Constants.kElevatorDownSlot, Constants.kElevatorDownP,
                Constants.kElevatorDownI, Constants.kElevatorDownD, Constants.kElevatorDownF,
                Constants.kElevatorDownIz);

        masterPID.setOutputRange(Constants.kElevatorMinOutput, Constants.kElevatorMaxOutput);
    }

    private void setOpenLoop(double power){
        master.set(power);
    }

    private void setPosition(double position, int slot){
        masterPID.setReference(position, ControlType.kPosition, slot);
    }

    private double getPosition(){
        return masterEncoder.getPosition();
    }

    public static class ManualUpState extends RobotState{

        @Override
        public RobotState update() {
            Elevator.getInstance().setOpenLoop(Constants.kElevatorUpManualPower);
            return new HoldState();
        }
    }

    public static class ManualDownState extends RobotState{

        @Override
        public RobotState update() {
            Elevator.getInstance().setOpenLoop(Constants.kElevatorDownManualPower);
            return new HoldState();
        }
    }

    public static class HighCargoPresetState extends RobotState{

        @Override
        public RobotState update() {
            if (Elevator.getInstance().getPosition() > Constants.kHighCargoPreset){
                Elevator.getInstance().setPosition(Constants.kHighCargoPreset, Constants.kElevatorDownSlot);
                return new HoldState();
            }
            else if (Elevator.getInstance().getPosition() < Constants.kHighCargoPreset){
                Elevator.getInstance().setPosition(Constants.kHighCargoPreset, Constants.kElevatorUpSlot);
                return new HoldState();
            }
            else return new HoldState();
        }
    }

    public static class MidCargoPresetState extends RobotState {

        @Override
        public RobotState update() {
            if (Elevator.getInstance().getPosition() > Constants.kMidCargoPreset) {
                Elevator.getInstance().setPosition(Constants.kMidCargoPreset, Constants.kElevatorDownSlot);
                return new HoldState();
            }
            else if (Elevator.getInstance().getPosition() < Constants.kMidCargoPreset) {
                Elevator.getInstance().setPosition(Constants.kMidCargoPreset, Constants.kElevatorUpSlot);
                return new HoldState();
            }
            else return new HoldState();
        }
    }

    public static class LowCargoPresetState extends RobotState{

        @Override
        public RobotState update() {
            if (Elevator.getInstance().getPosition() > Constants.kLowCargoPreset){
                Elevator.getInstance().setPosition(Constants.kLowCargoPreset, Constants.kElevatorDownSlot);
                return new HoldState();
            }
            else if (Elevator.getInstance().getPosition() < Constants.kLowCargoPreset){
                Elevator.getInstance().setPosition(Constants.kLowCargoPreset, Constants.kElevatorUpSlot);
                return new HoldState();
            }
            else return new HoldState();
        }
    }

    public static class HighHatchPresetState extends RobotState{

        @Override
        public RobotState update() {
            if (Elevator.getInstance().getPosition() > Constants.kHighHatchPreset){
                Elevator.getInstance().setPosition(Constants.kHighHatchPreset, Constants.kElevatorDownSlot);
                return new HoldState();
            }
            else if (Elevator.getInstance().getPosition() < Constants.kHighHatchPreset){
                Elevator.getInstance().setPosition(Constants.kHighHatchPreset, Constants.kElevatorUpSlot);
                return new HoldState();
            }
            else return new HoldState();
        }
    }

    public static class MidHatchPresetState extends RobotState{

        @Override
        public RobotState update() {
            if (Elevator.getInstance().getPosition() > Constants.kMidHatchPreset){
                Elevator.getInstance().setPosition(Constants.kMidHatchPreset, Constants.kElevatorDownSlot);
                return new HoldState();
            }
            else if (Elevator.getInstance().getPosition() < Constants.kMidHatchPreset){
                Elevator.getInstance().setPosition(Constants.kMidHatchPreset, Constants.kElevatorUpSlot);
                return new HoldState();
            }
            else return new HoldState();
        }
    }

    public static class LowHatchPresetState extends RobotState{

        @Override
        public RobotState update() {
            if (Elevator.getInstance().getPosition() > Constants.kLowHatchPreset){
                Elevator.getInstance().setPosition(Constants.kLowHatchPreset, Constants.kElevatorDownSlot);
                return new HoldState();
            }
            else if (Elevator.getInstance().getPosition() < Constants.kLowHatchPreset){
                Elevator.getInstance().setPosition(Constants.kLowHatchPreset, Constants.kElevatorUpSlot);
                return new HoldState();
            }
            else return new HoldState();
        }
    }

    public static class HoldState extends RobotState{

        @Override
        public RobotState update() {
            if(Elevator.getInstance().getPosition() > Constants.kDropPreset) {
                Elevator.getInstance().setPosition(Elevator.getInstance().getPosition(), Constants.kElevatorHoldSlot);
                return new HoldState();
            }
            else return new IdleState();
        }
    }

    public static class IdleState extends RobotState {
        @Override
        public RobotState update() {
            Elevator.getInstance().setOpenLoop(0);
            return new IdleState();
        }
    }

    RobotState robotState = new HoldState();

    public void setRobotState(RobotState state){
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
    public void update(double timestamp) {

    }

    @Override
    public void end(double timestamp) {

    }
}

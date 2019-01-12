package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class Elevator extends SubsystemBase {

    private WPI_TalonSRX master, slave;
    private DigitalInput hallEffect;

    private SystemState currentState = SystemState.HOLD;
    private WantedState wantedState = WantedState.WANTS_TO_HOLD;
    private WantedState prevWantedState = WantedState.WANTS_TO_HOLD;

    private boolean isHomed = false;
    private boolean stateChanged;
    private boolean targetReached = false;
    private boolean wantedStateChanged = false;

    private double m_closedLoopTarget;
    private boolean m_usingClosedLoop;

    private double homingTimeStart;

    private static Elevator instance;

    public static Elevator getInstance() {
        return instance == null ? instance = new Elevator() : instance;
    }

    private InterruptHandlerFunction<Elevator> ihr = new InterruptHandlerFunction<Elevator>() {

        @Override
        public void interruptFired(int interruptAssertedMask, Elevator param) {
            if (master.getSelectedSensorVelocity(0) < 0){
                System.out.println("HOMED ON UPPER SIDE OF CROSSBAR!!!!");
                master.setSelectedSensorPosition((int)heightToSensorUnits(Constants.kCompHomeHeight), 0, 0);
                isHomed = true;
                master.configForwardSoftLimitEnable(true, 0);
                master.configReverseSoftLimitEnable(true, 0);
            }
            else if (master.getSelectedSensorVelocity(0) > 0){
                System.out.println("HOMED ON LOWER SIDE OF CROSSBAR!!!!");
                master.setSelectedSensorPosition((int)heightToSensorUnits(Constants.kCompBottomHomeHeight), 0, 0);
                isHomed = true;
                master.configForwardSoftLimitEnable(true, 0);
                master.configReverseSoftLimitEnable(true, 0);
            }
        }
    };

    private Elevator() {
        hallEffect = new DigitalInput(Constants.kHallEffectPort);
        hallEffect.requestInterrupts(ihr);
        hallEffect.setUpSourceEdge(false, true);
        hallEffect.enableInterrupts();

        master = TalonSRXUtil.generateGenericTalon(Constants.kElevatorMaster);
        slave = TalonSRXUtil.generateSlaveTalon(Constants.kElevatorSlave, Constants.kElevatorMaster);


        TalonSRXUtil.configMagEncoder(master);
        master.setSelectedSensorPosition((int)(heightToSensorUnits(7.5)), 0, 0);

        //configure Hold PID values
        TalonSRXUtil.setPIDGains(master, Constants.kElevatorHoldSlot, Constants.kElevatorHoldP,
                Constants.kElevatorHoldI, Constants.kElevatorHoldD, 0);

        //configure FastUp PID values
        TalonSRXUtil.setPIDGains(master, Constants.kElevatorMotionMagicUpSlot, Constants.kElevatorMotionMagicUpP,
                Constants.kElevatorMotionMagicUpI, Constants.kElevatorMotionMagicUpD, Constants.kElevatorMotionMagicUpF);

        //configure FastDown PID values
        TalonSRXUtil.setPIDGains(master, Constants.kElevatorMotionMagicDownSlot, Constants.kElevatorMotionMagicDownP,
                Constants.kElevatorMotionMagicDownI, Constants.kElevatorMotionMagicDownD, Constants.kElevatorMotionMagicDownF);

        //voltage limiting
        TalonSRXUtil.setPeakOutput(Constants.kElevatorMaxUpVoltage/12.0,
                Constants.kElevatorMaxDownVoltage/12.0, master, slave);
        TalonSRXUtil.setMinOutput(Constants.kElevatorMinHoldVoltage/12.0,
                0, master, slave);

        //soft limits
        master.configForwardSoftLimitThreshold((int)(heightToSensorUnits(Constants.kElevatorMaxHeight)), 0);
        master.configReverseSoftLimitThreshold((int)(heightToSensorUnits(Constants.kElevatorMinHeight)), 0);
        master.configForwardSoftLimitEnable(false, 0);
        master.configReverseSoftLimitEnable(false,0);

        TalonSRXUtil.setCoastMode(master, slave);
    }

    private void setOpenLoop(double power){
        master.set(ControlMode.PercentOutput, power);
    }

    public WantedState getWantedState() {
        return wantedState;
    }

    public SystemState getCurrentState() {
        return currentState;
    }

    private void setTargetPosition(ControlMode mode, double targetHeight, int slotID){
        if (!isHomed)return;
        if (stateChanged){
            master.selectProfileSlot(slotID,0);
        }
        master.set(mode, (int)heightToSensorUnits(targetHeight), 0);
    }

    private enum SystemState {
        CLOSED_LOOP_UP,
        CLOSED_LOOP_DOWN,
        HOLD,
        MANUAL_UP,
        MANUAL_DOWN,
        HOMING,
    }

    public enum WantedState {
        WANTS_TO_HIGH_HATCH_POS,
        WANTS_TO_MID_HATCH_POS,
        WANTS_TO_LOW_HATCH_POS,
        WANTS_TO_HIGH_CARGO_POS,
        WANTS_TO_MID_CARGO_POS,
        WANTS_TO_LOW_CARGO_POS,
        WANTS_TO_MANUAL_UP,
        WANTS_TO_MANUAL_DOWN,
        WANTS_TO_HOLD,
        WANTS_TO_HOME
    }

    @Override
    public void init(double timestamp) {
        currentState = SystemState.HOLD;
    }

    @Override
    public void update(double timestamp) {
        if (prevWantedState != wantedState){
            wantedStateChanged = true;
            prevWantedState = wantedState;
        }
        else wantedStateChanged = false;
        if (wantedStateChanged){
            targetReached = false;
        }
        SystemState newState = SystemState.HOLD;

        switch(currentState){
            case HOLD:
                newState = handleHold();
                break;
            case HOMING:
                break;
            case MANUAL_UP:
                break;
            case MANUAL_DOWN:
                break;
            case CLOSED_LOOP_UP:
                break;
            case CLOSED_LOOP_DOWN:
                break;
        }
        //State Transfer
        if(newState != currentState){
            currentState = newState;
            stateChanged = true;
        }
        else stateChanged = false;
    }

    private SystemState handleHold(){

        if (stateChanged){
            master.selectProfileSlot(Constants.kElevatorHoldSlot,0);
        }
        if (getHeight() < Constants.kDropPreset){
            setOpenLoop(0);
            return defaultStateTransfer();
        }
        setTargetPosition(ControlMode.Position, getHeight(), Constants.kElevatorHoldSlot);
        return defaultStateTransfer();
    }

<<<<<<< HEAD
    /*private SystemState handleHome(){

    }*/
=======
//    private SystemState handleHome(){
//
//    }
>>>>>>> e76c19993a22f45cbba9877fd9084474891c0aad

    private SystemState defaultStateTransfer(){
        SystemState rv;
        switch(wantedState){
            case WANTS_TO_HIGH_HATCH_POS:
                if (stateChanged){
                    m_closedLoopTarget = Constants.kHighHatchPreset;
                }
                m_usingClosedLoop = true;
                break;
            case WANTS_TO_MID_HATCH_POS:
                if (stateChanged){
                    m_closedLoopTarget = Constants.kMidHatchPreset;
                }
                m_usingClosedLoop = true;
                break;
            case WANTS_TO_LOW_HATCH_POS:
                if (stateChanged){
                    m_closedLoopTarget = Constants.kLowHatchPreset;
                }
                m_usingClosedLoop = true;
                break;
            case WANTS_TO_HIGH_CARGO_POS:
                if (stateChanged){
                    m_closedLoopTarget = Constants.kHighCargoPreset;
                }
                m_usingClosedLoop = true;
                break;
            case WANTS_TO_MID_CARGO_POS:
                if (stateChanged){
                    m_closedLoopTarget = Constants.kMidCargoPreset;
                }
                m_usingClosedLoop = true;
                break;
            case WANTS_TO_LOW_CARGO_POS:
                if (stateChanged){
                    m_closedLoopTarget = Constants.kLowCargoPreset;
                }
                m_usingClosedLoop = true;
                break;
            case WANTS_TO_HOLD:
                m_usingClosedLoop = false;
                return SystemState.HOLD;
            case WANTS_TO_MANUAL_UP:
                m_usingClosedLoop = false;
                return SystemState.MANUAL_UP;
            case WANTS_TO_MANUAL_DOWN:
                m_usingClosedLoop = false;
                return SystemState.MANUAL_UP;
            case WANTS_TO_HOME:
                return SystemState.HOMING;
        }

        if(m_closedLoopTarget > getHeight() && m_usingClosedLoop) {
            rv = SystemState.CLOSED_LOOP_UP;
        }
        else if (m_closedLoopTarget < getHeight() && m_usingClosedLoop){
            rv = SystemState.CLOSED_LOOP_DOWN;
        }
        else rv = SystemState.HOLD;
        return rv;
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

    public boolean isHomed(){
        return isHomed;
    }

    public boolean isTriggered(){
        return !hallEffect.get();
    }

    public boolean atClosedLoopTarget(){
        if (!m_usingClosedLoop || wantedStateChanged || stateChanged) return false;
        return (Math.abs(getHeight() - m_closedLoopTarget) < Constants.kElevatorTolerance);
    }

    public double getHeight() {
        return sensorUnitsToHeight(master.getSelectedSensorPosition(0));
    }

    private double heightToSensorUnits(double inches) {
        return (inches/Constants.kElevatorPulleyDiameter)*4096.0*Constants.kElevatorGearRatio;
    }

    private double velocityToSensorUnits(double inchesPerSec){
        return heightToSensorUnits(inchesPerSec)/10.0;
    }

    private double accelerationToSensorUnits(double inchesPerSec2){
        return velocityToSensorUnits(inchesPerSec2)/10.0;
    }

    private double sensorUnitsToHeight(double ticks) {
        return (ticks/4096.0)*Constants.kElevatorPulleyDiameter/Constants.kElevatorGearRatio;
    }

}

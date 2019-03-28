package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.CargoConstants.*;

public class NewCargoIntake extends SubsystemBase {

    private WPI_TalonSRX mCargoIntakeLeft, mCargoIntakeRight;

    private double startTime;
    WantedState mPrevWantedState;
    boolean mStateChanged;
    boolean mWantedStateChanged;

    private enum SystemState {
        INTAKING,
        EXHAUSTING,
        STOP,
        CLIMB_DRIVING
    }

    public enum WantedState {
        WANTS_TO_INTAKE,
        WANTS_TO_EXHAUST,
        WANTS_TO_STOP,
        WANTS_TO_CLIMB_DRIVE
    }

    private SystemState mCurrentState = SystemState.STOP;
    private WantedState mWantedState = WantedState.WANTS_TO_STOP;

    private static NewCargoIntake instance;

    public static NewCargoIntake getInstance() {
        return instance == null ? instance = new NewCargoIntake() : instance;
    }

    private NewPivot mPivot = NewPivot.getInstance();

    private NewCargoIntake () {
        mCargoIntakeLeft = TalonSRXUtil.generateGenericTalon(kIntake);
        mCargoIntakeRight = TalonSRXUtil.generateSlaveTalon(kIntakeSlave, kIntake);

        mCargoIntakeLeft.setInverted(false);
        mCargoIntakeRight.setInverted(false);
    }

    public void setWantedState(WantedState wantedState) { this.mWantedState = wantedState; }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Cargo Left", mCargoIntakeLeft.getOutputCurrent());
        SmartDashboard.putNumber("Cargo Right", mCargoIntakeRight.getOutputCurrent());
        SmartDashboard.putString("Cargo State", mWantedState.name());
        SmartDashboard.putString("Cargo State", mCurrentState.name());
        SmartDashboard.putNumber("Start Time", startTime);
        SmartDashboard.putNumber("Current Time", Timer.getFPGATimestamp());
        SmartDashboard.putBoolean("Has Ball", hasBall());
    }

    @Override
    public void zeroSensors() { }

    @Override
    public void init(double timestamp) { }

    @Override
    public void update(double timestamp) {
        if (mPrevWantedState != mWantedState){
            mWantedStateChanged = true;
            mPrevWantedState = mWantedState;
        }
        else mWantedStateChanged = false;
        SystemState newState;
        switch (mCurrentState) {
            case INTAKING:
//                if(hasBall()){
//                    newState = handleStop();
//                } else
                newState = handleIntake();
                break;
            case EXHAUSTING:
                newState = handleExhaust();
                break;
            case STOP: default:
                newState = handleStop();
                break;
            case CLIMB_DRIVING:
                newState = handleClimbDrive();
                break;
        }

        if (newState != mCurrentState) {
            System.out.println(
                    String.format(
                            "CHANGED (%s) -> (%s)",
                            mCurrentState.name(),
                            newState.name()
                    )
            );
            mCurrentState = newState;
            mStateChanged = true;
        } else {
            mStateChanged = false;
        }
        this.outputToDashboard();
    }

    private SystemState handleIntake() {
        mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_INTAKE_POS);
        setIntakePower(kIntakeSpeed);
        if (mStateChanged) {
            startTime = Timer.getFPGATimestamp();
        }

        if (hasBall()) {
            setWantedState(WantedState.WANTS_TO_STOP);
        }

        return defaultStateTransfer();
    }

    private SystemState handleExhaust() {
        if (mStateChanged) {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_EXHAUST_POS);
        }
        setIntakePower(-kIntakeSpeed);
        return defaultStateTransfer();
    }

    private SystemState handleStop() {
        if (mStateChanged) {
            mPivot.setWantedState(NewPivot.WantedState.WANTS_TO_DEPLOY_POS);
            setIntakePower(0);
        }

        return defaultStateTransfer();
    }

    private SystemState handleClimbDrive() {
        setIntakePower(1);
        return defaultStateTransfer();
    }

    private SystemState defaultStateTransfer() {
        switch (mWantedState) {
            case WANTS_TO_INTAKE:
                return SystemState.INTAKING;
            case WANTS_TO_EXHAUST:
                return SystemState.EXHAUSTING;
            case WANTS_TO_STOP:
                return SystemState.STOP;
            case WANTS_TO_CLIMB_DRIVE:
                return SystemState.CLIMB_DRIVING;
            default:
                return SystemState.STOP;
        }
    }

    private void setIntakePower(double power) {
        mCargoIntakeLeft.set(power);
        mCargoIntakeRight.set(power);
    }

    private boolean hasBall () {
        return ((Timer.getFPGATimestamp() - startTime) > (kIntitialSpikeDelay)) &&
                mCargoIntakeLeft.getOutputCurrent() > kIntakeSpike;
    }

    @Override
    public void end(double timestamp) { }


}
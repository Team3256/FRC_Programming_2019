package frc.team3256.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class NewElevator extends SubsystemBase {

    private CANSparkMax mMaster, mSlave;
    private DigitalInput mHallEffect;

    private enum ElevatorControlState {
        MANUAL_UP,
        MANUAL_DOWN,
        HOLD,
        HOMING,
        CLOSED_LOOP_UP,
        CLOSED_LOOP_DOWN
    }

    public enum WantedState {
        WANTS_TO_MANUAL_UP,
        WANTS_TO_MANUAL_DOWN,
        WANTS_TO_HOME,
        WANTS_TO_HOLD,
        WANTS_TO_HIGH_CARGO,
        WANTS_TO_MID_CARGO,
        WANTS_TO_LOW_CARGO,
        WANTS_TO_HIGH_HATCH,
        WANTS_TO_MID_HATCH,
        WANTS_TO_LOW_HATCH
    }

    private ElevatorControlState mCurrentState = ElevatorControlState.HOLD;
    private WantedState mWantedState = WantedState.WANTS_TO_HOLD;
    private WantedState mPrevWantedState = WantedState.WANTS_TO_HOLD;

    private boolean mStateChanged = false;
    private boolean mWantedStateChanged = false;
    private boolean mUsingClosedLoop = false;

    private double mClosedLoopTarget = 0.0;

    private boolean mIsHomed;

    private static NewElevator instance;
    public static NewElevator getInstance() {
        return instance == null ? instance = new NewElevator() : instance;
    }

    private InterruptHandlerFunction<NewElevator> ihr = new InterruptHandlerFunction<NewElevator>() {
        @Override
        public void interruptFired(int interruptAssertedMask, NewElevator param) {
            mIsHomed = true;
        }
    };

    private NewElevator() {
        mMaster = new CANSparkMax(kSparkMaxMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        mSlave = new CANSparkMax(kSparkMaxSlave, CANSparkMaxLowLevel.MotorType.kBrushless);

        mMaster.setInverted(true);
        mSlave.follow(mMaster, true);

        mHallEffect = new DigitalInput(kHallEffectPort);
        mHallEffect.requestInterrupts(ihr);
        mHallEffect.setUpSourceEdge(false, true);
        mHallEffect.enableInterrupts();

        SparkMAXUtil.setPIDGains(
                mMaster.getPIDController(),
                kElevatorHoldPort,
                kElevatorHoldP,
                kElevatorHoldI,
                kElevatorHoldD,
                kElevatorHoldIZone,
                kElevatorF
        );

        SparkMAXUtil.setPIDGains(
                mMaster.getPIDController(),
                kElevatorClosedLoopPort,
                kElevatorClosedLoopP,
                kElevatorClosedLoopI,
                kElevatorClosedLoopD,
                kElevatorClosedLoopIZone,
                kElevatorClosedLoopF
        );

        setBrake();

        mIsHomed = false;
    }

    public void setWantedState(WantedState wantedState){
        this.mWantedState = wantedState;
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Elevator Temp Master", mMaster.getMotorTemperature());
        SmartDashboard.putNumber("Elevator Temp Slave", mSlave.getMotorTemperature());

        SmartDashboard.putNumber("Elevator Current", mMaster.getOutputCurrent());

        SmartDashboard.putNumber("Elevator Height", getCurrentPositionInches());
        SmartDashboard.putNumber("Elevator Ticks", mMaster.getEncoder().getPosition());

        SmartDashboard.putBoolean("Elevator Hall Effect", mHallEffect.get());

        SmartDashboard.putNumber("Elevator RPM", mMaster.getEncoder().getVelocity() * kElevatorGearRatio);

        SmartDashboard.putString("Elevator State", mCurrentState.name());
    }

    @Override
    public void zeroSensors() {
        mMaster.setEncPosition(0);
    }

    @Override
    public void init(double timestamp) {
    }

    @Override
    public void update(double timestamp) {
        if (mPrevWantedState != mWantedState){
            mWantedStateChanged = true;
            mPrevWantedState = mWantedState;
        }
        else mWantedStateChanged = false;

        ElevatorControlState newState = ElevatorControlState.HOLD;
        switch (mCurrentState) {
            case HOLD:
                newState = handleHold();
                break;
            case HOMING:
                newState = handleHome();
                break;
            case CLOSED_LOOP_UP:
                newState = handleClosedLoopUp();
                break;
            case CLOSED_LOOP_DOWN:
                newState = handleClosedLoopDown();
                break;
            case MANUAL_UP:
                newState = handleManualControlUp();
                break;
            case MANUAL_DOWN:
                newState = handleManualControlDown();
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
    }

    @Override
    public void end(double timestamp) {
        this.setOpenLoop(0.0);
    }

    private ElevatorControlState defaultStateTransfer() {
        ElevatorControlState nextState;

        switch (mWantedState) {
            case WANTS_TO_HOLD:
                mUsingClosedLoop = false;
                return ElevatorControlState.HOLD;
            case WANTS_TO_HOME:
                return ElevatorControlState.HOMING;
            case WANTS_TO_MANUAL_UP:
                mUsingClosedLoop = false;
                return ElevatorControlState.MANUAL_UP;
            case WANTS_TO_MANUAL_DOWN:
                mUsingClosedLoop = false;
                return ElevatorControlState.MANUAL_DOWN;
            case WANTS_TO_HIGH_CARGO:
                if (mStateChanged) {
                    mClosedLoopTarget = kPositionHighCargo;
                }
                mUsingClosedLoop = true;
                break;
            case WANTS_TO_MID_CARGO:
                if (mStateChanged) {
                    mClosedLoopTarget = kPositionMidCargo;
                }
                mUsingClosedLoop = true;
                break;
            case WANTS_TO_LOW_CARGO:
                if (mStateChanged) {
                    mClosedLoopTarget = kPositionLowCargo;
                }
                mUsingClosedLoop = true;
                break;
            case WANTS_TO_HIGH_HATCH:
                if (mStateChanged) {
                    mClosedLoopTarget = kPositionHighHatch;
                }
                mUsingClosedLoop = true;
                break;
            case WANTS_TO_MID_HATCH:
                if (mStateChanged) {
                    mClosedLoopTarget = kPositionMidHatch;
                }
                mUsingClosedLoop = true;
                break;
            case WANTS_TO_LOW_HATCH:
                if (mStateChanged) {
                    mClosedLoopTarget = kPositionLowHatch;
                }
                mUsingClosedLoop = true;
                break;
        }

        if(mClosedLoopTarget > getCurrentPositionInches() && mUsingClosedLoop) {
            nextState = ElevatorControlState.CLOSED_LOOP_UP;
        }
        else if (mClosedLoopTarget < getCurrentPositionInches() && mUsingClosedLoop){
            nextState = ElevatorControlState.CLOSED_LOOP_DOWN;
        }
        else nextState = ElevatorControlState.HOLD;

        return nextState;
    }

    public ElevatorControlState handleHold() {
        mMaster.getPIDController().setReference(getCurrentPosition(), ControlType.kPosition, kElevatorHoldPort);

        return defaultStateTransfer();
    }

    public ElevatorControlState handleHome() {
        if (mIsHomed) {
            return ElevatorControlState.HOLD;
        } else {
            if (mStateChanged) {
                mMaster.set(-kElevatorSpeed);
            }
            return ElevatorControlState.HOMING;
        }

        //return ElevatorControlState.HOLD;
    }

    public ElevatorControlState handleClosedLoopUp() {
        if (mIsHomed) {
            if (atClosedLoopTarget()) {
                return ElevatorControlState.HOLD;
            }

            mMaster.getPIDController().setReference(inchesToRotations(mClosedLoopTarget), ControlType.kPosition, kElevatorClosedLoopPort);

            return defaultStateTransfer();
        }

        return defaultStateTransfer();
    }

    public ElevatorControlState handleClosedLoopDown() {
        if (mIsHomed) {
            if (atClosedLoopTarget()) {
                return ElevatorControlState.HOLD;
            }

            mMaster.getPIDController().setReference(inchesToRotations(mClosedLoopTarget), ControlType.kPosition, kElevatorClosedLoopPort);

            return defaultStateTransfer();
        }

        return defaultStateTransfer();
    }

    public ElevatorControlState handleManualControlUp() {
        setOpenLoop(kElevatorSpeed);
        return defaultStateTransfer();
    }

    public ElevatorControlState handleManualControlDown() {
        setOpenLoop(-kElevatorSpeed);
        return defaultStateTransfer();
    }

    public boolean atClosedLoopTarget(){
        if (!mUsingClosedLoop || mWantedStateChanged || mStateChanged) return false;
        return (Math.abs(getCurrentPositionInches() - mClosedLoopTarget) < 1.0);
    }

    public void setBrake() {
        mMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        mSlave.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setCoast() {
        mMaster.setIdleMode(CANSparkMax.IdleMode.kCoast);
        mSlave.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setOpenLoop(double power) {
        System.out.println("sestOpenLoop: " + power);
    }

    public boolean isHomed() {
        return mIsHomed;
    }

    public double getCurrentPositionInches() {
        return rotationToInches(mMaster.getEncoder().getPosition());
    }

    public double getCurrentPosition() {
        return mMaster.getEncoder().getPosition();
    }

    // Helper Functions
    private double rotationToInches(double rotations) {
        return (rotations * kElevatorGearRatio * kElevatorSpoolSize * Math.PI) + kElevatorOffset;
    }

    private double inchesToRotations(double inches) {
        return (inches - kElevatorOffset) / Math.PI / kElevatorGearRatio / kElevatorSpoolSize;
    }
}

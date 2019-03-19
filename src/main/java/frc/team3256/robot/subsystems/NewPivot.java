package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.HatchConstants.*;

public class NewPivot extends SubsystemBase {

    private WPI_TalonSRX mMaster;
    private DoubleSolenoid mBrake;

    private enum SystemState {
        MANUAL_UP,
        MANUAL_DOWN,
        HOLD,
        CLOSED_LOOP_UP,
        CLOSED_LOOP_DOWN,
        IN_ILLEGAL_AREA
    }

    public enum WantedState {
        WANTS_TO_MANUAL_UP,
        WANTS_TO_MANUAL_DOWN,
        WANTS_TO_HOLD,
        WANTS_TO_INTAKE_POS,
        WANTS_TO_DEPLOY_POS
    }

    private SystemState mCurrentState = SystemState.HOLD;
    private WantedState mWantedState = WantedState.WANTS_TO_HOLD;
    private WantedState mPrevWantedState = WantedState.WANTS_TO_HOLD;

    private boolean mStateChanged = false;
    private boolean mWantedStateChanged = false;
    private boolean mUsingClosedLoop = false;

    private double mClosedLoopTarget = 0.0;

    private static NewPivot instance;

    public static NewPivot getInstance() {
        return instance == null ? instance = new NewPivot() : instance;
    }

    private NewPivot() {
        mMaster = TalonSRXUtil.generateGenericTalon(kHatchPivotPort);

        mMaster.setInverted(true);

        TalonSRXUtil.configMagEncoder(mMaster);

        TalonSRXUtil.setBrakeMode(mMaster);

        TalonSRXUtil.setPIDGains(
                mMaster,
                kHatchHoldPort,
                kHatchHoldP,
                kHatchHoldI,
                kHatchHoldD,
                kHatchHoldF
        );

        TalonSRXUtil.setPIDGains(
                mMaster,
                kHatchClosedLoopUpPort,
                kHatchClosedLoopUpP,
                kHatchClosedLoopUpI,
                kHatchClosedLoopUpD,
                kHatchClosedLoopUpF
        );

        TalonSRXUtil.setPIDGains(
                mMaster,
                kHatchClosedLoopDownPort,
                kHatchClosedLoopDownP,
                kHatchClosedLoopDownI,
                kHatchClosedLoopDownD,
                kHatchClosedLoopDownF
        );

        mMaster.configMotionCruiseVelocity(18000, 0);
        mMaster.configMotionAcceleration(12000, 0); //12000

        mMaster.selectProfileSlot(0, 0);
        mMaster.setSensorPhase(true);

        mBrake = new DoubleSolenoid(15, kRatchetForwardChannel, kRatchetReverseChannel);
    }

    public void setWantedState(WantedState wantedState) {
        this.mWantedState = wantedState;
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putString("Pivot State", mCurrentState.name());
        SmartDashboard.putBoolean("Pivot Brake", mBrake.get() == DoubleSolenoid.Value.kForward);
        SmartDashboard.putNumber("Pivot Angle", getAngle());
        SmartDashboard.putNumber("Pivot Encoder", getEncoderValue());
    }

    @Override
    public void zeroSensors() {
        mMaster.setSelectedSensorPosition((int) angleToSensorUnits(kHatchAngleOffset));
    }

    @Override
    public void init(double timestamp) {

    }

    @Override
    public void update(double timestamp) {
        if (mPrevWantedState != mWantedState) {
            mWantedStateChanged = true;
            mPrevWantedState = mWantedState;
        } else mWantedStateChanged = false;

        SystemState newState = SystemState.HOLD;
        switch (mCurrentState) {
            case HOLD:
                newState = handleHold();
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

    private SystemState handleHold() {
        if (mStateChanged) {
            mMaster.selectProfileSlot(kHatchHoldPort, 0);
            engageBrake();
        }

        mMaster.set(ControlMode.Position, getEncoderValue());

        return defaultStateTransfer();
    }

    private SystemState handleClosedLoopUp() {
        if (atClosedLoopTarget()) {
            return SystemState.HOLD;
        }

        if (mStateChanged) {
            mMaster.selectProfileSlot(0, 0);
            mMaster.selectProfileSlot(kHatchClosedLoopUpPort, 0);
            releaseBrake();
        }

        mMaster.set(ControlMode.MotionMagic, angleToSensorUnits(mClosedLoopTarget));

        return defaultStateTransfer();
    }

    private SystemState handleClosedLoopDown() {
        if (atClosedLoopTarget()) {
            return SystemState.HOLD;
        }

        if (mStateChanged) {
            mMaster.selectProfileSlot(0, 0);
            mMaster.selectProfileSlot(kHatchClosedLoopDownPort, 0);
            releaseBrake();
        }

        mMaster.set(ControlMode.MotionMagic, angleToSensorUnits(mClosedLoopTarget));

        return defaultStateTransfer();
    }

    private SystemState handleManualControlUp() {
        releaseBrake();
        setOpenLoop(kHatchPivotSpeed);
        return defaultStateTransfer();
    }

    private SystemState handleManualControlDown() {
        releaseBrake();
        setOpenLoop(-kHatchPivotSpeed);
        return defaultStateTransfer();
    }

    private SystemState defaultStateTransfer() {
        SystemState nextState;

        switch (mWantedState) {
            case WANTS_TO_HOLD:
                mUsingClosedLoop = false;
                return SystemState.HOLD;
            case WANTS_TO_MANUAL_UP:
                mUsingClosedLoop = false;
                return SystemState.MANUAL_UP;
            case WANTS_TO_MANUAL_DOWN:
                mUsingClosedLoop = false;
                return SystemState.MANUAL_DOWN;
            case WANTS_TO_INTAKE_POS:
                mUsingClosedLoop = true;
                mClosedLoopTarget = kPositionCargoIntake;
                break;
            case WANTS_TO_DEPLOY_POS:
                mUsingClosedLoop = true;
                mClosedLoopTarget = kPositionDeployHatch;
                break;
        }

        if (mClosedLoopTarget < getAngle() && mUsingClosedLoop) {
            nextState = SystemState.CLOSED_LOOP_UP;
        } else if (mClosedLoopTarget < getAngle() && mUsingClosedLoop) {
            nextState = SystemState.CLOSED_LOOP_DOWN;
        } else nextState = SystemState.HOLD;

        return nextState;
    }

    private boolean atClosedLoopTarget() {
        if (!mUsingClosedLoop || mWantedStateChanged || mStateChanged) return false;
        return (Math.abs(getAngle() - mClosedLoopTarget) < 1.0);
    }

    @Override
    public void end(double timestamp) {

    }

    private void setOpenLoop(double power) {
        mMaster.set(power);
    }

    private double angleToSensorUnits(double degrees) {
        return (degrees / 360.0) * kHatchPivotGearRatio * 4096.0;
    }

    private double sensorUnitsToAngle(double ticks) {
        return ((ticks / 4096.0) / kHatchPivotGearRatio) * 360.0;
    }

    private double getEncoderValue() {
        return mMaster.getSelectedSensorPosition(0);
    }

    private double getAngle() {
        return sensorUnitsToAngle(mMaster.getSelectedSensorPosition(0));
    }

    public void releaseBrake() {
        mBrake.set(DoubleSolenoid.Value.kReverse);
    }

    private void engageBrake() {
        mBrake.set(DoubleSolenoid.Value.kForward);
    }
}

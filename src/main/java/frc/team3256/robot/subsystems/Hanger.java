package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.warriorlib.loop.Loop;
import frc.team3256.warriorlib.subsystem.SubsystemBase;
import static frc.team3256.robot.constants.DriveTrainConstants.*;

public class Hanger extends SubsystemBase implements Loop {
    private static Hanger instance;
    private DoubleSolenoid hang;
    private DoubleSolenoid outerHang;

    private double startTime;

    public enum HangerState {
        HANGING,
        RETRACTING
    }

    public enum WantedState {
        WANTS_TO_HANG,
        WANTS_TO_RETRACT
    }

    private HangerState mCurrentState = HangerState.RETRACTING;
    private WantedState mWantedState = WantedState.WANTS_TO_RETRACT;
    private boolean stateChanged = true;

    public HangerState getHangerState() {
        return mCurrentState;
    }

    public void setWantedState(WantedState wantedState){
        mWantedState = wantedState;
    }

    private Hanger() {
        hang = new DoubleSolenoid(pcmId, kHangerForward, kHangerReverse);
        outerHang = new DoubleSolenoid(pcmId, kOuterHangerForward, kOuterHangerReverse);
    }

    public static Hanger getInstance() { return instance == null ? instance = new Hanger() : instance; }

    private HangerState handleHang() {
        if (stateChanged) {
            hang.set(DoubleSolenoid.Value.kForward);
            outerHang.set(DoubleSolenoid.Value.kForward);
        }
        return defaultStateTransfer();
    }

    private HangerState handleRetract() {
        if (stateChanged) {
            hang.set(DoubleSolenoid.Value.kReverse);
            startTime = Timer.getFPGATimestamp();
        }
        if(Timer.getFPGATimestamp() - startTime > 2) {
            outerHang.set(DoubleSolenoid.Value.kReverse);
        }
        return defaultStateTransfer();
    }

    private HangerState defaultStateTransfer() {
        switch(mWantedState) {
            case WANTS_TO_HANG:
                return HangerState.HANGING;
            case WANTS_TO_RETRACT:
            default:
                return HangerState.RETRACTING;
        }
    }

    @Override
    public void update(double timestamp) {
        HangerState newState;
        switch(mCurrentState) {
            case HANGING:
                newState = handleHang();
                break;
            case RETRACTING:
            default:
                newState = handleRetract();
                break;
        }
        if (newState != mCurrentState){
            System.out.println("Current state:" + mCurrentState + "\tNew state:" + newState);
            mCurrentState = newState;
            stateChanged = true;
        }
        else {
            stateChanged = false;
        }
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putString("Hanger State", mCurrentState.name());
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

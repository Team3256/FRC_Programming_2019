package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.warriorlib.loop.Loop;
import frc.team3256.warriorlib.subsystem.SubsystemBase;
import static frc.team3256.robot.constants.DriveTrainConstants.*;

public class Hanger extends SubsystemBase implements Loop {
    private static Hanger instance;
    private DoubleSolenoid hang;

    private enum HangerState {
        HANGING,
        RETRACTED
    }

    private enum WantedState {
        WANTS_TO_HANG,
        WANTS_TO_RETRACT
    }



    private HangerState mCurrentState = HangerState.RETRACTED;
    private WantedState mWantedState = WantedState.WANTS_TO_RETRACT;

    public void getHangerState() {
        return m
    }
    public void setWantedState(WantedState wantedState){
        this.mWantedState = wantedState;
    }


    private Hanger() {
        hang = new DoubleSolenoid(DriveTrainConstants.pcmId, kHangerForward, kHangerReverse);
    }

    public static Hanger getInstance() { return instance == null ? instance = new Hanger() : instance; }

    public void hang() {
        hang.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {hang.set(DoubleSolenoid.Value.kReverse);}

    @Override
    public void update(double timestamp) {

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

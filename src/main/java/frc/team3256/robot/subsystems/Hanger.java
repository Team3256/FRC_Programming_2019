package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class Hanger extends SubsystemBase {
    private static Hanger instance;
    private DoubleSolenoid hang;

    private Hanger() {
        hang = new DoubleSolenoid(Constants.kHangerForward, Constants.kHangerReverse);
    }

    public static Hanger getInstance() { return instance == null ? instance = new Hanger() : instance; }

    public void hang() {
        hang.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        hang.close();
    }

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

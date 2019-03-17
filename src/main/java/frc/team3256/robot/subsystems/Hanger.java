package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.warriorlib.subsystem.SubsystemBase;
import static frc.team3256.robot.constants.DriveTrainConstants.*;

public class Hanger extends SubsystemBase {
    private static Hanger instance;
    private DoubleSolenoid hang;

    private Hanger() {
        hang = new DoubleSolenoid(15, kHangerForward, kHangerReverse);
    }

    public static Hanger getInstance() { return instance == null ? instance = new Hanger() : instance; }

    public void hang() {
        hang.set(DoubleSolenoid.Value.kForward);
    }

    public void close() {
        hang.close();
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

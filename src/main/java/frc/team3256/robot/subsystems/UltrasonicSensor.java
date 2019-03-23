package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.UltrasonicConstants.*;

public class UltrasonicSensor extends SubsystemBase {
    private static UltrasonicSensor instance;

    private Ultrasonic climbUltrasonic;

    private UltrasonicSensor() {
        climbUltrasonic = new Ultrasonic(kClimbUltrasonicPing,kClimbUltrasonicEcho);
        climbUltrasonic.setEnabled(true);
    }

    public void getRangeMM() {
        climbUltrasonic.getRangeMM();
    }

    public void getRangeInches() {
        climbUltrasonic.getRangeInches();
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

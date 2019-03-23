package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.UltrasonicConstants.*;

public class UltrasonicSensor extends SubsystemBase {
    private static UltrasonicSensor instance;

    private Ultrasonic climbUltrasonic;

    private UltrasonicSensor() {
        climbUltrasonic = new Ultrasonic(kClimbUltrasonicPing,kClimbUltrasonicEcho);
        climbUltrasonic.setEnabled(true);
    }

    public static UltrasonicSensor getInstance() { return instance == null ? instance = new UltrasonicSensor() : instance; }

    public double getRangeMM() {
        return climbUltrasonic.getRangeMM();
    }

    public double getRangeInches() { return climbUltrasonic.getRangeInches(); }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("UltraSonic MM", getRangeMM());
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

package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.UltrasonicConstants.*;

public class UltrasonicSensor extends SubsystemBase {
    private static UltrasonicSensor instance;

    private Ultrasonic climbUltrasonic, hatchUltrasonic;

    private UltrasonicSensor() {
        climbUltrasonic = new Ultrasonic(kClimbUltrasonicPing, kClimbUltrasonicEcho);
        hatchUltrasonic = new Ultrasonic(kHatchUltrasonicPing, kHatchUltrasSonicEcho);
        climbUltrasonic.setEnabled(true);
        hatchUltrasonic.setEnabled(true);
    }

    public static UltrasonicSensor getInstance() { return instance == null ? instance = new UltrasonicSensor() : instance; }

    public double getClimbRangeMM() {
        return climbUltrasonic.getRangeMM();
    }

    public double getHatchRangeMM() {
        return hatchUltrasonic.getRangeMM();
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("UltraSonic MM", getClimbRangeMM());
        SmartDashboard.putNumber("Hatch MM", getHatchRangeMM());
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

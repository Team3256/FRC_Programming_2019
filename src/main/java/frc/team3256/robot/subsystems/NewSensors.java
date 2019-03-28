package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.DriveTrainConstants.*;
import static frc.team3256.robot.constants.NewSensorConstants.*;

public class NewSensors extends SubsystemBase {
    private static NewSensors instance;

    private Compressor compressor;
    private AnalogInput pressureSensor;
    private Ultrasonic climbUltrasonicLeft, climbUltrasonicRight, ultrasonicFront;

    private NewSensors() {
        compressor = new Compressor(pcmId);
        pressureSensor = new AnalogInput(kPressureSensorPort);
        climbUltrasonicLeft = new Ultrasonic(kClimbUltrasonicLeftPing, kClimbUltrasonicLeftEcho);
        climbUltrasonicRight = new Ultrasonic(kClimbUltrasonicRightPing, kClimbUltrasonicRightEcho);
        ultrasonicFront = new Ultrasonic(kFrontUltrasonicPing, kFrontUltrasonicEcho);
        climbUltrasonicLeft.setEnabled(true);
        climbUltrasonicRight.setEnabled(true);
        ultrasonicFront.setEnabled(true);
    }

    public static NewSensors getInstance() {return instance == null ? instance = new NewSensors(): instance;}

    public void turnOnCompressor() {
        compressor.setClosedLoopControl(true);
    }

    public void turnOffCompressor() {
        compressor.setClosedLoopControl(false);
        compressor.stop();
    }

    public double getAirPressurePsi() { return 250.0 * (pressureSensor.getVoltage()/4.75) - 25; }

    public double getClimbLeftRange() { return climbUltrasonicLeft.getRangeMM(); }

    public double getClimbRightRange() { return climbUltrasonicRight.getRangeMM(); }

    public double getFrontRange() { return ultrasonicFront.getRangeMM(); }


    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Air Pressure Psi", getAirPressurePsi());
        SmartDashboard.putNumber("ClimbLeft MM", getClimbLeftRange());
        SmartDashboard.putNumber("ClimbRight MM", getClimbRightRange());
        SmartDashboard.putNumber("Front MM", getFrontRange());
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

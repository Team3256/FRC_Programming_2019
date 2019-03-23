package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.DriveTrainConstants.*;
import static frc.team3256.robot.constants.SensorConstants.*;

public class Sensors extends SubsystemBase {
    private static Sensors instance;

    private Compressor compressor;
    private AnalogInput pressureSensor;
    private Ultrasonic climbSensor;

    private Sensors() {
        compressor = new Compressor(pcmId);
        pressureSensor = new AnalogInput(kPressureSensorPort);
        //climbSensor = new Ultrasonic(kClimbTrigger, kClimbEcho);
        //climbSensor.setAutomaticMode(true);
    }

    public static Sensors getInstance() {return instance == null ? instance = new Sensors(): instance;}

    public void turnOnCompressor() {
        compressor.setClosedLoopControl(true);
    }

    public void turnOffCompressor() {
        compressor.setClosedLoopControl(false);
        compressor.stop();
    }

    public double getAirPressurePsi() { return 250.0 * (pressureSensor.getVoltage()/4.75) - 25; }


    public boolean isCompressorFull () {
        return compressor.getPressureSwitchValue();
    }

    public boolean climbReady() {return getAirPressurePsi() > 90; }

    public double getCompressorCurrent() {
        return compressor.getCompressorCurrent();
    }

    public double getRangeMM() { return climbSensor.getRangeMM(); }

    public double getRangeInches () { return climbSensor.getRangeInches(); }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Air Pressure Psi", getAirPressurePsi());
        //SmartDashboard.putNumber("Climb Ultrasonic", getRangeMM());
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

    @Override
    public void zeroSensors() {

    }
}

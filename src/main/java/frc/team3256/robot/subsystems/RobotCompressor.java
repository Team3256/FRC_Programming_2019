package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.DriveTrainConstants.*;

public class RobotCompressor extends SubsystemBase {
    private static RobotCompressor instance;

    private Compressor compressor;
    private AnalogInput pressureSensor;

    private RobotCompressor() {
        compressor = new Compressor(pcmId);
        pressureSensor = new AnalogInput(kPressureSensorPort);
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Air Pressure Psi", getAirPressurePsi());
    }

    @Override
    public void zeroSensors() {

    }

    public void turnOn() {
        compressor.setClosedLoopControl(true);
    }

    public void turnOff() {
        compressor.setClosedLoopControl(false);
        compressor.stop();
    }

    public boolean isFull() {
        return compressor.getPressureSwitchValue();
    }

    public boolean climbReady() {return getAirPressurePsi() > 90; }

    public double getCurrent() {
        return compressor.getCompressorCurrent();
    }

    public double getAirPressurePsi() { return 250.0 * (pressureSensor.getVoltage()/4.75) - 25; }

    public static RobotCompressor getInstance() {return instance == null ? instance = new RobotCompressor(): instance;}

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

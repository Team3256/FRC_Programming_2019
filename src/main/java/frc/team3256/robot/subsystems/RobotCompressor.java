package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import static frc.team3256.robot.constants.DriveTrainConstants.*;

public class RobotCompressor {
    private static RobotCompressor instance;

    private Compressor compressor;
    private AnalogInput pressureSensor;

    private RobotCompressor() {
        compressor = new Compressor(pcmId);
        pressureSensor = new AnalogInput(kPressureSensorPort);
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

    public double getAirPressurePsi() { return 250.0 * (pressureSensor.getVoltage()/5.0) - 25; }

    public static RobotCompressor getInstance() {return instance == null ? instance = new RobotCompressor(): instance;}
}

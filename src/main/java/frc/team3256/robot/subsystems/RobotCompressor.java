package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;

public class RobotCompressor {
    private static final int kCompressorPort = 0; // change to actual thing lul
    private static RobotCompressor instance;

    private Compressor compressor;

    private RobotCompressor() {
        compressor = new Compressor(kCompressorPort);
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

    public double getCurrent() {
        return compressor.getCompressorCurrent();
    }

    public static RobotCompressor getInstance() {return instance == null ? instance = new RobotCompressor(): instance;}
}

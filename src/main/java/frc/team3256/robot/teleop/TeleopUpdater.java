package frc.team3256.robot.teleop;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.team3256.robot.DriveTrain;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private Joystick driver;

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private TeleopUpdater() {
        driver = new Joystick(0);
    }

    public double deadband(double x, double min) {
        if (Math.abs(x) < min) {
            return 0;
        } else {
            return x;
        }
    }

    public void handleDrive() {
        driveTrain.setOpenLoop(deadband(-driver.getRawAxis(5), 0.1)*0.5, deadband(-driver.getRawAxis(1), 0.1)*0.5);
}

    public void update() {
        handleDrive();
        handlePneumatics();
    }

    public void handlePneumatics() {

    }
}

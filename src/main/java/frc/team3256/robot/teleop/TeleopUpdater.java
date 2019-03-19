package frc.team3256.robot.teleop;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.teleop.control.*;
import frc.team3256.warriorlib.control.DrivePower;
import frc.team3256.warriorlib.control.XboxControllerObserver;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private XboxDriverController driverController;
    private XboxController manipulatorController;

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private TeleopUpdater() {
        driverController = XboxDriverController.getInstance();
    }

    public void handleDrive() {
        driveTrain.setBrakeMode();
        DrivePower drivePower = DriveTrain.getInstance().betterCurvatureDrive(
                driverController.getThrottle(),
                driverController.getTurn()*(driverController.getHighGear() ? 0.6 : 1.0),
                driverController.getQuickTurn(),
                driverController.getHighGear()
        );
        driveTrain.setHighGear(drivePower.getHighGear());
        driveTrain.setPowerClosedLoop(drivePower.getLeft(), drivePower.getRight());
    }

    public void update() {
        handleDrive();


    }
}

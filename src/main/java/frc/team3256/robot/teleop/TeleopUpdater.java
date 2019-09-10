package frc.team3256.robot.teleop;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.teleop.control.*;
import frc.team3256.warriorlib.control.DrivePower;
import frc.team3256.warriorlib.control.XboxControllerObserver;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private XboxControllerObserver driverController;
    private GameCubeControllerObserver manipulatorController;

    private CargoIntakeControlScheme cargoIntakeControlScheme;
    private HatchIntakeControlScheme hatchIntakeControlScheme;
    private ClimbControlScheme climbControlScheme;
    private DriverControlScheme driverControlScheme;
    private Elevator mElevator = Elevator.getInstance();

    private XboxListenerBase currentControlScheme;

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private TeleopUpdater() {
        driverController = new XboxControllerObserver(0);
        manipulatorController = new GameCubeControllerObserver(1);

        cargoIntakeControlScheme = new CargoIntakeControlScheme();
        hatchIntakeControlScheme = new HatchIntakeControlScheme();
        climbControlScheme = new ClimbControlScheme();

        currentControlScheme = cargoIntakeControlScheme;
        manipulatorController.setListener(currentControlScheme);

        driverControlScheme = new DriverControlScheme();
        driverController.setListener(driverControlScheme);
        driverControlScheme.setController(driverController);

        //cargoIntakeControlScheme.setController(manipulatorController);
        //hatchIntakeControlScheme.setController(manipulatorController);
    }

    public void handleDrive() {
        driveTrain.setBrakeMode();
//        DrivePower drivePower = DriveTrain.curvatureDrive(
//                driverControlScheme.getLeftY(),
//                driverControlScheme.getRightX()*(driverControlScheme.isHighGear() ? 0.6 : 1.0),
//                driverControlScheme.isQuickTurn(),
//                driverControlScheme.isHighGear());
//        DrivePower drivePower = DriveTrain.getInstance().betterCurvatureDrive(
//                driverControlScheme.getLeftY(),
//                driverControlScheme.getRightY(),
//                driverControlScheme.isQuickTurn(),
//                driverControlScheme.isHighGear()
//        );
        DrivePower drivePower = DriveTrain.getInstance().tankDrive(driverControlScheme.getLeftY(), driverControlScheme.getRightY());
        driveTrain.setHighGear(drivePower.getHighGear());
        //driveTrain.setPowerClosedLoop(drivePower.getLeft(), drivePower.getRight(), drivePower.getHighGear());
        driveTrain.setPowerOpenLoop(drivePower.getLeft(), drivePower.getRight());
    }

    public void changeToCargoControlScheme() {
        SmartDashboard.putString("ControlScheme", "Cargo");
        currentControlScheme = cargoIntakeControlScheme;
        manipulatorController.setListener(currentControlScheme);
    }

    public void changeToHatchControlScheme() {
        SmartDashboard.putString("ControlScheme", "Hatch");
        currentControlScheme = hatchIntakeControlScheme;
        manipulatorController.setListener(currentControlScheme);
    }

    public void update() {
        handleDrive();
        manipulatorController.update();
        driverController.update();
    }

    public XboxController getDriverController() {
        return driverController.getXboxController();
    }
}

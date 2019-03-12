package frc.team3256.robot.teleop;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.teleop.control.CargoIntakeControlScheme;
import frc.team3256.robot.teleop.control.DriverControlScheme;
import frc.team3256.robot.teleop.control.GameCubeControllerObserver;
import frc.team3256.robot.teleop.control.HatchIntakeControlScheme;
import frc.team3256.warriorlib.control.DrivePower;
import frc.team3256.warriorlib.control.XboxControllerObserver;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private XboxControllerObserver driverController;
    private GameCubeControllerObserver manipulatorController;

    private CargoIntakeControlScheme cargoIntakeControlScheme;
    private HatchIntakeControlScheme hatchIntakeControlScheme;
    private DriverControlScheme driverControlScheme;

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

        currentControlScheme = cargoIntakeControlScheme;
        manipulatorController.setListener(currentControlScheme);

        driverControlScheme = new DriverControlScheme();
        driverController.setListener(driverControlScheme);
        driverControlScheme.setController(driverController);

        //cargoIntakeControlScheme.setController(manipulatorController);
        //hatchIntakeControlScheme.setController(manipulatorController);
    }

    private void handleDrive() {
        driveTrain.setBrakeMode();
        DrivePower drivePower = DriveTrain.curvatureDrive(
                driverControlScheme.getLeftY(),
                driverControlScheme.getRightX()*0.7,
                driverControlScheme.isQuickTurn(),
                driverControlScheme.isHighGear());
        driveTrain.setHighGear(drivePower.getHighGear());
        driveTrain.setPowerClosedLoop(drivePower.getLeft(), drivePower.getRight());
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
        SmartDashboard.putNumber("Left RPM", driveTrain.getLeftRPM());
        SmartDashboard.putNumber("Right RPM", driveTrain.getRightRPM());
        //SmartDashboard.putNumber("RPM Difference", driveTrain.getLeftRPM() - driveTrain.getRightRPM());
//        if(HatchPivot.getInstance().hasHatch) { //Implement when we know hasHatch & hasCargo works
//            changeToHatchControlScheme();
//        }
    }

    public XboxController getDriverController() {
        return driverController.getXboxController();
    }
}

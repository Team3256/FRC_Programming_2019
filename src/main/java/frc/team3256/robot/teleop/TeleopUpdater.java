package frc.team3256.robot.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.*;
import frc.team3256.robot.teleop.control.*;
import frc.team3256.robot.teleop.scheme.CargoIntakeControlScheme;
import frc.team3256.robot.teleop.scheme.HatchIntakeControlScheme;
import frc.team3256.warriorlib.control.DrivePower;
import frc.team3256.warriorlib.control.XboxControllerObserver;
import frc.team3256.warriorlib.control.XboxListenerBase;

import static frc.team3256.robot.constants.UltrasonicConstants.*;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private IDriverController driverController = XboxDriverController.getInstance();
    private XboxControllerObserver manipulatorController;

    private XboxListenerBase currentControlScheme;
    private CargoIntakeControlScheme cargoIntakeControlScheme;
    private HatchIntakeControlScheme hatchIntakeControlScheme;

    private Elevator mElevator = Elevator.getInstance();
    private Pivot mPivot = Pivot.getInstance();
    private CargoIntake mCargoIntake = CargoIntake.getInstance();
    private Hanger mHanger = Hanger.getInstance();
    private Sensors mSensors = Sensors.getInstance();
    private UltrasonicSensor ultrasonicSensor = UltrasonicSensor.getInstance();

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private TeleopUpdater() {
        manipulatorController = new XboxControllerObserver(1);
        cargoIntakeControlScheme = new CargoIntakeControlScheme();
        hatchIntakeControlScheme = new HatchIntakeControlScheme();

        currentControlScheme = cargoIntakeControlScheme;
        manipulatorController.setListener(currentControlScheme);
    }

    public void handleDrive() {
        driveTrain.setBrakeMode();
        DrivePower drivePower = DriveTrain.getInstance().betterCurvatureDrive(
                Math.abs(driverController.getThrottle()) > 0.15 ? driverController.getThrottle() : 0.0,
                driverController.getTurn()*(driverController.getHighGear() ? 0.6 : 1.0),
                driverController.getQuickTurn(),
                driverController.getHighGear()
        );
        driveTrain.setHighGear(drivePower.getHighGear());

        //Implement once we get Ultrasonics installed
//        if(ultrasonicSensor.getClimbRangeMM() < kClimbGroundRange
//           && mHanger.getHangerState() == Hanger.HangerState.HANGING
//           && mElevator.getCurrentPosition() < 0) {
//            driveTrain.setHighGear(false);
//            driveTrain.setPowerOpenLoop(drivePower.getLeft()/2, drivePower.getRight()/2);
//        } else {
//            driveTrain.setPowerOpenLoop(drivePower.getLeft(), drivePower.getRight());
//        }

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
    }
}

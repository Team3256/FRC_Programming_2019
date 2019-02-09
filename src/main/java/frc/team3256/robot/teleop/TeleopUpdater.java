package frc.team3256.robot.teleop;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.team3256.robot.teleop.control.CargoIntakeControlScheme;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.control.DrivePower;
import frc.team3256.warriorlib.control.XboxControllerObserver;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class TeleopUpdater {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private XboxController driverController;
    private XboxControllerObserver manipulatorController;

    private CargoIntakeControlScheme cargoIntakeControlScheme;
    //private HatchIntakeControlScheme hatchIntakeControlScheme;

    private XboxListenerBase currentControlScheme;

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private TeleopUpdater() {
        cargoIntakeControlScheme = new CargoIntakeControlScheme();
        //hatchIntakeControlScheme = new HatchIntakeControlScheme();
        currentControlScheme = cargoIntakeControlScheme;

        driverController = new XboxController(0);
        manipulatorController = new XboxControllerObserver(1);
        manipulatorController.setListener(currentControlScheme);
    }

    private void handleDrive() {
        driveTrain.setBrakeMode();
        DrivePower drivePower = DriveTrain.curvatureDrive(
            -driverController.getY(GenericHID.Hand.kLeft),
            driverController.getX(GenericHID.Hand.kRight),
            driverController.getTriggerAxis(GenericHID.Hand.kRight) > 0.25);
        driveTrain.setHighGear(drivePower.getHighGear());
        driveTrain.setOpenLoop(drivePower.getLeft(), drivePower.getRight());
    }

    private void handleHangDrive(){
        driveTrain.setBrakeMode();
        DrivePower drivePower = DriveTrain.curvatureDrive(
                -driverController.getY(GenericHID.Hand.kLeft),
                driverController.getX(GenericHID.Hand.kRight),
                driverController.getTriggerAxis(GenericHID.Hand.kRight) > 0.25
        );
        driveTrain.setHighGear(drivePower.getHighGear());
        driveTrain.setHangDrive(drivePower.getLeft(), drivePower.getRight());
    }

    public void changeToCargoControlScheme() {
        currentControlScheme = cargoIntakeControlScheme;
        manipulatorController.setListener(currentControlScheme);
    }

    public void changeToHatchControlScheme() {
        //currentControlScheme = hatchIntakeControlScheme;
        manipulatorController.setListener(currentControlScheme);
    }

    public void update() {
        handleDrive();
        
        manipulatorController.update();

        if(driverController.getBackButtonPressed()){
            handleHangDrive();
        }
    }
}

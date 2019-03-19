package frc.team3256.robot.teleop;

import frc.team3256.robot.constants.ElevatorConstants;
import frc.team3256.robot.subsystems.NewElevator;
import frc.team3256.robot.teleop.control.*;

public class TeleopUpdater {
    //private DriveTrain driveTrain = DriveTrain.getInstance();

    //private XboxDriverController driverController;
    private IManipulatorController manipulatorController = DesktopXboxManipulatorController.getInstance();
    private NewElevator mElevator = NewElevator.getInstance();

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }

    private TeleopUpdater() {
        //driverController = XboxDriverController.getInstance();
    }

//    public void handleDrive() {
//        driveTrain.setBrakeMode();
//        DrivePower drivePower = DriveTrain.getInstance().betterCurvatureDrive(
//                driverController.getThrottle(),
//                driverController.getTurn()*(driverController.getHighGear() ? 0.6 : 1.0),
//                driverController.getQuickTurn(),
//                driverController.getHighGear()
//        );
//        driveTrain.setHighGear(drivePower.getHighGear());
//        driveTrain.setPowerClosedLoop(drivePower.getLeft(), drivePower.getRight());
//    }

    public void update() {
//        handleDrive();
        double desiredHeight = Double.NaN;

        double elevatorThrottle = manipulatorController.getElevatorThrottle();

        boolean goToHighRocket = manipulatorController.goToHigh();
        boolean goToMidRocket = manipulatorController.goToMid();
        boolean goToLowRocket = manipulatorController.goToLow();
        boolean goToHome = manipulatorController.goToHome();

        if (goToHighRocket) {
            desiredHeight = ElevatorConstants.kPositionHighCargo;
        } else if (goToMidRocket) {
            desiredHeight = ElevatorConstants.kPositionMidCargo;
        } else if (goToLowRocket) {
            desiredHeight = ElevatorConstants.kPositionLowCargo;
        } else if (goToHome) {
            desiredHeight = ElevatorConstants.kPositionMin;
        }

        if (elevatorThrottle > 0) {
            System.out.println("Up");
        } else if (elevatorThrottle < 0) {
            System.out.println("Down");
        } else if (!Double.isNaN(desiredHeight)) {
            System.out.println(desiredHeight);
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_MOVE_TO_POSITION);
            mElevator.setWantedPosition(desiredHeight);
        } else {
            mElevator.setWantedState(NewElevator.WantedState.WANTS_TO_HOLD);
        }
    }
}

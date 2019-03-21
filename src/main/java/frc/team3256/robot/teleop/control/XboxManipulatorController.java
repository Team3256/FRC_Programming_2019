package frc.team3256.robot.teleop.control;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class XboxManipulatorController implements IManipulatorController {
    private static XboxManipulatorController instance;
    public static XboxManipulatorController getInstance() {
        return instance == null ? instance = new XboxManipulatorController() : instance;
    }

    private XboxController xboxController;

    private XboxManipulatorController() {
        this.xboxController = new XboxController(1);
    }

    @Override
    public boolean getShouldToggleClimb() {
        return xboxController.getBackButtonReleased();
    }

    @Override
    public double getElevatorThrottle() {
        return -xboxController.getY(GenericHID.Hand.kLeft);
    }

    @Override
    public double getPivotThrottle() {
        return -xboxController.getY(GenericHID.Hand.kRight);
    }

    @Override
    public boolean shouldCargoIntake() {
        return xboxController.getTriggerAxis(GenericHID.Hand.kRight) > 0.15;
    }

    @Override
    public boolean shouldCargoOuttake() {
        return xboxController.getTriggerAxis(GenericHID.Hand.kLeft) > 0.15;
    }

    @Override
    public boolean shouldHatchStartIntake() {
        return xboxController.getBumperPressed(GenericHID.Hand.kRight);
    }

    @Override
    public boolean shouldHatchFinishIntake() {
        return xboxController.getBumperReleased(GenericHID.Hand.kRight);
    }

    @Override
    public boolean shouldHatchStartOuttake() {
        return xboxController.getBumperPressed(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean shouldHatchFinishOuttake() {
        return xboxController.getBumperReleased(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean goToHigh() {
        return xboxController.getYButton();
    }

    @Override
    public boolean goToMid() {
        return xboxController.getXButton();
    }

    @Override
    public boolean goToLow() {
        return xboxController.getAButton();
    }

    @Override
    public boolean goToCargoShip() {
        return xboxController.getBButton();
    }

    @Override
    public boolean goToHome() {
        return xboxController.getStartButton();
    }
}

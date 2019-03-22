package frc.team3256.robot.teleop.control;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class XboxDriverController implements IDriverController {

    private static XboxDriverController instance;
    public static XboxDriverController getInstance() {
        return instance == null ? instance = new XboxDriverController() : instance;
    }

    private XboxController xboxController;

    private XboxDriverController() {
        xboxController = new XboxController(0);
    }

    @Override
    public double getThrottle() {
        return -xboxController.getY(GenericHID.Hand.kLeft);
    }

    @Override
    public double getTurn() {
        return xboxController.getX(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getQuickTurn() {
        return xboxController.getTriggerAxis(GenericHID.Hand.kRight) != 0;
    }

    @Override
    public boolean getHighGear() {
        return xboxController.getTriggerAxis(GenericHID.Hand.kLeft) < 0.15;
    }

    @Override
    public boolean getShouldClimb() {
        return xboxController.getStartButton();
    }
}

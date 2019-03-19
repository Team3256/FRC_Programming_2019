package frc.team3256.robot.teleop.control;

public interface IDriverController {
    double getThrottle();
    double getTurn();

    boolean getQuickTurn();
    boolean getHighGear();

    boolean getShouldClimb();
}

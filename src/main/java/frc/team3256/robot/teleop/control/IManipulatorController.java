package frc.team3256.robot.teleop.control;

public interface IManipulatorController {
    boolean getShouldClimb();

    double getElevatorThrottle();

    double getPivotThrottle();

    boolean shouldCargoIntake();

    boolean shouldCargoOuttake();

    boolean shouldHatchIntake();

    boolean shouldHatchOuttake();

    boolean goToHigh();

    boolean goToMid();

    boolean goToLow();

    boolean goToCargoShip();

    boolean goToHome();
}

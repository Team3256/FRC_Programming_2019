package frc.team3256.robot.teleop.control;

public interface IManipulatorController {
    boolean getShouldToggleClimb();

    double getElevatorThrottle();

    double getPivotThrottle();

    boolean shouldCargoIntake();

    boolean shouldCargoOuttake();

    boolean shouldHatchStartIntake();

    boolean shouldHatchFinishIntake();

    boolean shouldHatchStartOuttake();

    boolean shouldHatchFinishOuttake();

    boolean goToHigh();

    boolean goToMid();

    boolean goToLow();

    boolean goToCargoShip();

    boolean goToHome();
}

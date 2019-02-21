package frc.team3256.robot.subsystems.cargointake;

public abstract class CargoIntakeState {
    protected CargoIntake cargoIntake;

    public CargoIntakeState(CargoIntake cargoIntake) {
        this.cargoIntake = cargoIntake;
    }

    public abstract void update(double timestamp);

    public abstract void intake();
    public abstract void exhaust();
}

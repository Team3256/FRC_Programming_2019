package frc.team3256.robot.auto.action;

import edu.wpi.first.wpilibj.Timer;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.warriorlib.auto.action.Action;

public class ZeroCargoAction implements Action {
    private CargoIntake cargoIntake = CargoIntake.getInstance();
    private double startTime = 0;

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= 1.5;
    }

    @Override
    public void update() {
        if (Timer.getFPGATimestamp() - startTime >= 0.5)
            cargoIntake.setPivotPower(0);
    }

    @Override
    public void done() {
        cargoIntake.zeroSensors();
        cargoIntake.setPivotBrake();
    }

    @Override
    public void start() {
        cargoIntake.setPivotCoast();
        cargoIntake.setPivotPower(0.6);
        startTime = Timer.getFPGATimestamp();
    }
}

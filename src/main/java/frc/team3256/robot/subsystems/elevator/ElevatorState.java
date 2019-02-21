package frc.team3256.robot.subsystems.elevator;

import static frc.team3256.robot.constants.ElevatorConstants.*;
import static frc.team3256.robot.constants.ElevatorConstants.kPositionLowHatch;

public abstract class ElevatorState {
    protected Elevator elevator;

    ElevatorState(Elevator elevator) {
        this.elevator = elevator;
    }

    public abstract void onUp();
    public abstract void onDown();

    public abstract void setPositionHighCargo();

    public abstract void setPositionMidCargo();

    public abstract void setPositionLowCargo();

    public abstract void setPositionHighHatch();

    public abstract void setPositionMidHatch();

    public abstract void setPositionLowHatch();
}

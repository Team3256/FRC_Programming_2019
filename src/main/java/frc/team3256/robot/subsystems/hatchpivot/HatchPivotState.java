package frc.team3256.robot.subsystems.hatchpivot;

import frc.team3256.robot.subsystems.HatchPivot;

public abstract class HatchPivotState {
    protected HatchPivot hatchPivot;

    public HatchPivotState(HatchPivot hatchPivot) {
        this.hatchPivot = hatchPivot;
    }
}

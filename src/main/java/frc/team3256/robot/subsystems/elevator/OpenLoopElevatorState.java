package frc.team3256.robot.subsystems.elevator;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class OpenLoopElevatorState extends ElevatorState {
    OpenLoopElevatorState(Elevator elevator) {
        super(elevator);
    }

    @Override
    public void onUp() {
        elevator.setOpenLoop(kElevatorSpeed);
    }

    @Override
    public void onDown() {
        elevator.setOpenLoop(-kElevatorSpeed);
    }

    @Override
    public void setPositionHighCargo() {
        // Can't do that lmao
    }

    @Override
    public void setPositionMidCargo() {
        // Can't do that lmao
    }

    @Override
    public void setPositionLowCargo() {
        // Can't do that lmao
    }

    @Override
    public void setPositionHighHatch() {
        // Can't do that lmao
    }

    @Override
    public void setPositionMidHatch() {
        // Can't do that lmao
    }

    @Override
    public void setPositionLowHatch() {
        // Can't do that lmao
    }
}

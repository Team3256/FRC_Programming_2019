package frc.team3256.robot.subsystems.elevator;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class ClosedLoopElevatorState extends ElevatorState {
    ClosedLoopElevatorState(Elevator elevator) {
        super(elevator);
    }

    @Override
    public void onUp() {
        if (elevator.getPosition() < kPositionMax) {
            elevator.setPosition(elevator.getPosition() + 0.5);
        } else {
            elevator.setPosition(kPositionMax);
        }
    }

    @Override
    public void onDown() {
        if (elevator.getPosition() > kPositionMin) {
            elevator.setPosition(elevator.getPosition() - 0.5);
        } else {
            elevator.setPosition(kPositionMin);
        }
    }

    @Override
    public void setPositionHighCargo() {
        elevator.setPosition(kPositionHighCargo);
    }

    @Override
    public void setPositionMidCargo() {
        elevator.setPosition(kPositionMidCargo);
    }

    @Override
    public void setPositionLowCargo() {
        elevator.setPosition(kPositionLowCargo);
    }

    @Override
    public void setPositionHighHatch() {
        elevator.setPosition(kPositionHighHatch);
    }

    @Override
    public void setPositionMidHatch() {
        elevator.setPosition(kPositionMidHatch);
    }

    @Override
    public void setPositionLowHatch() {
        elevator.setPosition(kPositionLowHatch);
    }
}

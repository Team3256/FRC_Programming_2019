package frc.team3256.robot.teleop.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.elevator.Elevator;
import frc.team3256.warriorlib.control.XboxListenerBase;

import static frc.team3256.robot.constants.ElevatorConstants.kElevatorSpeed;

public abstract class CommonControlScheme extends XboxListenerBase {
    protected Elevator elevator = Elevator.getInstance();

    @Override
    public void onLeftJoystick(double x, double y) {
        SmartDashboard.putNumber("ElevatorSpeed", kElevatorSpeed);
        if (y > 0.25) {
            System.out.println("Up");
            elevator.setOpenLoop(kElevatorSpeed);
        } else if (y < -0.25){
            System.out.println("Down");
            elevator.setOpenLoop(-kElevatorSpeed);
        } else {
            elevator.setOpenLoop(0);
        }
    }
}

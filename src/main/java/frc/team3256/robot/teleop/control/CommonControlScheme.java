package frc.team3256.robot.teleop.control;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.subsystems.HatchPivot;
import frc.team3256.warriorlib.control.XboxListenerBase;

import static frc.team3256.robot.constants.ElevatorConstants.kElevatorSpeed;
import static frc.team3256.robot.constants.HatchConstants.kHatchPivotSpeed;

public abstract class CommonControlScheme extends XboxListenerBase {
    protected Elevator elevator = Elevator.getInstance();
    protected HatchPivot hatchPivot = HatchPivot.getInstance();
    protected CargoIntake cargoIntake = CargoIntake.getInstance();

    private double lastRightTrigger = 0;

    // Move elevator
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

    // Move pivot (inverted)
    @Override
    public void onRightJoystick(double x, double y) {
        if (y > 0.25) {
            if (hatchPivot.getBrakeStatus() != DoubleSolenoid.Value.kReverse) {
                hatchPivot.releaseBrake();
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hatchPivot.setHatchPivotPower(kHatchPivotSpeed);
                });
                thread.start();
            }
            else
                hatchPivot.setHatchPivotPower(kHatchPivotSpeed);
        } else if (y < -0.25) {
            if (hatchPivot.getBrakeStatus() != DoubleSolenoid.Value.kReverse) {
                hatchPivot.releaseBrake();
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hatchPivot.setHatchPivotPower(-kHatchPivotSpeed);
                });
                thread.start();
            }
            else
                hatchPivot.setHatchPivotPower(-kHatchPivotSpeed);
        } else {
            hatchPivot.engageBrake();
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hatchPivot.setHatchPivotPower(0);
            });
            thread.start();
        }
    }

    // Exhaust Cargo on hold
    @Override
    public void onLeftTrigger(double value) {
        if (value > 0.25)
            cargoIntake.exhaust();
        else cargoIntake.setIntakePower(0);
    }

    // Intake Cargo on hold
    @Override
    public void onRightTrigger(double value) {
        System.out.println("Right trigger");
        hatchPivot.setPositionCargoIntake();
        if (value > 0.25 && value - lastRightTrigger > 0) {
            cargoIntake.intake();
        } else {
            cargoIntake.setIntakePower(0);
            hatchPivot.setPositionDeploy();
        }
        lastRightTrigger = value;
    }

    @Override
    public void onLeftShoulderPressed() {
        hatchPivot.deployHatch();
    }

    // Set position to unhook, then retract
    @Override
    public void onLeftShoulderReleased() {
        elevator.setPositionUnhookHatch();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hatchPivot.retractHatch();
        });
        thread.start();
    }

    @Override
    public void onRightShoulderPressed() {
        elevator.setPositionIntakeHatch();
        hatchPivot.deployHatch();
    }

    // Set the position to hook, then retract
    @Override
    public void onRightShoulderReleased() {
        elevator.setPositionHookHatch();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hatchPivot.retractHatch();
        });
        thread.start();
    }

    @Override
    public void onSelectedPressed() {
        hatchPivot.zeroSensors();
    }
}

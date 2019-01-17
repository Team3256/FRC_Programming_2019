package frc.team3256.robot.operation;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.HatchIntake;

public class TeleopUpdater {
    DriveConfigImplementation driveConfigImplementation = new DriveConfigImplementation();
    XboxController xboxController;
    DriveTrain driveTrain;
    HatchIntake hatchIntake;
    CargoIntake cargoIntake;

    public TeleopUpdater(){
        xboxController = new XboxController(0);
        driveTrain = DriveTrain.getInstance();
        hatchIntake = HatchIntake.getInstance();
        cargoIntake = CargoIntake.getInstance();
    }

    public void update(){
        hatchIntake.update(Timer.getFPGATimestamp());
        cargoIntake.update(Timer.getFPGATimestamp());

        //Drivetrain subsystem

        //Arcade Drive
        double throttle = driveConfigImplementation.getThrottle();
        double turn = driveConfigImplementation.getTurn();
        if (Math.abs(throttle) <= 0.15) {
            throttle = 0;
        }
        if (Math.abs(turn) <= 0.15) {
            turn = 0;
        }
        double left = throttle + turn;
        double right = throttle - turn;
        left = Math.max(-1, Math.min(left, 1));
        right = Math.max(-1, Math.min(right, 1));
        driveTrain.setOpenLoop(left, right);

        if (driveTrain.leftSlave.getOutputCurrent() > 10 || driveTrain.leftSlave.getOutputCurrent() > 10)
            System.out.println("LEFT " + driveTrain.leftSlave.getOutputCurrent() + " RIGHT " + driveTrain.rightSlave.getOutputCurrent());


    }
}

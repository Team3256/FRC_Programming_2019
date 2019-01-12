package frc.team3256.robot.operation;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.control.TeleopDriveController;

public class TeleopUpdater {
    TeleopDriveController teleopDriveController;
    DriveTrain driveTrain;

    private XboxController xboxController;

    public TeleopUpdater(){
        teleopDriveController = new TeleopDriveController();
        xboxController = new XboxController(0);
    }

    public void update(){
        teleopDriveController.arcadeDrive(xboxController.getY(GenericHID.Hand.kLeft),
                xboxController.getX(GenericHID.Hand.kRight));
    }
}

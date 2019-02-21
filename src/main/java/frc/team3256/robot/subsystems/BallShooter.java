package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class BallShooter extends SubsystemBase {
    private static BallShooter instance;

    private CargoIntake cargoIntake = CargoIntake.getInstance();

    private WPI_TalonSRX shooterMaster;
    private WPI_TalonSRX shooterSlave;
    private boolean checkForBall = false;

    private BallShooter() {
        shooterMaster = TalonSRXUtil.generateGenericTalon(11);
        shooterSlave = TalonSRXUtil.generateSlaveTalon(12, 11);
        shooterSlave.setInverted(false);
    }

    public static BallShooter getInstance() {
        return instance == null ? instance = new BallShooter() : instance;
    }

    @Override
    public void outputToDashboard() {

    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void init(double timestamp) {

    }

    @Override
    public void update(double timestamp) {
//        if (cargoIntake.getCargoEncoderPosition() < -38) {
//            if (shooterMaster.getOutputCurrent() > 3.0 && shooterMaster.getOutputCurrent() < 5.0) {
//                SmartDashboard.putBoolean("ShooterTime", true);
//                checkForBall = false;
//            } else {
//                SmartDashboard.putBoolean("ShooterTime", false);
//            }
//            shooterMaster.set(0.3);
//            requestedPower = 0.3;
//        } else {
//            requestedPower = 0;
//        }
//        shooterMaster.set(requestedPower);
    }

    @Override
    public void end(double timestamp) {

    }

    public void setPower(double i) {
        shooterMaster.set(i);
    }
}

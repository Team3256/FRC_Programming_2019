package frc.team3256.robot.teleop;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import frc.team3256.robot.DriveTrain;
import frc.team3256.robot.constants.Constants;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;

public class TeleopUpdater {
//    private DriveTrain driveTrain = DriveTrain.getInstance();
//    private Solenoid leftShoot, rightShoot;
//    private DoubleSolenoid leftPop, rightPop, leftEject, rightEject ;
//
    private Joystick driver;
//    private CANSparkMax pivot;
//
    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }
//    private boolean leftEjectToggle = false, rightEjectToggle = false, prevLeftEjectToggle = false, prevRightEjectToggle = false;
//    private boolean leftPopToggle = false, rightPopToggle = false, prevRightPopToggle = false, prevLeftPopToggle = false;
//
    private TeleopUpdater() {
        driver = new Joystick(0);
    }

//    public double deadband(double x, double min) {
//        if (Math.abs(x) < min) {
//            return 0;
//        } else {
//            return x;
//        }
//    }
//
//
//    public void handleDrive() {
//        driveTrain.setPowerOpenLoop(deadband(-driver.getRawAxis(5), 0.1)*0.5, deadband(-driver.getRawAxis(1), 0.1)*0.5);
//        if (driver.getPOV() == 0) {
//            System.out.println(pivot.getBusVoltage());
//            pivot.set(-0.6);
//        } else if (driver.getPOV() == 180){
//            pivot.set(0.6);
//        }
//        else {
//            pivot.set(0.0);
//        }
//}
//
//    public void update() {
//        handleDrive();
//        handlePneumatics();
//    }
//
//    public void handlePneumatics() {
//        if (driver.getRawAxis(2) > 0.25) {
//            leftShoot.set(true);
//            try {
//                Thread.sleep(85);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            leftShoot.set(false);
//        }
//        else if (driver.getRawAxis(3) > 0.25) {
//            rightShoot.set(true);
//            try {
//                Thread.sleep(85);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            rightShoot.set(false);
//        }
//        else {
//            leftShoot.set(false);
//            rightShoot.set(false);
//        }
//
//        if (driver.getRawButtonPressed(3)) {
//            leftPopToggle = !leftPopToggle;
//        }
//
//        else if(leftPopToggle != prevLeftPopToggle) {
//            if (leftPopToggle) {
//                leftPop.set(DoubleSolenoid.Value.kForward);
//                prevLeftPopToggle = leftPopToggle;
//            }
//            else {
//                leftPop.set(DoubleSolenoid.Value.kReverse);
//                prevLeftPopToggle = leftPopToggle;
//            }
//        }
//
//        else if (driver.getRawButtonPressed(2)) {
//            rightPopToggle = !rightPopToggle;
//        }
//
//        else if(rightPopToggle != prevRightPopToggle) {
//            if (rightPopToggle) {
//                rightPop.set(DoubleSolenoid.Value.kForward);
//                prevRightPopToggle = rightPopToggle;
//            }
//            else {
//                rightPop.set(DoubleSolenoid.Value.kReverse);
//                prevRightPopToggle = rightPopToggle;
//            }
//        }
//
//        else if (driver.getRawButtonPressed(5)) {
//            leftEjectToggle = !leftEjectToggle;
//        }
//
//        else if(leftEjectToggle != prevLeftEjectToggle) {
//            if (leftEjectToggle) {
//                leftEject.set(DoubleSolenoid.Value.kForward);
//                prevLeftEjectToggle = leftEjectToggle;
//            }
//            else {
//                leftEject.set(DoubleSolenoid.Value.kReverse);
//                prevLeftEjectToggle = leftEjectToggle;
//            }
//        }
//        else if (driver.getRawButtonPressed(6)) {
//            rightEjectToggle = !rightEjectToggle;
//        }
//        else if (rightEjectToggle != prevRightEjectToggle) {
//            if (rightEjectToggle) {
//                rightEject.set(DoubleSolenoid.Value.kForward);
//                prevRightEjectToggle = rightEjectToggle;
//            }
//            else {
//                rightEject.set(DoubleSolenoid.Value.kReverse);
//                prevRightEjectToggle = rightEjectToggle;
//            }
//        }
//    }
}

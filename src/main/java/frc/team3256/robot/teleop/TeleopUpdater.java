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
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Solenoid leftShoot, rightShoot;
    private DoubleSolenoid leftEject,rightEject, leftPop, rightPop;

    private Joystick driver;
    private CANSparkMax pivot;

    private static TeleopUpdater instance;
    public static TeleopUpdater getInstance() {
        return instance == null ? instance = new TeleopUpdater() : instance;
    }
    private boolean leftEjectToggle = false, rightEjectToggle = false, leftPopToggle = false, rightPopToggle = false;

    private TeleopUpdater() {
        driver = new Joystick(0);
        leftShoot = new Solenoid(Constants.pcmOneId, 0);
        rightShoot = new Solenoid(Constants.pcmOneId, 1);
        leftEject = new DoubleSolenoid(Constants.pcmTwoId, Constants.leftEjectBack, Constants.leftEjectForward);
        rightEject = new DoubleSolenoid(Constants.pcmTwoId, Constants.rightEjectBack, Constants.rightEjectForward);
        leftPop = new DoubleSolenoid(Constants.pcmTwoId, Constants.leftPopBack, Constants.leftPopForward);
        rightPop = new DoubleSolenoid(Constants.pcmTwoId, Constants.rightPopBack, Constants.rightPopForward);
        leftEject.set(DoubleSolenoid.Value.kOff);
        rightEject.set(DoubleSolenoid.Value.kOff);
        leftPop.set(DoubleSolenoid.Value.kOff);
        rightPop.set(DoubleSolenoid.Value.kOff);

        pivot = SparkMAXUtil.generateGenericSparkMAX(8, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public double deadband(double x, double min) {
        if (Math.abs(x) < min) {
            return 0;
        } else {
            return x;
        }
    }


    public void handleDrive() {
        driveTrain.setOpenLoop(deadband(-driver.getRawAxis(5), 0.1)*0.5, deadband(-driver.getRawAxis(1), 0.1)*0.5);
        if (driver.getPOV() == 0) {
            System.out.println(pivot.getBusVoltage());
            pivot.set(-0.6);
        } else if (driver.getPOV() == 180){
            pivot.set(0.6);
        }
        else {
            pivot.set(0.0);
        }
}

    public void update() {
        handleDrive();
        handlePneumatics();
    }

    public void handlePneumatics() {
//        if (driver.getRawAxis(2) > 0.25) {
//            leftShoot.set(true);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            leftShoot.set(false);
//        }
//        else if (driver.getRawAxis(3) > 0.25) {
//            rightShoot.set(true);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            rightShoot.set(false);
//        }
//        else if (driver.getRawButtonPressed(3)) {
//            if (leftEjectToggle) {
//                leftEject.set(DoubleSolenoid.Value.kForward);
//            } else {
//                leftEject.set(DoubleSolenoid.Value.kReverse);
//            }
//            leftEjectToggle = !leftEjectToggle;
//        }
//        else if (driver.getRawButtonPressed(2)) {
//            if (rightEjectToggle) {
//                rightEject.set(DoubleSolenoid.Value.kForward);
//            } else {
//                rightEject.set(DoubleSolenoid.Value.kReverse);
//            }
//            rightEjectToggle = !rightEjectToggle;
//        }
//        else if (driver.getRawButtonPressed(6)) {
//            if (leftPopToggle) {
//                leftPop.set(DoubleSolenoid.Value.kForward);
//            } else {
//                leftPop.set(DoubleSolenoid.Value.kReverse);
//            }
//            leftPopToggle = !leftPopToggle;
//        }
//        else if (driver.getRawButtonPressed(5)) {
//            if (rightPopToggle) {
//                rightPop.set(DoubleSolenoid.Value.kForward);
//            } else {
//                rightPop.set(DoubleSolenoid.Value.kReverse);
//            }
//            rightPopToggle = !rightPopToggle;
//        }
        if (driver.getRawButtonPressed(3)) {
            rightEject.set(DoubleSolenoid.Value.kForward);
        }
        else if (driver.getRawButtonPressed(1)) {
            rightEject.set(DoubleSolenoid.Value.kReverse);
    }
        else if (driver.getRawButtonPressed(2)) {
            leftEject.set(DoubleSolenoid.Value.kForward);
    }
        else if (driver.getRawButtonPressed(4)) {
            leftEject.set(DoubleSolenoid.Value.kReverse);
    }
        else if (driver.getRawButtonPressed(5)) {
            rightPop.set(DoubleSolenoid.Value.kForward);
        }
        else if (driver.getRawAxis(2) > 0.25) {
            rightPop.set(DoubleSolenoid.Value.kReverse);
        }
        else if (driver.getRawButtonPressed(6)) {
            leftPop.set(DoubleSolenoid.Value.kForward);
        }
        else if (driver.getRawAxis(3) > 0.25) {
            leftPop.set(DoubleSolenoid.Value.kReverse);
        }
        leftShoot.set(false);
        rightShoot.set(false);
    }
}

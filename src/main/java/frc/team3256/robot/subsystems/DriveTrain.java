package frc.team3256.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.operations.PIDController;
import frc.team3256.warriorlib.control.DrivePower;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.loop.Loop;
import frc.team3256.warriorlib.math.Rotation;
import frc.team3256.warriorlib.subsystem.DriveTrainBase;

import static frc.team3256.robot.constants.DriveTrainConstants.*;

public class DriveTrain extends DriveTrainBase implements Loop {

    private static DriveTrain instance;
    private static double prevTurn = 0.0;
    private CANSparkMax leftMaster, rightMaster, leftSlave, rightSlave;
    private CANEncoder leftEncoder, rightEncoder;
    private CANPIDController leftPIDController, rightPIDController;
    private DoubleSolenoid shifter;
    private boolean init = false;
    private PigeonIMU gyro;
    private PIDController turnPIDController = new PIDController(kTurnP, kTurnI, kTurnD);

    private DriveTrain() {
        //gyro = new PigeonIMU(14);
        //gyro.setAccumZAngle(0, 0);
        //gyro.setYaw(0, 0);
        //        internalGyro = new ADXRS453_Gyro();
        //        internalGyro.startCalibrate();
        leftMaster = SparkMAXUtil.generateGenericSparkMAX(kLeftDriveMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave = SparkMAXUtil.generateSlaveSparkMAX(kLeftDriveSlave, CANSparkMaxLowLevel.MotorType.kBrushless, leftMaster);
        //leftSlave2 = TalonSRXUtil.generateSlaveTalon(kLeftDriveSlave2, kLeftDriveMaster);
        rightMaster = SparkMAXUtil.generateGenericSparkMAX(kRightDriveMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave = SparkMAXUtil.generateSlaveSparkMAX(kRightDriveSlave, CANSparkMaxLowLevel.MotorType.kBrushless, rightMaster);

        leftEncoder = leftMaster.getEncoder();
        rightEncoder = rightMaster.getEncoder();
        leftPIDController = leftMaster.getPIDController();
        rightPIDController = rightMaster.getPIDController();

        SparkMAXUtil.setPIDGains(leftPIDController, 0, kVelocityP, kVelocityI, kVelocityD, kVelocityF, kVelocityIZone);
        SparkMAXUtil.setPIDGains(rightPIDController, 0, kVelocityP, kVelocityI, kVelocityD, kVelocityF, kVelocityIZone);

        SparkMAXUtil.setSmartMotionParams(leftPIDController, -kVelocityMaxRPM, kVelocityMaxRPM, kMaxAccel, kAllowedErr, 0);
        SparkMAXUtil.setSmartMotionParams(rightPIDController, -kVelocityMaxRPM, kVelocityMaxRPM, kMaxAccel, kAllowedErr, 0);

        SparkMAXUtil.setBrakeMode(leftMaster, leftSlave, rightMaster, rightSlave);

        /*leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*loopTime), 0);

        leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*loopTime), 0);
        */
        shifter = new DoubleSolenoid(15, kShifterForward, kShifterReverse);
        //shifter = new DoubleSolenoid(15, 3, 4);

        //rightSlave2 = TalonSRXUtil.generateSlaveTalon(kRightDriveSlave2, kRightDriveMaster);
        //gyro.calibrate();
        rightMaster.setInverted(false);
        leftMaster.setInverted(true);

//        leftMaster.setClosedLoopRampRate(0.0);
//        rightMaster.setClosedLoopRampRate(0.0);
    }

    public static DriveTrain getInstance() {
        return instance == null ? instance = new DriveTrain() : instance;
    }

    public static DrivePower curvatureDrive(double throttle, double turn, boolean quickTurn, boolean highGear) {
        //boolean highGear = true;
        if (Math.abs(turn) <= 0.15) { //deadband
            turn = 0;
        }
        if (Math.abs(throttle) <= 0.15) {
            throttle = 0;
        }
        double angularPower, overPower;

        if (quickTurn) {
            highGear = false;
            if (Math.abs(throttle) < 0.2) {
                quickStopAccumulator = (1 - kQuickStopAlpha) * quickStopAccumulator + kQuickStopAlpha * clamp(turn) * kQuickStopScalar;
            }
            overPower = 1.0;
            angularPower = turn/kAngularPowerScalar;
            if (Math.abs(turn - prevTurn) > kQuickTurnDeltaLimit) {
                //System.out.println("TURN: " + turn);
                //System.out.println("PREVIOUS TURN: " + prevTurn);
                if (turn > 0) {
                    angularPower = prevTurn + kQuickTurnDeltaLimit;
                    //System.out.println("ANGULAR POWER: " + angularPower);
                } else
                    angularPower = prevTurn - kQuickTurnDeltaLimit;
            }
        } else {
            overPower = 0.0;
            angularPower = (Math.abs(throttle) * turn - quickStopAccumulator)/kAngularPowerScalar;
            if (quickStopAccumulator > 1) {
                quickStopAccumulator -= 1;
            } else if (quickStopAccumulator < -1) {
                quickStopAccumulator += 1;
            } else {
                quickStopAccumulator = 0.0;
            }
        }
        prevTurn = turn;
        double left = throttle + angularPower;
        double right = throttle - angularPower;
        if (left > 1.0) {
            right -= overPower * (left - 1.0);
            left = 1.0;
        } else if (right > 1.0) {
            left -= overPower * (right - 1.0);
            right = 1.0;
        } else if (left < -1.0) {
            right += overPower * (-1.0 - left);
            left = -1.0;
        } else if (right < -1.0) {
            left += overPower * (-1.0 - right);
            right = -1.0;
        }
        //System.out.println("FEEDING LEFT " + left + " RIGHT " + right);
        return new DrivePower(left, right, highGear);
    }

    public static double clamp(double val) {
        return Math.max(Math.min(val, 1.0), -1.0);
    }

    public void setOpenLoop(double leftPower, double rightPower) {
        leftPower *= leftPower * leftPower;
        rightPower *= rightPower * rightPower;
        leftPIDController.setReference(leftPower*kVelocityMaxRPM, ControlType.kVelocity);
        rightPIDController.setReference(rightPower*kVelocityMaxRPM, ControlType.kVelocity);
    }

    public void setHangDrive(double leftPower, double rightPower) {
        //leftHangDrive.set(ControlMode.PercentOutput, leftPower);
        //rightHangDrive.set(ControlMode.PercentOutput, rightPower);
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
        this.outputToDashboard();
    }

    @Override
    public void end(double timestamp) {
        setOpenLoop(0, 0);
    }

    public double getLeftCurrent() {
        return leftMaster.getOutputCurrent();
    }

    public double getRightCurrent() {
        return rightMaster.getOutputCurrent();
    }

    /**
     * Native NEO unit is RPM so convert it to inches per second
     */
    public double getLeftVelocity() {
        return rpmToInchesPerSec(leftEncoder.getVelocity()) / kGearRatio;
    }

    public double getLeftRPM() {
        return leftEncoder.getVelocity();
    }

    public double getRightVelocity() {
        return rpmToInchesPerSec(rightEncoder.getVelocity()) / kGearRatio;
    }

    public double getRightRPM() {
        return rightEncoder.getVelocity();
    }

    public double getLeftDistance() {
        return rotationsToInches(leftEncoder.getPosition()) / kGearRatio;
    }

    public double getRightDistance() {
        return rotationsToInches(rightEncoder.getPosition()) / kGearRatio;
    }

    public void resetEncoders() {
        leftMaster.setEncPosition(0);
        rightMaster.setEncPosition(0);
    }

    public double inchesToRotations(double inches) {
        return inches / (kWheelDiameter * Math.PI);
    }

    public double rotationsToInches(double rotations) {
        return rotations * Math.PI * kWheelDiameter;
    }

    public double inchesPerSecToRPM(double inchesPerSec) {
        return inchesToRotations(inchesPerSec) * 60D;
    }

    public double rpmToInchesPerSec(double rpm) {
        return rotationsToInches(rpm) / 60D;
    }

    public PigeonIMU getGyro() {
        return gyro;
    }

    public double getAngle() {
        return 0;
//        double[] ypr = new double[3];
//        gyro.getYawPitchRoll(ypr);
//        return ypr[0];
        //        return -internalGyro.getAngle();
    }

    public Rotation getRotationAngle() {
        return Rotation.fromDegrees(getAngle() + 90);
    }

    public void resetGyro() {
        //gyro.setYaw(0, 0);
        //gyro.setAccumZAngle(0, 0);
        //        internalGyro.reset();
    }

    public void setHighGear(boolean highGear) {
        //shifter.set(highGear ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void setVelocityClosedLoop(double leftVelInchesPerSec, double rightVelInchesPerSec) {
        /*double leftOutput = leftVelocityPIDController.calculatePID(leftVelInchesPerSec, getLeftVelocity());
        double rightOutput = rightVelocityPIDController.calculatePID(rightVelInchesPerSec, getRightVelocity());
        leftMaster.set(ControlMode.Velocity,inchesPerSecToSensorUnits(leftOutput));
        rightMaster.set(ControlMode.Velocity,inchesPerSecToSensorUnits(rightOutput));*/

        leftPIDController.setReference(inchesPerSecToRPM(leftVelInchesPerSec) * kGearRatio, ControlType.kVelocity);
        rightPIDController.setReference(inchesPerSecToRPM(rightVelInchesPerSec) * kGearRatio, ControlType.kVelocity);;
    }

    public void setBrakeMode() {
        SparkMAXUtil.setBrakeMode(leftMaster, leftSlave, rightMaster, rightSlave);
    }

    public void setCoastMode() {
        SparkMAXUtil.setCoastMode(leftMaster, leftSlave, rightMaster, rightSlave);
    }

}

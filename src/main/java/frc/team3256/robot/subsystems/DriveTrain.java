package frc.team3256.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.robot.operations.PIDController;
import frc.team3256.robot.operations.Range;
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
    private PIDController alignPIDController;
    private double gyroOffset = 0;

    private static final double kThrottleDeadband = 0.02;

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
        double left, right;

        left = throttle + angularPower;
        right = throttle - angularPower;

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
        if (!quickTurn) { //we only want cubic drive if we aren't quickturning
            left *= left * left;
            right *= right * right;
        }
        return new DrivePower(left, right, highGear);
    }

    private static final double kWheelDeadband = 0.02;
    private static final double kTurnSensitivity = 1.0;
    double mQuickStopAccumulator;

    private DriveTrain() {
        gyro = new PigeonIMU(14);
        gyro.setAccumZAngle(0, 0);
        gyro.setYaw(0, 0);
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

        SparkMAXUtil.setPIDGains(leftPIDController, kVelocityLowGearSlot, kVelocityLowGearP, kVelocityLowGearI, kVelocityLowGearD, kVelocityLowGearF, kVelocityLowGearIZone);
        SparkMAXUtil.setPIDGains(rightPIDController, kVelocityLowGearSlot, kVelocityLowGearP, kVelocityLowGearI, kVelocityLowGearD, kVelocityLowGearF, kVelocityLowGearIZone);
        SparkMAXUtil.setPIDGains(leftPIDController, kVelocityHighGearSlot, kVelocityHighGearP, kVelocityHighGearI, kVelocityHighGearD, kVelocityHighGearF, kVelocityHighGearIZone);
        SparkMAXUtil.setPIDGains(rightPIDController, kVelocityHighGearSlot, kVelocityHighGearP, kVelocityHighGearI, kVelocityHighGearD, kVelocityHighGearF, kVelocityHighGearIZone);

        SparkMAXUtil.setSmartMotionParams(leftPIDController, -kVelocityMaxRPM, kVelocityMaxRPM, kMaxAccel, kAllowedErr, kVelocityLowGearSlot);
        SparkMAXUtil.setSmartMotionParams(rightPIDController, -kVelocityMaxRPM, kVelocityMaxRPM, kMaxAccel, kAllowedErr, kVelocityLowGearSlot);
        SparkMAXUtil.setSmartMotionParams(leftPIDController, -kVelocityMaxRPM, kVelocityMaxRPM, kMaxAccel, kAllowedErr, kVelocityHighGearSlot);
        SparkMAXUtil.setSmartMotionParams(rightPIDController, -kVelocityMaxRPM, kVelocityMaxRPM, kMaxAccel, kAllowedErr, kVelocityHighGearSlot);

        SparkMAXUtil.setBrakeMode(leftMaster, leftSlave, rightMaster, rightSlave);

        /*leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*loopTime), 0);

        leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*loopTime), 0);
        */
        shifter = new DoubleSolenoid(DriveTrainConstants.pcmId, kShifterForward, kShifterReverse);
        //shifter = new DoubleSolenoid(15, 3, 4);

        //rightSlave2 = TalonSRXUtil.generateSlaveTalon(kRightDriveSlave2, kRightDriveMaster);
        //gyro.calibrate();
        rightMaster.setInverted(false); //false
        leftMaster.setInverted(true); //true

        alignPIDController  = new PIDController(kAlignkP, kAlignkI, kAlignkD);

//        leftMaster.setClosedLoopRampRate(0.0);
//        rightMaster.setClosedLoopRampRate(0.0);
    }

    public DrivePower autoAlignAssist(double throttle, double pixelDisplacement) {
        if (throttle == 0) {
            return new DrivePower(0,0,false);
        }
        double turnOutput = alignPIDController.calculatePID(pixelDisplacement, 0);
        turnOutput = Range.clip(turnOutput, -1, 1);
        double leftOutput = throttle + turnOutput;
        double rightOutput = throttle - turnOutput;
        return new DrivePower(leftOutput, rightOutput, false);
    }


    public DrivePower betterCurvatureDrive(double throttle, double wheel, boolean isQuickTurn, boolean highGear) {
        wheel = handleDeadband(wheel, kWheelDeadband);
        throttle = handleDeadband(throttle, kThrottleDeadband);

        double overPower;
        double angularPower;

        if (isQuickTurn) {
            highGear = false;
            if (Math.abs(throttle) < 0.2) {
                double alpha = 0.1;
                mQuickStopAccumulator = (1 - alpha) * mQuickStopAccumulator + alpha * Math.min(1.0, Math.max(-1.0, wheel)) * 2;
            }
            overPower = 1.0;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * kTurnSensitivity - mQuickStopAccumulator;
            if (mQuickStopAccumulator > 1) {
                mQuickStopAccumulator -= 1;
            } else if (mQuickStopAccumulator < -1) {
                mQuickStopAccumulator += 1;
            } else {
                mQuickStopAccumulator = 0.0;
            }
        }

        double rightPwm = throttle - angularPower;
        double leftPwm = throttle + angularPower;
        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-1.0 - leftPwm);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-1.0 - rightPwm);
            rightPwm = -1.0;
        }

        return new DrivePower(leftPwm, rightPwm, highGear);
    }

    public double handleDeadband(double val, double deadband) {
        return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }

    public static double clamp(double val) {
        return Math.max(Math.min(val, 1.0), -1.0);
    }

    public void setPowerClosedLoop(double leftPower, double rightPower, boolean highGear) {
        leftPIDController.setReference(leftPower*kVelocityMaxRPM, ControlType.kVelocity, highGear ? kVelocityHighGearSlot : kVelocityLowGearSlot);
        rightPIDController.setReference(rightPower*kVelocityMaxRPM, ControlType.kVelocity, highGear ? kVelocityHighGearSlot : kVelocityLowGearSlot);
    }

    public void setPowerOpenLoop(double leftPower, double rightPower) {
        leftMaster.set(leftPower);
        rightMaster.set(rightPower);
    }

    /**
     * Runs to zero power right away
     */
    public void runZeroPower() {
        leftMaster.set(0);
        rightMaster.set(0);
    }

    @Override
    public void outputToDashboard() {
        //SmartDashboard.putNumber("right encoder", getRightDistance());
        //SmartDashboard.putNumber("left encoder", getLeftDistance());
        SmartDashboard.putNumber("Left A Temp", celsiusToFahrenheit(leftMaster.getMotorTemperature()));
        SmartDashboard.putNumber("Left B Temp", celsiusToFahrenheit(leftSlave.getMotorTemperature()));
        SmartDashboard.putNumber("Right A Temp", celsiusToFahrenheit(rightMaster.getMotorTemperature()));
        SmartDashboard.putNumber("Right B Temp", celsiusToFahrenheit(rightSlave.getMotorTemperature()));
        SmartDashboard.putNumber("Left RPM", this.getLeftRPM());
        SmartDashboard.putNumber("Right RPM", this.getRightRPM());
    }

    public double celsiusToFahrenheit(double c) {
        return (c * 9.0/5.0) + 32.0;
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
        setPowerClosedLoop(0, 0, false);
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
        double[] ypr = new double[3];
        gyro.getYawPitchRoll(ypr);
        return ypr[0] + gyroOffset;
    }

    public Rotation getRotationAngle() {
        return Rotation.fromDegrees(getAngle() + 90);
    }

    public void resetGyro() {
        gyro.setYaw(0, 0);
        gyro.setAccumZAngle(0, 0);
        //internalGyro.reset();
    }

    public void setGyroOffset(double offset) {
        this.gyroOffset = offset;
    }

    public void setHighGear(boolean highGear) {
        shifter.set(highGear ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void setVelocityClosedLoop(double leftVelInchesPerSec, double rightVelInchesPerSec) {
        /*double leftOutput = leftVelocityPIDController.calculatePID(leftVelInchesPerSec, getLeftVelocity());
        double rightOutput = rightVelocityPIDController.calculatePID(rightVelInchesPerSec, getRightVelocity());
        leftMaster.set(ControlMode.Velocity,inchesPerSecToSensorUnits(leftOutput));
        rightMaster.set(ControlMode.Velocity,inchesPerSecToSensorUnits(rightOutput));*/

        leftPIDController.setReference(inchesPerSecToRPM(leftVelInchesPerSec) * kGearRatio, ControlType.kVelocity, kVelocityHighGearSlot);
        rightPIDController.setReference(inchesPerSecToRPM(rightVelInchesPerSec) * kGearRatio, ControlType.kVelocity, kVelocityHighGearSlot);
    }

    public void setBrakeMode() {
        SparkMAXUtil.setBrakeMode(leftMaster, leftSlave, rightMaster, rightSlave);
    }

    public void setCoastMode() {
        SparkMAXUtil.setCoastMode(leftMaster, leftSlave, rightMaster, rightSlave);
    }

}

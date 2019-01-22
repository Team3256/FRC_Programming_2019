package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.sun.org.apache.bcel.internal.Const;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import frc.team3256.robot.math.Rotation;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.operations.DrivePower;
import frc.team3256.robot.operations.PIDController;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.loop.Loop;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class DriveTrain extends SubsystemBase implements Loop {

    private static DriveTrain instance;
    public WPI_TalonSRX leftMaster, rightMaster, leftSlave, rightSlave;//, leftSlave2, rightSlave2;
    private DoubleSolenoid shifter;
    private boolean init = false;
    private PigeonIMU gyro;
    private static double prevTurn = 0.0;
    private PIDController velocityPIDController;

    public static DriveTrain getInstance() {
        return instance == null ? instance = new DriveTrain() : instance;
    }

    private DriveTrain() {
        gyro = new PigeonIMU(0);
        gyro.setAccumZAngle(0, 0);
        gyro.setYaw(0, 0);
        velocityPIDController = new PIDController(Constants.velkP, Constants.velkI, Constants.velkD);
        leftMaster = TalonSRXUtil.generateGenericTalon(Constants.kLeftDriveMaster);
        leftSlave = TalonSRXUtil.generateSlaveTalon(Constants.kLeftDriveSlave, Constants.kLeftDriveMaster);
        //leftSlave2 = TalonSRXUtil.generateSlaveTalon(Constants.kLeftDriveSlave2, Constants.kLeftDriveMaster);
        rightMaster = TalonSRXUtil.generateGenericTalon(Constants.kRightDriveMaster);
        rightSlave = TalonSRXUtil.generateSlaveTalon(Constants.kRightDriveSlave, Constants.kRightDriveMaster);

        /*leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*Constants.loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*Constants.loopTime), 0);

        leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*Constants.loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*Constants.loopTime), 0);
        */

        shifter = new DoubleSolenoid(Constants.kShifterForward, Constants.kShifterReverse);

        TalonSRXUtil.configMagEncoder(leftMaster);
        TalonSRXUtil.configMagEncoder(rightMaster);

        leftMaster.setSensorPhase(false);
        rightMaster.setSensorPhase(false);
        //rightSlave2 = TalonSRXUtil.generateSlaveTalon(Constants.kRightDriveSlave2, Constants.kRightDriveMaster);
        //gyro.calibrate();
        rightMaster.setInverted(false);
        rightSlave.setInverted(InvertType.FollowMaster);

        leftMaster.setInverted(true);
        leftSlave.setInverted(InvertType.FollowMaster);


        leftMaster.setSelectedSensorPosition(0, 0,100);
        rightMaster.setSelectedSensorPosition(0, 0,100);
    }

    public void setOpenLoop(double leftPower, double rightPower) {
        if (!init){
            leftMaster.enableVoltageCompensation(false);
            rightMaster.enableVoltageCompensation(false);
            leftSlave.enableVoltageCompensation(false);
            rightSlave.enableVoltageCompensation(false);
//            leftSlave2.enableVoltageCompensation(false);
//            rightSlave2.enableVoltageCompensation(false);
            TalonSRXUtil.setCoastMode(leftMaster, leftSlave, rightMaster, rightSlave);
            init = true;
        }
        leftMaster.set(ControlMode.PercentOutput, leftPower);
        rightMaster.set(ControlMode.PercentOutput, rightPower);
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

    }

    @Override
    public void end(double timestamp) {
        setOpenLoop(0,0);
    }

    public double getVelocity() {
        return (leftMaster.getSelectedSensorVelocity() + rightMaster.getSelectedSensorVelocity())/2.0;
    }

    public double getLeftVelocity() {
        return leftMaster.getSelectedSensorVelocity();
    }


    public double getRightVelocity() {
        return rightMaster.getSelectedSensorVelocity();
    }

    public double getLeftDistance() {
        return sensorUnitsToInches(leftMaster.getSelectedSensorPosition(0));
    }

    public double getRightDistance() {
        return sensorUnitsToInches(rightMaster.getSelectedSensorPosition(0));
    }

    public double getAverageDistance(){
        return (getLeftDistance() + getRightDistance())/2.0;
    }

    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 0);
        rightMaster.setSelectedSensorPosition(0, 0,0);
    }

    public double inchesToSensorUnits(double inches) {
        return (inches * 4096.0)/(Constants.kWheelDiameter * Math.PI) * Constants.kDriveEncoderScalingFactor;
    }

    //Sensor units for velocity are encoder units per 100 ms
    public double inchesPerSecToSensorUnits(double inchesPerSec){
        return inchesToSensorUnits(inchesPerSec)/10.0;
    }

    public double inchesPerSec2ToSensorUnits(double inchesPerSec2){
        return inchesPerSecToSensorUnits(inchesPerSec2)/10.0;
    }

    public double sensorUnitsToInches(double sensorUnits){
        return (sensorUnits*Math.PI*Constants.kWheelDiameter)/4096/Constants.kDriveEncoderScalingFactor;
    }

    public double sensorUnitsToInchesPerSec(double sensorUnits){
        return sensorUnitsToInches(sensorUnits*10.0);
    }

    public PigeonIMU getGyro(){
        return gyro;
    }

    public double getAngle(){
        double[] ypr = new double[3];
        gyro.getYawPitchRoll(ypr);
        return ypr[0];
    }

    public Rotation getRotationAngle(){
        return Rotation.fromDegrees(getAngle());
    }

    public void resetGyro(){
        gyro.setYaw(0, 0);
        gyro.setAccumZAngle(0, 0);
    }

    public static DrivePower curvatureDrive(double throttle, double turn, boolean quickTurn, boolean highGear){
        if (Math.abs(turn) <= 0.15) { //deadband
            turn = 0;
        }

        double angularPower, overPower;

                if (quickTurn){
                    highGear = false;
                    if (Math.abs(throttle) < 0.2){
                        Constants.quickStopAccumulator = (1-Constants.kQuickStopAlpha)* Constants.quickStopAccumulator + Constants.kQuickStopAlpha * clamp(turn) * Constants.kQuickStopScalar;
                    }
                    overPower = 1.0;
                    angularPower = turn/1.1;
                    if (Math.abs(turn - prevTurn) > Constants.kQuickTurnDeltaLimit){
                        //System.out.println("TURN: " + turn);
                        //System.out.println("PREVIOUS TURN: " + prevTurn);
                        if (turn > 0){
                            angularPower = prevTurn + Constants.kQuickTurnDeltaLimit;
                            //System.out.println("ANGULAR POWER: " + angularPower);
                        }
                        else angularPower = prevTurn - Constants.kQuickTurnDeltaLimit;
                    }
                }
                else{
                    overPower = 0.0;
                    angularPower = Math.abs(throttle)*turn - Constants.quickStopAccumulator;
                    if (Constants.quickStopAccumulator > 1){
                        Constants.quickStopAccumulator -= 1;
            }
            else if (Constants.quickStopAccumulator < -1){
                Constants.quickStopAccumulator += 1;
            }
            else{
                Constants.quickStopAccumulator = 0.0;
            }
        }
        prevTurn = turn;
        double left = throttle + angularPower;
        double right = throttle - angularPower;
        if (left > 1.0) {
            right -= overPower*(left - 1.0);
            left = 1.0;
        }
        else if (right > 1.0){
            left -= overPower*(right - 1.0);
            right = 1.0;
        }
        else if (left < -1.0){
            right += overPower*(-1.0 - left);
            left = -1.0;
        }
        else if (right < -1.0){
            left += overPower*(-1.0 - right);
            right = -1.0;
        }
        return new DrivePower(left, right, highGear);
    }

    public static double clamp(double val){
        return Math.max(Math.min(val, 1.0), -1.0);
    }

    public void setHighGear(boolean highGear) {
        shifter.set(highGear ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void setVelocityClosedLoop(double leftVelInchesPerSec, double rightVelInchesPerSec) {
        double leftOutput = velocityPIDController.calculatePID(leftVelInchesPerSec, getLeftVelocity());
        double rightOutput = velocityPIDController.calculatePID(rightVelInchesPerSec, getRightVelocity());
        leftMaster.set(ControlMode.Velocity,inchesPerSecToSensorUnits(leftOutput));
        rightMaster.set(ControlMode.Velocity,inchesPerSecToSensorUnits(rightOutput));
    }

    private static double inchesToRotations(double inches) {
        return inches / (Constants.kWheelDiameter * Math.PI);
    }

    private static double inchesPerSecondToRpm(double inches_per_second) {
        return inchesToRotations(inches_per_second) * 60;
    }



}

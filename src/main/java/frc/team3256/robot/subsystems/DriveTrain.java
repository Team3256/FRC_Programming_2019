package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.ADXRS453_Gyro;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.loop.Loop;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class DriveTrain extends SubsystemBase implements Loop {

    private static DriveTrain instance;
    public WPI_TalonSRX leftMaster, rightMaster, leftSlave, rightSlave;//, leftSlave2, rightSlave2;
    private boolean init = false;
    private ADXRS450_Gyro gyro;

    public static DriveTrain getInstance() {
        return instance == null ? instance = new DriveTrain() : instance;
    }

    private DriveTrain() {
        gyro = new ADXRS450_Gyro();
        leftMaster = TalonSRXUtil.generateGenericTalon(Constants.kLeftDriveMaster);
        leftSlave = TalonSRXUtil.generateSlaveTalon(Constants.kLeftDriveSlave, Constants.kLeftDriveMaster);
        //leftSlave2 = TalonSRXUtil.generateSlaveTalon(Constants.kLeftDriveSlave2, Constants.kLeftDriveMaster);
        rightMaster = TalonSRXUtil.generateGenericTalon(Constants.kRightDriveMaster);
        rightSlave = TalonSRXUtil.generateSlaveTalon(Constants.kRightDriveSlave, Constants.kRightDriveMaster);

        leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*Constants.loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*Constants.loopTime), 0);

        leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*Constants.loopTime), 0);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*Constants.loopTime), 0);

        leftSlave.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*Constants.loopTime), 0);
        rightSlave.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000*Constants.loopTime), 0);

        leftSlave.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*Constants.loopTime), 0);
        rightSlave.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000*Constants.loopTime), 0);

        TalonSRXUtil.configMagEncoder(leftMaster);
        TalonSRXUtil.configMagEncoder(rightMaster);
        TalonSRXUtil.configMagEncoder(leftSlave);
        TalonSRXUtil.configMagEncoder(rightSlave);

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
            TalonSRXUtil.setBrakeMode(leftMaster, leftSlave, rightMaster, rightSlave);
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
        gyro.reset();
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

    public double getLeftDistance() {
        return sensorUnitsToInches(leftMaster.getSelectedSensorPosition(0));
    }

    public double getLeftSlaveDistance() {
        return sensorUnitsToInches(leftSlave.getSelectedSensorPosition(0));
    }

    public double getRightDistance() {
        return sensorUnitsToInches(rightMaster.getSelectedSensorPosition(0));
    }

    public double getRightSlaveDistance() {
        return sensorUnitsToInches(rightSlave.getSelectedSensorPosition());
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

    public ADXRS450_Gyro getGyro(){
        return gyro;
    }

    public double getAngle(){
        //Return negative value of the gyro, because the gyro returns
        return -gyro.getAngle();
    }

    public void resetGyro(){
        gyro.reset();
    }

}

package frc.team3256.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.TalonUtil;
import frc.team3256.robot.constants.Constants;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.loop.Loop;
import frc.team3256.warriorlib.math.Rotation;
import frc.team3256.warriorlib.subsystem.DriveTrainBase;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class DriveTrain extends DriveTrainBase implements Loop {

    private static DriveTrain instance;
    //private TalonSRX leftMaster, rightMaster, leftSlave, rightSlave;
    private PWMTalonSRX leftMaster, rightMaster, leftSlave, rightSlave;

    public static DriveTrain getInstance() {
        return instance == null ? instance = new DriveTrain() : instance;
    }

    @Override
    public void outputToDashboard() {
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void init(double timestamp) {
        setOpenLoop(0, 0);
        //leftMaster.setSelectedSensorPosition(0, 0,0);
        //rightMaster.setSelectedSensorPosition(0, 0,0);
    }

    @Override
    public void update(double timestamp) {

    }

    @Override
    public void end(double timestamp) {
        setOpenLoop(0, 0);
    }

    private DriveTrain() {
//        // create talon objects
//        leftMaster = TalonSRXUtil.generateGenericTalon(Constants.kLeftDriveMaster);
//        leftSlave = TalonSRXUtil.generateSlaveTalon(Constants.kLeftDriveSlave, Constants.kLeftDriveMaster);
//        rightMaster = TalonSRXUtil.generateGenericTalon(Constants.kRightDriveMaster);
//        rightSlave = TalonSRXUtil.generateSlaveTalon(Constants.kRightDriveSlave, Constants.kRightDriveMaster);

        leftMaster = new PWMTalonSRX(Constants.kLeftDriveMaster);
        leftSlave = new PWMTalonSRX(Constants.kLeftDriveSlave);
        rightMaster = new PWMTalonSRX(Constants.kRightDriveMaster);
        rightSlave = new PWMTalonSRX(Constants.kRightDriveSlave);

//        leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000* Constants.loopTime), 0);
//        rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000* Constants.loopTime), 0);
//
//        leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000* Constants.loopTime), 0);
//        rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, (int)(1000* Constants.loopTime), 0);

        leftMaster.setInverted(true);
        leftSlave.setInverted(true);
        rightMaster.setInverted(false);
        rightSlave.setInverted(false);
    }

    @Override
    public double getLeftDistance() {
        return 0;
    }

    @Override
    public double getRightDistance() {
        return 0;
    }

    @Override
    public Rotation getRotationAngle() {
        return null;
    }

    @Override
    public double getLeftVelocity() {
        return 0;
    }

    @Override
    public double getRightVelocity() {
        return 0;
    }

    @Override
    public void setVelocityClosedLoop(double left, double right) {

    }

    @Override
    public void resetEncoders() {

    }

    @Override
    public void resetGyro() {

    }

//    public double getLeftOutputVoltage() {
//        return leftMaster.getMotorOutputVoltage();
//    }
//
//    public double getRightOutputVoltage() {
//        return rightMaster.getMotorOutputVoltage();
//    }

    public void setOpenLoop(double leftPower, double rightPower) {
//        leftMaster.enableVoltageCompensation(false);
//        rightMaster.enableVoltageCompensation(false);
//        leftSlave.enableVoltageCompensation(false);
//        rightSlave.enableVoltageCompensation(false);
//        TalonUtil.setBrakeMode(leftMaster, leftSlave, rightMaster, rightSlave);
//        leftMaster.set(ControlMode.PercentOutput, leftPower);
//        rightMaster.set(ControlMode.PercentOutput, rightPower);
        leftMaster.set(leftPower);
        leftSlave.set(leftPower);
        rightMaster.set(rightPower);
        rightSlave.set(rightPower);
    }

    public void setBrake(){
        //leftMaster.set(ControlMode.PercentOutput, 0);
        //rightMaster.set(ControlMode.PercentOutput, 0);
    }

}
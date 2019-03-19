package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.HatchConstants.*;

public class HatchPivot extends SubsystemBase {

    private static HatchPivot instance;
    private WPI_TalonSRX hatchPivot;
    private DoubleSolenoid deployHatch, brake;
    private Ultrasonic ultrasonicHatch;
    public boolean hasHatch = false;

    private HatchPivot() {
        hatchPivot = TalonSRXUtil.generateGenericTalon(kHatchPivotPort);

        hatchPivot.setInverted(true);

        deployHatch = new DoubleSolenoid(DriveTrainConstants.pcmId, kHatchForwardChannel, kHatchReverseChannel);
        brake = new DoubleSolenoid(DriveTrainConstants.pcmId, kRatchetForwardChannel, kRatchetReverseChannel);

        //ultrasonicHatch = new Ultrasonic(kHatchPingChannel,kHatchEchoChannel);

        TalonSRXUtil.configMagEncoder(hatchPivot);

        TalonSRXUtil.setBrakeMode(hatchPivot);

        TalonSRXUtil.setPIDGains(hatchPivot, 0, kHatchP, kHatchI, kHatchD, kHatchF);

        hatchPivot.configMotionCruiseVelocity(18000, 0);
        hatchPivot.configMotionAcceleration(12000, 0); //12000

        hatchPivot.selectProfileSlot(0, 0);
        hatchPivot.setSensorPhase(true);
        //hatchPivot.configSetParameter(ParamEnum.nom)
    }

    public static HatchPivot getInstance() {return instance == null ? instance = new HatchPivot(): instance;}

    public double getCurrent() {
        return hatchPivot.getOutputCurrent();
    }

    public void setCoast() {
        TalonSRXUtil.setCoastMode(hatchPivot);
    }

    public void setBrake() {
        TalonSRXUtil.setBrakeMode(hatchPivot);
    }

    public void deployHatch() {
        deployHatch.set(DoubleSolenoid.Value.kForward);
    }

    public void retractHatch() {
        deployHatch.set(DoubleSolenoid.Value.kReverse);
    }

    public void releaseBrake() {
        brake.set(DoubleSolenoid.Value.kReverse);
    }

    public void engageBrake() {
        brake.set(DoubleSolenoid.Value.kForward);
    }

    public DoubleSolenoid.Value getBrakeStatus() {
        return brake.get();
    }

    public void setHatchPivotPower(double speed) {
        hatchPivot.set(speed);
    }

    public void setHatchPivotVelocity(double percentage) {
        //hatchPivot.set(ControlMode.Velocity, percentage * kMaxVel);
        // pls implement
    }

    public void setPositionCargoIntake() {
        //hatchPivot.set(ControlMode.MotionMagic, angleToSensorUnits(kPositionCargoIntake), DemandType.ArbitraryFeedForward, 0);
    }

    public void setPositionFoldIn() {
        //hatchPivot.set(ControlMode.MotionMagic, angleToSensorUnits(kPositionFoldIn), DemandType.ArbitraryFeedForward, 0);
    }

    public void setPositionDeploy() {
        //hatchPivot.set(ControlMode.MotionMagic, angleToSensorUnits(kPositionDeployHatch), DemandType.ArbitraryFeedForward, 0);
    }

    private double angleToSensorUnits(double degrees) {
        return (degrees/360.0) * kHatchPivotGearRatio * 4096.0;
    }

    private double sensorUnitsToAngle(double ticks) {
        return ((ticks / 4096.0) / kHatchPivotGearRatio) * 360.0;
    }

    public double getAngle() {
        return sensorUnitsToAngle(hatchPivot.getSelectedSensorPosition(0));
    }

    @Override
    public void update(double timestamp) {
        //hasHatch = ultrasonicHatch.getRangeInches() < kHatchSensingRange;
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Hatch Angle", getAngle());
        SmartDashboard.putNumber("Hatch Position", hatchPivot.getSelectedSensorPosition(0));
        //System.out.println("Hatch Pivot Raw: " + hatchPivot.getSelectedSensorPosition(0));
        //System.out.println("Hatch Pivot Angle: " + getAngle());
        //SmartDashboard.putNumber("Hatch Target", hatchPivot.getClosedLoopError());
        //SmartDashboard.putNumber("Hatch Wanted Encoder", angleToSensorUnits(kPositionStarting));
        //SmartDashboard.putNumber("Hatch Sensing Distance", ultrasonicHatch.getRangeInches());
        //SmartDashboard.putBoolean("Hatch Time", hasHatch);
    }

    @Override
    public void zeroSensors() {
        hatchPivot.setSelectedSensorPosition((int) angleToSensorUnits(kHatchAngleOffset));
    }
    @Override
    public void init(double timestamp) {
        //hatchPivot.setSelectedSensorPosition((int) angleToSensorUnits(kPositionStarting),0,0);
    }

    @Override
    public void end(double timestamp) {

    }

    public double getEncoderValue() {
        return hatchPivot.getSelectedSensorPosition(0);
    }
}

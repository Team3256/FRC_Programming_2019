package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.robot.constants.HatchConstants;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.HatchConstants.*;

public class HatchPivot extends SubsystemBase {

    private static HatchPivot instance;
    private WPI_TalonSRX hatchPivot;
    private DoubleSolenoid deployHatch, ratchetPivot;

    private HatchPivot() {
        hatchPivot = TalonSRXUtil.generateGenericTalon(kHatchPivotPort);

        deployHatch = new DoubleSolenoid(15, kHatchForwardChannel, kHatchReverseChannel);
        ratchetPivot = new DoubleSolenoid(15, kRatchetForwardChannel, kRatchetReverseChannel);

        TalonSRXUtil.configMagEncoder(hatchPivot);

        TalonSRXUtil.setBrakeMode();

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

    public void unratchet() {
        ratchetPivot.set(DoubleSolenoid.Value.kReverse);
    }

    public void ratchet() {
        ratchetPivot.set(DoubleSolenoid.Value.kForward);
    }

    public void setHatchPivotPower(double speed) {
        hatchPivot.set(speed);
    }

    public void setPositionFoldIn() {
        hatchPivot.set(ControlMode.MotionMagic, angleToSensorUnits(kPositionFoldIn), DemandType.ArbitraryFeedForward, 0);
    }

    public void setPositionFloorIntake() {
        hatchPivot.set(ControlMode.MotionMagic, angleToSensorUnits(kPositionFloorIntakeHatch), DemandType.ArbitraryFeedForward, 0);
    }

    public void setPositionDeploy() {
        hatchPivot.set(ControlMode.MotionMagic, angleToSensorUnits(kPositionDeployHatch), DemandType.ArbitraryFeedForward, 0);
    }

    private double angleToSensorUnits(double degrees) { return (degrees) * DriveTrainConstants.kMagEncoderTicksTalon / HatchConstants.kHatchPivotGearRatio / (2 * Math.PI); }

    private double sensorUnitsToAngle(double ticks) { return Math.PI * 2 * ((ticks / DriveTrainConstants.kMagEncoderTicksTalon) * HatchConstants.kHatchPivotGearRatio); }

    public double getAngle() {
        return sensorUnitsToAngle(hatchPivot.getSelectedSensorPosition(0));
    }

    @Override
    public void update(double timestamp) {
        //System.out.println("Hatch Pivot Raw: " + hatchPivot.getSelectedSensorPosition(0));
        //System.out.println("Hatch Pivot Angle: " + getAngle());
        SmartDashboard.putNumber("Hatch Position", hatchPivot.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Hatch Target", hatchPivot.getClosedLoopError());
        SmartDashboard.putNumber("Hatch Angle", getAngle());
    }

    @Override
    public void outputToDashboard() {

    }

    @Override
    public void zeroSensors() {
        hatchPivot.setSelectedSensorPosition(0);
    }

    @Override
    public void init(double timestamp) {
        hatchPivot.setSelectedSensorPosition(0,0,0);
    }

    @Override
    public void end(double timestamp) {

    }
}

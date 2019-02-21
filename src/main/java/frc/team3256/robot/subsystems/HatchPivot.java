package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.constants.HatchConstants;
import frc.team3256.robot.operations.Constants;
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
        hatchPivot.selectProfileSlot(0, 0);
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

    public void closeHatch() {
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
        hatchPivot.set(ControlMode.Position, angleToSensorUnits(kPositionFoldIn), DemandType.Neutral, 0);
    }


    public void setPositionFloorIntake() {
        hatchPivot.set(ControlMode.Position, angleToSensorUnits(kPositionFloorIntakeHatch), DemandType.Neutral, 0);
    }

    public void setPositionDeploy() {
        hatchPivot.set(ControlMode.Position, angleToSensorUnits(kPositionDeployHatch), DemandType.Neutral, 0);
    }

    private double angleToSensorUnits(double degrees) { return (degrees / 360) * Constants.kMagEncoderTicksTalon * HatchConstants.kHatchPivotGearRatio; }

    private double sensorUnitsToAngle(double ticks) { return (ticks / Constants.kMagEncoderTicksTalon) / HatchConstants.kHatchPivotGearRatio * 360; }

    public double getAngle() {
        return sensorUnitsToAngle(hatchPivot.getSelectedSensorPosition(0));
    }

    @Override
    public void update(double timestamp) {
        //System.out.println("Hatch Pivot Raw: " + hatchPivot.getSelectedSensorPosition(0));
        //System.out.println("Hatch Pivot Angle: " + getAngle());
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

    }

    @Override
    public void end(double timestamp) {

    }
}

package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class HatchPivot extends SubsystemBase {

    private static HatchPivot instance;
    private WPI_TalonSRX hatchPivot;
    private DoubleSolenoid deployHatch, ratchetPivot;

    private HatchPivot() {
        hatchPivot = TalonSRXUtil.generateGenericTalon(Constants.khatchPivotPort);
        deployHatch = new DoubleSolenoid(Constants.kDeployHatchForward, Constants.kDeployHatchReverse);
        ratchetPivot = new DoubleSolenoid(Constants.kRatchetPivotForward, Constants.kRatchetPivotReverse);

        TalonSRXUtil.configMagEncoder(hatchPivot);

        TalonSRXUtil.setBrakeMode();

        TalonSRXUtil.setPIDGains(hatchPivot, Constants.kHatchPivotUpSlot, Constants.kHatchPivotUpP, Constants.kHatchPivotUpI, Constants.kHatchPivotUpD, Constants.kHatchPivotUpF);

        TalonSRXUtil.setPIDGains(hatchPivot, Constants.kHatchPivotDownSlot, Constants.kHatchPivotDownP, Constants.kHatchPivotDownI, Constants.kHatchPivotDownD, Constants.kHatchPivotDownF);
    }

    public static HatchPivot getInstance() {return instance == null ? instance = new HatchPivot(): instance;}

    public void deployHatch() { deployHatch.set(DoubleSolenoid.Value.kForward); }

    public void closeHatch() { deployHatch.close(); }

    public void startRatchet() {
        ratchetPivot.set(DoubleSolenoid.Value.kReverse);
    }

    public void ratchet() {
        ratchetPivot.set(DoubleSolenoid.Value.kForward);
    }

    public void setFloorIntakePosition() {
        hatchPivot.selectProfileSlot(Constants.kHatchPivotDownSlot, 0);
        hatchPivot.set(ControlMode.Position, angleToSensorUnits(Constants.kHatchPivotFloorIntakePreset), DemandType.Neutral, 0);
    }

    //Default
    public void setDeployPosition() {
        hatchPivot.selectProfileSlot(Constants.kHatchPivotDownSlot, 0);
        hatchPivot.set(ControlMode.Position, angleToSensorUnits(Constants.kHatchPivotDeployPreset), DemandType.Neutral, 0);
    }

    private double angleToSensorUnits(double degrees) { return (degrees / 360) * Constants.kMagEncoderTicksTalon * Constants.kHatchPivotGearRatio; }

    private double sensorUnitsToAngle(double ticks) { return (ticks / Constants.kMagEncoderTicksTalon) / Constants.kHatchPivotGearRatio * 360; }

    public double getAngle() {
        return sensorUnitsToAngle(hatchPivot.getSelectedSensorPosition(0));
    }


    @Override
    public void update(double timestamp) {
        System.out.println("Hatch Pivot Angle: " + getAngle());
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
    public void end(double timestamp) {

    }
}

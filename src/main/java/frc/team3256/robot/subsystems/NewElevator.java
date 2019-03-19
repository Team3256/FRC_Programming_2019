package frc.team3256.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.warriorlib.hardware.SparkMAXUtil;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

import static frc.team3256.robot.constants.ElevatorConstants.*;

public class NewElevator extends SubsystemBase {

    private CANSparkMax mMaster, mSlave;
    private DigitalInput mHallEffect;
    private ElevatorControlState mElevatorControlState;

    private ElevatorValues mElevatorValues;

    private enum ElevatorControlState {
        OPEN_LOOP,
        VELOCITY_PID,
        POSITION_PID
    }

    private boolean mWasHomed;

    public NewElevator() {
        mMaster = new CANSparkMax(kSparkMaxMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        mSlave = new CANSparkMax(kSparkMaxSlave, CANSparkMaxLowLevel.MotorType.kBrushless);

        mMaster.setInverted(true);
        mSlave.follow(mMaster, true);

        SparkMAXUtil.setBrakeMode(mMaster, mSlave);

        mWasHomed = false;
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Elevator Temp Master", mMaster.getMotorTemperature());
        SmartDashboard.putNumber("Elevator Temp Slave", mSlave.getMotorTemperature());

        SmartDashboard.putNumber("Elevator Current", mMaster.getOutputCurrent());

        SmartDashboard.putNumber("Elevator Height", getCurrentPositionInches());
        SmartDashboard.putNumber("Elevator Ticks", mElevatorValues.positionTicks);

        SmartDashboard.putBoolean("Elevator Hall Effect", mElevatorValues.hallEffect);

        SmartDashboard.putNumber("Elevator RPM", mMaster.getEncoder().getVelocity() * kElevatorGearRatio);
    }

    @Override
    public void zeroSensors() {
        mMaster.setEncPosition(0);
    }

    @Override
    public void init(double timestamp) {
    }

    @Override
    public void update(double timestamp) {
        mElevatorValues.hallEffect = getHallEffect();
        mElevatorValues.positionTicks = mMaster.getEncoder().getPosition();
        handleOutputs();
    }

    @Override
    public void end(double timestamp) {
        this.setOpenLoop(0.0);
    }

    public void setBrake() {
        mMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        mSlave.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setCoast() {
        mMaster.setIdleMode(CANSparkMax.IdleMode.kCoast);
        mSlave.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setOpenLoop(double power) {
        mElevatorControlState = ElevatorControlState.OPEN_LOOP;
        mElevatorValues.power = power;
    }

    public void setPositionInches() {

    }

    public void setPositionTicks() {

    }

    public double getCurrentPositionInches() {
        return rotationToInches(mElevatorValues.positionTicks);
    }

    // Helper Functions
    private double rotationToInches(double rotations) {
        return (rotations * kElevatorGearRatio * kElevatorSpoolSize * Math.PI) + kElevatorOffset;
    }

    private double inchesToRotations(double inches) {
        return (inches - kElevatorOffset) / Math.PI / kElevatorGearRatio / kElevatorSpoolSize;
    }

    public boolean getHallEffect() {
        return !mHallEffect.get();
    }

    public void handleOutputs() {
        switch (mElevatorControlState) {
            case OPEN_LOOP:

                break;
            case POSITION_PID:

                break;
            case VELOCITY_PID:
                mMaster.getEncoder().setPosition()
                break;
        }
    }

    public static class ElevatorValues {
        public double positionTicks;
        public int setPoint;
        public boolean hallEffect;
        public boolean atSetPoint;

        public double power;
    }
}

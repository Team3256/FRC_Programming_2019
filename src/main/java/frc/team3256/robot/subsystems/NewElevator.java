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
    private ElevatorControlState mElevatorControlState = ElevatorControlState.HOLD;

    public void setWantedPosition(double desiredHeight) {
    }

    private enum ElevatorControlState {
        MANUAL_UP,
        MANUAL_DOWN,
        HOLD,
        HOMING,
        MOVING_TO_POSITION
    }

    public enum WantedState {
        WANTS_TO_MANUAL_UP,
        WANTS_TO_MANUAL_DOWN,
        WANTS_TO_HOME,
        WANTS_TO_HOLD,
        WANTS_TO_MOVE_TO_POSITION
    }

    private ElevatorControlState currentState = ElevatorControlState.HOLD;
    private WantedState wantedState = WantedState.WANTS_TO_HOLD;
    private WantedState prevWantedState = WantedState.WANTS_TO_HOLD;

    private boolean mWasHomed;

    private static NewElevator instance;
    public static NewElevator getInstance() {
        return instance == null ? instance = new NewElevator() : instance;
    }

    private NewElevator() {
        //mMaster = new CANSparkMax(kSparkMaxMaster, CANSparkMaxLowLevel.MotorType.kBrushless);
        //mSlave = new CANSparkMax(kSparkMaxSlave, CANSparkMaxLowLevel.MotorType.kBrushless);

        //mMaster.setInverted(true);
        //mSlave.follow(mMaster, true);

        this.setBrake();

        mWasHomed = false;
    }

    public void setWantedState(WantedState wantedState){
        this.wantedState = wantedState;
    }

    @Override
    public void outputToDashboard() {
        SmartDashboard.putNumber("Elevator Temp Master", mMaster.getMotorTemperature());
        SmartDashboard.putNumber("Elevator Temp Slave", mSlave.getMotorTemperature());

        SmartDashboard.putNumber("Elevator Current", mMaster.getOutputCurrent());

        SmartDashboard.putNumber("Elevator Height", getCurrentPositionInches());
        SmartDashboard.putNumber("Elevator Ticks", mMaster.getEncoder().getPosition());

        SmartDashboard.putBoolean("Elevator Hall Effect", mHallEffect.get());

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
        //mElevatorValues.hallEffect = getHallEffect();
        //mElevatorValues.positionTicks = mMaster.getEncoder().getPosition();
        System.out.println(currentState.name());
    }

    @Override
    public void end(double timestamp) {
        this.setOpenLoop(0.0);
    }

    public void setBrake() {
        //mMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        //mSlave.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setCoast() {
        System.out.println("Set Coast");
        //mMaster.setIdleMode(CANSparkMax.IdleMode.kCoast);
        //mSlave.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setOpenLoop(double power) {
        System.out.println("setOpenLoop: " + power);
    }

    public void setPositionInches(double inches) {
    }

    public void setPositionTicks() {

    }

    public double getCurrentPositionInches() {
        return rotationToInches(mMaster.getEncoder().getPosition());
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
}

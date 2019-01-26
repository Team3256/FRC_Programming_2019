package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.hardware.TalonSRXUtil;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class HatchPivot extends SubsystemBase {

    private WPI_TalonSRX hatchPivot;
    private DoubleSolenoid deployLeft, deployRight, deployTop, ratchetPivot;

    private boolean stateChanged = false;

    private double m_closedLoopTarget;
    private boolean m_usingClosedLoop;

    private static HatchPivot instance;
    public static HatchPivot getInstance() {return instance == null ? instance = new HatchPivot(): instance;}

    private HatchPivot() {
        hatchPivot = TalonSRXUtil.generateGenericTalon(Constants.khatchPivotPort);
        deployLeft = new DoubleSolenoid(Constants.kDeployLeftForward, Constants.kDeployLeftReverse);
        deployRight = new DoubleSolenoid(Constants.kDeployRightForward, Constants.kDeployRightReverse);
        deployTop = new DoubleSolenoid(Constants.kDeployTopForward, Constants.kDeployTopReverse);
        ratchetPivot = new DoubleSolenoid(Constants.kRatchetPivotForward, Constants.kRatchetPivotReverse);

        TalonSRXUtil.configMagEncoder(hatchPivot);

        TalonSRXUtil.setBrakeMode();

        TalonSRXUtil.setPIDGains(hatchPivot, Constants.kHatchPivotUpSlot, Constants.kHatchPivotUpP,
                Constants.kHatchPivotUpI, Constants.kHatchPivotUpD, Constants.kHatchPivotUpF);

        TalonSRXUtil.setPIDGains(hatchPivot, Constants.kHatchPivotDownSlot, Constants.kHatchPivotDownP,
                Constants.kHatchPivotDownI, Constants.kHatchPivotDownD, Constants.kHatchPivotDownF);
    }

    public void deployHatch(){
        deployLeft.set(DoubleSolenoid.Value.kForward);
        deployRight.set(DoubleSolenoid.Value.kForward);
        deployTop.set(DoubleSolenoid.Value.kForward);
    }

    private void closeHatch(){
        deployLeft.close();
        deployRight.close();
        deployTop.close();
    }

    public void ratchet() {
        ratchetPivot.set(DoubleSolenoid.Value.kForward);
    }

    public void startRatchet() {
        ratchetPivot.set(DoubleSolenoid.Value.kReverse);
    }

    public static class DeployingState extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().deployHatch();
            return new IdleState();
        }
    }

    public static class ManualPivotUpState extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().hatchPivot.set(Constants.kHatchPivotUpPower);
            return new IdleState();
        }
    }

    public static class ManualPivotDownState extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().hatchPivot.set(Constants.kHatchPivotDownPower);
            return new IdleState();
        }
    }

    public static class PivotFloorIntakePreset extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().m_closedLoopTarget = Constants.kCargoPivotFloorPreset;
            HatchPivot.getInstance().m_usingClosedLoop = true;
            if(HatchPivot.getInstance().atClosedLoopTarget()) {
                return new IdleState();
            }
            else if (HatchPivot.getInstance().getAngle() > HatchPivot.getInstance().m_closedLoopTarget) {
                HatchPivot.getInstance().setTargetPosition(ControlMode.Position,
                        HatchPivot.getInstance().m_closedLoopTarget, Constants.kHatchPivotDownSlot);
                return new IdleState();
            }
            else return new IdleState();
        }
    }

    public static class PivotDeployPreset extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().m_closedLoopTarget = Constants.kHatchPivotDeployPreset;
            HatchPivot.getInstance().m_usingClosedLoop = true;
            if(HatchPivot.getInstance().atClosedLoopTarget()) {
                return new IdleState();
            }
            else if (HatchPivot.getInstance().getAngle() < HatchPivot.getInstance().m_closedLoopTarget) {
                HatchPivot.getInstance().setTargetPosition(ControlMode.Position,
                        HatchPivot.getInstance().m_closedLoopTarget, Constants.kHatchPivotUpSlot);
                return new IdleState();
            }
            else return new IdleState();
        }
    }

    public static class RatchetState extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().ratchet();
            return new IdleState();
        }
    }

    public static class IdleState extends RobotState {

        @Override
        public RobotState update() {
            HatchPivot.getInstance().hatchPivot.set(0);
            HatchPivot.getInstance().closeHatch();
            return new IdleState();
        }
    }

    RobotState robotState = new IdleState();

    @Override
    public void update(double timestamp) {
        RobotState newState = robotState.update();
        if (!robotState.equals(newState)) {
            System.out.println(String.format(
                    "(%s) STATE CHANGE: %s -> %s",
                    getClass().getSimpleName(),
                    robotState.getClass().getSimpleName(),
                    newState.getClass().getSimpleName()
            ));
        }
        robotState = newState;
    }

    public void setTargetPosition(ControlMode mode, double targetAngle, int slotID) {
        if(stateChanged) {
            hatchPivot.selectProfileSlot(slotID, 0);
        }
        hatchPivot.set(mode, angleToSensorUnits(targetAngle), DemandType.Neutral, 0);
    }

    public void setRobotState(RobotState state){
        this.robotState = state;
    }

    private double angleToSensorUnits (double degrees) {
        return(degrees/360)*Constants.kMagEncoderTicksTalon*Constants.kHatchPivotGearRatio;
    }

    private double sensorUnitsToAngle (double ticks) {
        return (ticks/Constants.kMagEncoderTicksTalon)/Constants.kHatchPivotGearRatio*360;
    }

    private double getAngle() {
        return sensorUnitsToAngle(hatchPivot.getSelectedSensorPosition(0));
    }

    private boolean atClosedLoopTarget() {
        if(!m_usingClosedLoop || stateChanged)return false;
        return(Math.abs(getAngle() - m_closedLoopTarget) < Constants.kHatchPivotTolerance);
    }

    @Override
    public void outputToDashboard() {

    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void init(double timestamp) {
        startRatchet();
    }

    @Override
    public void end(double timestamp) {

    }
}
package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class HatchIntake extends SubsystemBase {

    private double kHatchIntakePower = Constants.kHatchIntakePower;
    private double kPivotUpPower = Constants.kPivotUpPower;
    private double kPivotDownPower = Constants.kPivotDownPower;

    private VictorSP hatchIntake, hatchPivot;
    private DoubleSolenoid deployLeft, deployRight, deployTop;

    private HatchIntake() {
        hatchIntake = new VictorSP(Constants.kHatchIntakePort);
        hatchPivot = new VictorSP(Constants.kHatchPivotPort);
        deployLeft = new DoubleSolenoid(Constants.kDeployLeftForward, Constants.kDeployLeftReverse);
        deployRight = new DoubleSolenoid(Constants.kDeployRightForward, Constants.kDeployRightReverse);
        deployTop = new DoubleSolenoid(Constants.kDeployTopForward, Constants.kDeployTopReverse);
    }

    private void deployHatch(){
        deployLeft.set(DoubleSolenoid.Value.kForward);
        deployRight.set(DoubleSolenoid.Value.kForward);
        deployTop.set(DoubleSolenoid.Value.kForward);
    }

    private void closeHatch(){
        deployLeft.close();
        deployRight.close();
        deployTop.close();
    }

    class IntakingState extends RobotState {
        @Override
        public RobotState update() {
            hatchIntake.set(kHatchIntakePower);
            return new IdleState();
        }
    }

    class DeployingState extends RobotState {
        @Override
        public RobotState update() {
            deployHatch();
            return new IdleState();
        }
    }

    class PivotingUp extends RobotState {
        @Override
        public RobotState update() {
            hatchPivot.set(kPivotUpPower);
            return new IdleState();
        }
    }

    class PivotingDown extends RobotState {
        @Override
        public RobotState update() {
            hatchPivot.set(kPivotDownPower);
            return new IdleState();
        }
    }

    class IdleState extends RobotState {
        @Override
        public RobotState update() {
            hatchIntake.set(0);
            hatchPivot.set(0);
            closeHatch();
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

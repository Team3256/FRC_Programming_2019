package frc.team3256.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3256.robot.operation.DriveConfigImplementation;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.state.RobotState;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class HatchPivot extends SubsystemBase {

    private WPI_TalonSRX hatchPivot;
    private DoubleSolenoid deployLeft, deployRight, deployTop;

    private static HatchPivot instance;
    public static HatchPivot getInstance() {return instance == null ? instance = new HatchPivot(): instance;}

    DriveConfigImplementation driveConfigImplementation = new DriveConfigImplementation();


    private HatchPivot() {
        hatchPivot = new WPI_TalonSRX(Constants.khatchPivot);
        deployLeft = new DoubleSolenoid(Constants.kDeployLeftForward, Constants.kDeployLeftReverse);
        deployRight = new DoubleSolenoid(Constants.kDeployRightForward, Constants.kDeployRightReverse);
        deployTop = new DoubleSolenoid(Constants.kDeployTopForward, Constants.kDeployTopReverse);
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

    public static class DeployingState extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().deployHatch();
            return new IdleState();
        }
    }

    public static class PivotingUp extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().hatchPivot.set(Constants.kHatchPivotUpPower);
            return new IdleState();
        }
    }

    public static class PivotingDown extends RobotState {
        @Override
        public RobotState update() {
            HatchPivot.getInstance().hatchPivot.set(Constants.kHatchPivotDownPower);
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

    public void setRobotState(RobotState state){
        this.robotState = state;
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
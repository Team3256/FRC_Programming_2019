package frc.team3256.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team3256.robot.Constants;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class HatchIntake extends SubsystemBase {
    private VictorSP hatchIntake, hatchPivot;
    private DoubleSolenoid deployLeft, deployRight, deployTop;

    private SystemState currentState;
    private SystemState previousState;
    private WantedState wantedState;
    private WantedState prevWantedState;
    private boolean stateChanged;
    private boolean wantedStateChanged;

    private boolean firstRun;

    private double kHatchIntakePower = Constants.kHatchIntakePower;
    private double kPivotUpPower = Constants.kPivotUpPower;
    private double kPivotDownPower = Constants.kPivotDownPower;

    private static HatchIntake instance;

    public enum SystemState {
        INTAKING,
        DEPLOYING,
        PIVOTING_UP,
        PIVOTING_DOWN,
        IDLE
    }

    public enum WantedState {
        WANTS_TO_INTAKE,
        WANTS_TO_DEPLOY,
        WANTS_TO_PIVOT_UP,
        WANTS_TO_PIVOT_DOWN,
        IDLE
    }

    private HatchIntake(){
        hatchIntake = new VictorSP(Constants.kHatchIntakePort);
        hatchPivot = new VictorSP(Constants.kHatchPivotPort);
        deployLeft = new DoubleSolenoid(Constants.kDeployLeftForward, Constants.kDeployLeftReverse);
        deployRight = new DoubleSolenoid(Constants.kDeployRightForward, Constants.kDeployRightReverse);
        deployTop = new DoubleSolenoid(Constants.kDeployTopForward, Constants.kDeployTopReverse);
    }

    public static HatchIntake getInstance(){
        return instance == null ? instance = new HatchIntake(): instance;
    }

    @Override
    public void outputToDashboard() {

    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void init(double timestamp) {
        prevWantedState = WantedState.IDLE;
        wantedState = WantedState.IDLE;
        stateChanged = true;
    }

    @Override
    public void update(double timestamp) {
        if (firstRun) {
            wantedStateChanged = true;
            firstRun = false;
        }
        else if (prevWantedState != wantedState){
            wantedStateChanged = true;
            prevWantedState = wantedState;
        }
        else wantedStateChanged = false;

        if(!wantedStateChanged) return;

        SystemState newState;
        switch(currentState) {
            case INTAKING:
                newState = handleIntaking();
                break;
            case DEPLOYING:
                newState = handleDeploying();
                break;
            case PIVOTING_UP:
                newState = handlePivotUp();
                break;
            case PIVOTING_DOWN:
                newState = handlePivotDown();
                break;
            case IDLE: default:
                newState = handleIdle();
                break;
        }
        //State Transfer
        if (newState != currentState){
            System.out.println("\tPREV_STATE:" + previousState + "\tCURR_STATE:" + currentState +
                    "\tNEW_STATE:" + newState);
            previousState = currentState;
            currentState = newState;
            stateChanged = true;
        }
        else stateChanged = false;

    }

    private SystemState handleIntaking(){
        hatchIntake.set(kHatchIntakePower);
        return defaultStateTransfer();
    }

    private SystemState handleDeploying(){
        deployHatch();
        return defaultStateTransfer();
    }

    private SystemState handleIdle(){
        hatchIntake.set(0);
        hatchPivot.set(0);
        closeHatch();
        return defaultStateTransfer();
    }

    private SystemState handlePivotUp(){
        hatchPivot.set(kPivotUpPower);
        return defaultStateTransfer();
    }

    private SystemState handlePivotDown(){
        hatchPivot.set(kPivotDownPower);
        return defaultStateTransfer();
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

    private SystemState defaultStateTransfer(){
        switch(wantedState) {
            case WANTS_TO_INTAKE:
                return SystemState.PIVOTING_UP;
            case WANTS_TO_DEPLOY:
                return SystemState.DEPLOYING;
            case WANTS_TO_PIVOT_UP:
                return SystemState.PIVOTING_UP;
            case WANTS_TO_PIVOT_DOWN:
                return SystemState.PIVOTING_DOWN;
            case IDLE:
                return SystemState.IDLE;
            default:
                return SystemState.IDLE;
        }
    }

    public void setWantedState(WantedState wantedState){
        this.wantedState = wantedState;
    }

    public SystemState getCurrentState(){
        return currentState;
    }

    public WantedState getWantedState(){
        return wantedState;
    }

    @Override
    public void end(double timestamp) {

    }
}

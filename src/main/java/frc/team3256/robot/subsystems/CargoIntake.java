package frc.team3256.robot.subsystems;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.subsystem.SubsystemBase;

public class CargoIntake extends SubsystemBase {

    private VictorSP cargoIntake;

    private SystemState currentState;
    private SystemState previousState;
    private WantedState wantedState;
    private WantedState prevWantedState;
    private boolean stateChanged;
    private boolean wantedStateChanged;

    private boolean firstRun;

    private double kIntakePower = Constants.kCargoIntakePower;
    private double kExhaustPower = Constants.kCargoExhaustPower;

    private static CargoIntake instance;

    public enum SystemState {
        INTAKING,
        EXHAUSTING,
        IDLE
    }

    public enum WantedState {
        WANTS_TO_INTAKE,
        WANTS_TO_EXHAUST,
        IDLE
    }


    private CargoIntake(){
        cargoIntake = new VictorSP(Constants.kCargoIntakePort);
        cargoIntake.setInverted(false); //TBD
    }

    public static CargoIntake getInstance(){
        return instance == null ? instance = new CargoIntake(): instance;
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
                case EXHAUSTING:
                    newState = handleExhausting();
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
        cargoIntake.set(kIntakePower);
        return defaultStateTransfer();
    }

    private SystemState handleExhausting(){
        cargoIntake.set(kExhaustPower);
        return defaultStateTransfer();
    }

    private SystemState handleIdle(){
        cargoIntake.set(0);
        return defaultStateTransfer();
    }

    private SystemState defaultStateTransfer() {
        switch (wantedState) {
            case WANTS_TO_INTAKE:
                return SystemState.INTAKING;
            case WANTS_TO_EXHAUST:
                return SystemState.EXHAUSTING;
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
    public void outputToDashboard() {

    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void end(double timestamp) {

    }
}

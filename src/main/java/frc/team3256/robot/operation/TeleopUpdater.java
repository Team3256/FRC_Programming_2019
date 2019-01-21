package frc.team3256.robot.operation;

import frc.team3256.robot.operations.DrivePower;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.subsystems.HatchPivot;

public class TeleopUpdater {

    private DriveConfigImplementation controls = new DriveConfigImplementation();

    private DriveTrain m_drive = DriveTrain.getInstance();
    private HatchPivot m_hatch = HatchPivot.getInstance();
    private CargoIntake m_cargo = CargoIntake.getInstance();
    private Elevator m_elevator = Elevator.getInstance();
    boolean highGear = true;

    public void update(){

        //Drive
        double throttle = controls.getThrottle();
        double turn = controls.getTurn();
        boolean quickTurn = controls.getQuickTurn();
        boolean shiftDown = controls.getLowGear();

        //Hatch
        boolean scoreHatch = controls.scoreHatch();
        boolean pivotHatchUp = controls.pivotHatchUp();
        boolean pivotHatchDown = controls.pivotHatchDown();

        //Cargo
        boolean intakeCargo = controls.getCargoIntake();
        boolean exhaustCargo = controls.getCargoExhaust();
        boolean scoreCargo = controls.scoreCargo();
        boolean pivotCargoUp = controls.pivotCargoUp();
        boolean pivotCargoDown = controls.pivotCargoDown();
        boolean clearanceCargoUp = controls.clearanceCargoUp();
        boolean clearanceCargoDown = controls.clearanceCargoDown();

        //Elevator
        boolean manualElevatorUp = controls.manualElevatorUp();
        boolean manualElevatorDown = controls.manualElevatorDown();
        boolean cargoPresetLow = controls.cargoPresetLow();
        boolean cargoPresetMid = controls.cargoPresetMid();
        boolean cargoPresetHigh = controls.cargoPresetHigh();
        boolean hatchPresetLow = controls.hatchPresetLow();
        boolean hatchPresetMid = controls.hatchPresetMid();
        boolean hatchPresetHigh = controls.hatchPresetHigh();

        //Hanger
        boolean hang = controls.hang();

        //DriveTrain Subsystem
        DrivePower drivePower = DriveTrain.curvatureDrive(throttle, turn, quickTurn, highGear);
        m_drive.setHighGear(drivePower.highGear());
        m_drive.setOpenLoop(drivePower.getLeft(), drivePower.getRight());

        if (m_drive.leftSlave.getOutputCurrent() > 10 || m_drive.leftSlave.getOutputCurrent() > 10)
            System.out.println("LEFT " + m_drive.leftSlave.getOutputCurrent() + " RIGHT " + m_drive.rightSlave.getOutputCurrent());


        //CargoIntake Subsystem
        if(intakeCargo){
            m_cargo.setRobotState(new CargoIntake.IntakingState());
        }
        else if (exhaustCargo) {
            m_cargo.setRobotState(new CargoIntake.ExhaustingState());
        }
        else if (scoreCargo) {
            m_cargo.setRobotState(new CargoIntake.ScoringState());
        }
        else if (pivotCargoUp) {
            m_cargo.setRobotState(new CargoIntake.PivotingUpState());
        }
        else if (pivotCargoDown) {
            m_cargo.setRobotState(new CargoIntake.PivotingDownState());
        }
        else if (clearanceCargoUp) {
            m_cargo.setRobotState(new CargoIntake.ClearanceUpState());
        }
        else if (clearanceCargoDown) {
            m_cargo.setRobotState(new CargoIntake.ClearanceDownState());
        }
        else m_cargo.setRobotState(new CargoIntake.IdleState());


        //HatchPivot Subsystem
        if (scoreHatch){
            m_hatch.setRobotState(new HatchPivot.DeployingState());
        }
        else if (pivotHatchUp){
            m_hatch.setRobotState(new HatchPivot.PivotingUp());
        }
        else if (pivotHatchDown){
            m_hatch.setRobotState(new HatchPivot.PivotingDown());
        }
        else m_hatch.setRobotState(new HatchPivot.IdleState());


        //Elevator Subsystem
        if (manualElevatorUp) {
            m_elevator.setRobotState(new Elevator.ManualUpState());
        }
        else if (manualElevatorDown) {
            m_elevator.setRobotState(new Elevator.ManualDownState());
        }
        else if (cargoPresetHigh) {
            m_elevator.setRobotState(new Elevator.HighCargoPresetState());
        }
        else if (cargoPresetMid) {
            m_elevator.setRobotState(new Elevator.MidCargoPresetState());
        }
        else if (cargoPresetLow) {
            m_elevator.setRobotState(new Elevator.LowCargoPresetState());
        }
        else if (hatchPresetHigh) {
            m_elevator.setRobotState(new Elevator.HighHatchPresetState());
        }
        else if (hatchPresetMid) {
            m_elevator.setRobotState(new Elevator.MidHatchPresetState());
        }
        else if (hatchPresetLow) {
            m_elevator.setRobotState(new Elevator.LowHatchPresetState());
        }
    }
}

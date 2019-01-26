package frc.team3256.robot.operation;

import frc.team3256.robot.operations.DrivePower;
import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.subsystems.HatchPivot;

public class TeleopUpdater {

    private DriveConfigImplementation controls = new DriveConfigImplementation();

    private DriveTrain m_drive = DriveTrain.getInstance();

    //private HatchPivot m_hatch = HatchPivot.getInstance();
    //private CargoIntake m_cargo = CargoIntake.getInstance();
    //private Elevator m_elevator = Elevator.getInstance();

    boolean highGear = true;

    private boolean isManipulatorHatchMode = true;
    private boolean isDriverHatchMode = true;

    public void update(){
        System.out.println(isManipulatorHatchMode ? "Manipulator: Hatch Mode" : "Manipulator: Cargo Mode");
        System.out.println(isDriverHatchMode ? "Driver: Hatch Mode" : "Driver: Cargo Mode");

        boolean switchManipulatorControlMode = controls.switchManipulatorControlMode();
        boolean switchDriverControlMode = controls.switchDriverControlMode();

        //Drive
        double throttle = controls.getThrottle();
        double turn = controls.getTurn();
        boolean quickTurn = controls.getQuickTurn();
        boolean shiftDown = controls.getLowGear();

        //Hatch
        boolean scoreHatch = controls.scoreHatch();
        boolean manualHatchUp = controls.manualHatchUp();
        boolean manualHatchDown = controls.manualHatchDown();
        boolean hatchFloorIntakePreset = controls.hatchPivotFloorIntakePreset();
        boolean hatchDeployPreset = controls.hatchPivotDeployPreset();

        //Cargo
        boolean intakeCargo = controls.getCargoIntake();
        boolean exhaustCargo = controls.getCargoExhaust();
        boolean scoreCargo = controls.scoreCargo();
        boolean pivotCargoUp = controls.pivotCargoUp();
        boolean pivotCargoDown = controls.pivotCargoDown();

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
        m_drive.setBrakeMode();
        DrivePower drivePower = DriveTrain.curvatureDrive(throttle, turn, quickTurn, highGear/*!shiftDown*/);
        m_drive.setHighGear(drivePower.highGear());
        m_drive.setOpenLoop(drivePower.getLeft(), drivePower.getRight());

        if (m_drive.leftSlave.getOutputCurrent() > 10 || m_drive.leftSlave.getOutputCurrent() > 10)
            System.out.println("LEFT " + m_drive.leftSlave.getOutputCurrent() + " RIGHT " + m_drive.rightSlave.getOutputCurrent());

        //Switch between Hatch and Cargo Control Modes
        if (switchManipulatorControlMode){
            isManipulatorHatchMode = !isManipulatorHatchMode;
        }

        if (switchDriverControlMode){
            isDriverHatchMode = !isDriverHatchMode;
        }
        /*
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
            m_cargo.setRobotState(new CargoIntake.ManualPivotUpState());
        }
        else if (pivotCargoDown) {
            m_cargo.setRobotState(new CargoIntake.ManualPivotDownState());
        }
        else m_cargo.setRobotState(new CargoIntake.IdleState());


        //HatchPivot Subsystem
        if (scoreHatch){
            m_hatch.setRobotState(new HatchPivot.DeployingState());
        }
        else if (manualHatchUp){
            m_hatch.setRobotState(new HatchPivot.ManualPivotUpState());
        }
        else if (manualHatchDown){
            m_hatch.setRobotState(new HatchPivot.ManualPivotDownState());
        }
        else if (hatchFloorIntakePreset){
            m_hatch.setRobotState(new HatchPivot.PivotFloorIntakePreset());
        }
        else if (hatchDeployPreset){
            m_hatch.setRobotState(new HatchPivot.PivotDeployPreset());
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
        */
    }
}

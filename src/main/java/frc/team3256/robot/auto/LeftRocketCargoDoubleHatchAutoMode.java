package frc.team3256.robot.auto;

import frc.team3256.robot.auto.action.TurnInPlaceAction;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.robot.subsystems.Elevator;
import frc.team3256.robot.subsystems.Pivot;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.WaitAction;
import frc.team3256.warriorlib.auto.purepursuit.PoseEstimator;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitAction;
import frc.team3256.warriorlib.auto.purepursuit.PurePursuitTracker;
import frc.team3256.warriorlib.auto.purepursuit.ResetPursuitAction;

import static frc.team3256.robot.auto.Paths.getCenterLeftDoubleBackAuto;

public class LeftRocketCargoDoubleHatchAutoMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {

        PurePursuitTracker purePursuitTracker = PurePursuitTracker.getInstance();
        purePursuitTracker.setRobotTrack(DriveTrainConstants.robotTrack);
        purePursuitTracker.setPaths(getCenterLeftDoubleBackAuto(), DriveTrainConstants.lookaheadDistance);

        Pivot.getInstance().setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_POS);
        runAction(new WaitAction(1.0));
        runAction(new ResetPursuitAction());
        DriveTrain.getInstance().setHighGear(true);
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_LOW_HATCH);
        runAction(new PurePursuitAction(0));
        runAction(new WaitAction(0.75));
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_START_OUTTAKE_HATCH);
        Pivot.getInstance().setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_HATCH);
        runAction(new WaitAction(0.5));
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_FINISH_OUTTAKE_HATCH);
        Pivot.getInstance().setWantedState(Pivot.WantedState.WANTS_TO_ELEVATOR_WAIT);
        runAction(new WaitAction(0.75));
        runAction(new PurePursuitAction(1));
        runAction(new WaitAction(1.5));
        runAction(new TurnInPlaceAction(90));
        DriveTrain.getInstance().resetGyro();
        DriveTrain.getInstance().resetEncoders();
        PoseEstimator.getInstance().reset();
        runAction(new WaitAction(1.0));
        System.out.println(PoseEstimator.getInstance().getPose());
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_START_INTAKE_HATCH);
        Pivot.getInstance().setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_HATCH);
        runAction(new PurePursuitAction(2));
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_FINISH_INTAKE_HATCH);
        Pivot.getInstance().setWantedState(Pivot.WantedState.WANTS_TO_ELEVATOR_WAIT);
        DriveTrain.getInstance().resetGyro();
        DriveTrain.getInstance().resetEncoders();
        PoseEstimator.getInstance().reset();
        runAction(new PurePursuitAction(3));
        runAction(new WaitAction(0.5));
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_LOW_HATCH);
        runAction(new TurnInPlaceAction(-151.25));
        runAction(new PurePursuitAction(4));
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_START_OUTTAKE_HATCH);
        Pivot.getInstance().setWantedState(Pivot.WantedState.WANTS_TO_DEPLOY_HATCH);
        runAction(new WaitAction(0.5));
        Elevator.getInstance().setWantedState(Elevator.WantedState.WANTS_TO_FINISH_OUTTAKE_HATCH);
        Pivot.getInstance().setWantedState(Pivot.WantedState.WANTS_TO_ELEVATOR_WAIT);

		//Elevator.getInstance().setPositionIntakeHatch();
		//HatchPivot.getInstance().deployHatch();


    }
}

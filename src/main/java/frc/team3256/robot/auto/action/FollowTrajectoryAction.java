package frc.team3256.robot.auto.action;

import frc.team3256.robot.subsystems.DriveTrain;

public class FollowTrajectoryAction implements Action {

    private DriveTrain drive = DriveTrain.getInstance();
    private double startVel, endVel, distance, initialAngle;

    public FollowTrajectoryAction(double startVel, double endVel, double distance, double initialAngle) {
        this.startVel = startVel;
        this.endVel = endVel;
        this.distance = distance;
        this.initialAngle = initialAngle;
    }

    @Override
    public boolean isFinished() {
        return drive.isDriveStraightFinished();
    }

    @Override
    public void update() {
    }

    @Override
    public void done() {
        drive.setBrake();
        //drive.setOpenLoop(0,0);
    }

    @Override
    public void start() {
        drive.setHighGear(true);
        drive.resetDriveStraightController();
        drive.resetEncoders();
        drive.setBrake();
        drive.configureDriveStraight(startVel, endVel, distance, initialAngle); //add + drive.getAverageDistance() later..for now it's relative
    }
}

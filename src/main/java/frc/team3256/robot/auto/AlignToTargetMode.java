package frc.team3256.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3256.robot.auto.action.AlignToTargetAction;
import frc.team3256.robot.auto.action.BangBaselineAction;
import frc.team3256.robot.auto.action.FinishAlignAction;
import frc.team3256.robot.auto.action.TurnInPlaceAction;
import frc.team3256.robot.constants.DriveTrainConstants;
import frc.team3256.robot.subsystems.DriveTrain;
import frc.team3256.warriorlib.auto.AutoModeBase;
import frc.team3256.warriorlib.auto.AutoModeEndedException;
import frc.team3256.warriorlib.auto.action.WaitAction;
import frc.team3256.warriorlib.auto.purepursuit.*;
import frc.team3256.warriorlib.math.Vector;

import java.util.Collections;

import static frc.team3256.robot.constants.DriveTrainConstants.*;

public class AlignToTargetMode extends AutoModeBase {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private PoseEstimator poseEstimator = PoseEstimator.getInstance();
    private PurePursuitTracker purePursuitTracker = PurePursuitTracker.getInstance();

    @Override
    protected void routine() throws AutoModeEndedException {
        //driveTrain.resetGyro();
        //driveTrain.setGyroOffset(-SmartDashboard.getNumber("visionAngle0", 0));
        driveTrain.resetEncoders();
        driveTrain.setGyroOffset(180.0);
        poseEstimator.resetPosition();
        poseEstimator.offsetPoseAngle(180.0);

        DriveTrain.getInstance().setHighGear(true);

        //double xTarget = SmartDashboard.getNumber("horizontalDisp0", 0);
        double robotCenterToCamera = 14.0 - 5.5;
        double cameraDistance = SmartDashboard.getNumber("visionDistance0", 0);
        double cameraAngleFromTarget = SmartDashboard.getNumber("visionAngle0",0) * Math.PI/180.0;
        double m = Math.cos(cameraAngleFromTarget) * cameraDistance;
        double n = Math.sin(cameraAngleFromTarget) * cameraDistance;
        double angleFromTarget = Math.atan(n / (m + robotCenterToCamera));
        double distance = n / Math.sin(angleFromTarget);
        angleFromTarget = angleFromTarget * 180.0 / Math.PI;

        double absPosition = 90-driveTrain.getAngle();


        double angleDelta = (absPosition - angleFromTarget) * Math.PI / 180.0;
        double x = Math.cos(angleDelta) * distance;
        double y = Math.sin(angleDelta) * distance;
        SmartDashboard.putNumber("Target Y", y);
        SmartDashboard.putNumber("Target X", x);
        SmartDashboard.putNumber("Gyro Angle", driveTrain.getAngle());
        SmartDashboard.putNumber("Robot angle", (absPosition));
        SmartDashboard.putNumber("Corrected distance", distance);
        SmartDashboard.putNumber("Corrected angle", angleFromTarget);
        SmartDashboard.putNumber("Total angle", (absPosition - angleFromTarget));

        PathGenerator pathGenerator = new PathGenerator(spacing, true);

        pathGenerator.addPoint(new Vector(0, 0));
        pathGenerator.addPoint(new Vector(x, 0.7*y));
        pathGenerator.addPoint(new Vector(x, 0.9*y));
        //pathGenerator.addPoint(new Vector(xTarget, 0.9*yTarget));

        pathGenerator.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        pathGenerator.setVelocities(15, 5, 1);

        Path path = pathGenerator.generatePath();

        purePursuitTracker.setRobotTrack(robotTrack);
        purePursuitTracker.setPaths(Collections.singletonList(path), 24);
        runAction(new PurePursuitAction(0));
        runAction(new WaitAction(1));
        //runAction(new TurnInPlaceAction(-SmartDashboard.getNumber("visionAngle0", 0), kAlignP, 0, 0));

        //runAction(new TurnInPlaceAction(-SmartDashboard.getNumber("visionAngle0", 0), kAlignP, 0, 0));

        /*runAction(new WaitAction(0.5));
        runAction(new AlignToTargetAction(1));
        runAction(new WaitAction(0.4));
        runAction(new FinishAlignAction(1));*/
    }
}

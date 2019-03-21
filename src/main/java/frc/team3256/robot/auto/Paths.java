package frc.team3256.robot.auto;

import frc.team3256.warriorlib.auto.purepursuit.Path;
import frc.team3256.warriorlib.auto.purepursuit.PathGenerator;
import frc.team3256.warriorlib.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static frc.team3256.robot.constants.DriveTrainConstants.*;

public abstract class Paths {
    private static List<Path> centerRightDoubleCargoHatch;
    private static List<Path> centerLeftDoubleCargoHatch;
    private static List<Path> baselineAutoPath;
    private static List<Path> backwardsAutoPath;
    private static List<Path> centerLeftSingleHatchPath;

    public static void initialize() {
        getCenterRightDoubleCargoHatch();
        getBaselineAutoPath();
        getCenterLeftSingleHatchAuto();
    }

    public static List<Path> getCenterRightDoubleCargoHatch() {
        if (centerRightDoubleCargoHatch != null)
            return centerRightDoubleCargoHatch;
        PathGenerator firstSegment = new PathGenerator(spacing, true);

        firstSegment.addPoint(new Vector(0,0));
        firstSegment.addPoint(new Vector(0,3.211716));
        firstSegment.addPoint(new Vector(0,10.420763));
        firstSegment.addPoint(new Vector(-51.8,110));
        firstSegment.addPoint(new Vector(-51.8,120));
        firstSegment.addPoint(new Vector(-51.8,130));
        firstSegment.addPoint(new Vector(-51.8,135)); // correct value: 136

        firstSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        firstSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path firstSegmentPath = firstSegment.generatePath();

        PathGenerator backSegmentOne = new PathGenerator(spacing, false);

        backSegmentOne.addPoint(new Vector(-51.8,136));
        backSegmentOne.addPoint(new Vector(-51.8,131));

        backSegmentOne.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backSegmentOne.setVelocities(maxVel, maxAccel, maxVelk);

        Path backOnePath = backSegmentOne.generatePath();

        PathGenerator secondSegment = new PathGenerator(spacing, true);

        secondSegment.addPoint(new Vector(-51.8,131));
        secondSegment.addPoint(new Vector(-45.561465,131.728441));
        secondSegment.addPoint(new Vector(-38.186881,130.927606));
        secondSegment.addPoint(new Vector(-25.241012,128.438429));
        secondSegment.addPoint(new Vector(-6.516056,122.121066));
        secondSegment.addPoint(new Vector(15.799654,109.752579));
        secondSegment.addPoint(new Vector(38.600518,90.818301));
        secondSegment.addPoint(new Vector(59.696425,67.062135));
        secondSegment.addPoint(new Vector(76.868319,40.723772));
        secondSegment.addPoint(new Vector(88.037241,14.476493));
        secondSegment.addPoint(new Vector(93.650591,-8.828362));
        secondSegment.addPoint(new Vector(95.521699,-10));
        secondSegment.addPoint(new Vector(95.521699,-15));
        secondSegment.addPoint(new Vector(95.521699,-18));
        secondSegment.addPoint(new Vector(95.267022,-21.124425));

        secondSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        secondSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path secondSegmentPath = secondSegment.generatePath();

        PathGenerator backSegmentTwo = new PathGenerator(spacing, false);

        backSegmentTwo.addPoint(new Vector(95.267022,-21.124425));
        backSegmentTwo.addPoint(new Vector(95.267022,-15.125752));

        backSegmentTwo.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backSegmentTwo.setVelocities(maxVel, maxAccel, maxVelk);

        Path backTwoPath = backSegmentTwo.generatePath();

        PathGenerator thirdSegment = new PathGenerator(spacing, true);

        thirdSegment.addPoint(new Vector(94.158554,-15.125752));
        thirdSegment.addPoint(new Vector(91.230643,4.266916));
        thirdSegment.addPoint(new Vector(83.852024,28.476564));
        thirdSegment.addPoint(new Vector(69.303536,53.969837));
        thirdSegment.addPoint(new Vector(47.113621,76.211246));
        thirdSegment.addPoint(new Vector(20.898808,92.976969));
        thirdSegment.addPoint(new Vector(-2.596718,108.498998));
        thirdSegment.addPoint(new Vector(-18.767476,115.545991));
        thirdSegment.addPoint(new Vector(-26.209281,123.442865));
        thirdSegment.addPoint(new Vector(-28.958754,127));
        thirdSegment.addPoint(new Vector(-28.958754,131));
        thirdSegment.addPoint(new Vector(-28.958754,136));

        thirdSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        thirdSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path thirdSegmentPath = thirdSegment.generatePath();

        PathGenerator backSegmentThree = new PathGenerator(spacing, false);

        backSegmentThree.addPoint(new Vector(-28.958754,136));
        backSegmentThree.addPoint(new Vector(-28.958754,131));

        backSegmentThree.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backSegmentThree.setVelocities(maxVel, maxAccel, maxVelk);

        Path backThreePath = backSegmentThree.generatePath();

        centerRightDoubleCargoHatch = Arrays.asList(firstSegmentPath, backOnePath, secondSegmentPath, backTwoPath, thirdSegmentPath, backThreePath);
        return centerRightDoubleCargoHatch;
    }

    public static List<Path> getBaselineAutoPath() {
        if (baselineAutoPath != null)
            return baselineAutoPath;

        PathGenerator firstSegment = new PathGenerator(spacing, true);

        firstSegment.addPoint(new Vector(0, 0));
        firstSegment.addPoint(new Vector(0, 30));
        firstSegment.addPoint(new Vector(0, 60));

        firstSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        firstSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path firstSegmentPath = firstSegment.generatePath();
        baselineAutoPath = Collections.singletonList(firstSegmentPath);
        return baselineAutoPath;
    }

    public static List<Path> getCenterLeftDoubleCargoHatch() {
        if (centerLeftDoubleCargoHatch != null)
            return centerLeftDoubleCargoHatch;
        PathGenerator firstSegment = new PathGenerator(spacing, true);

        firstSegment.addPoint(new Vector(0,0));
        firstSegment.addPoint(new Vector(0,3.211716));
        firstSegment.addPoint(new Vector(0,10.420763));
        firstSegment.addPoint(new Vector(51.8,110));
        firstSegment.addPoint(new Vector(51.8,120));
        firstSegment.addPoint(new Vector(51.8,130));
        firstSegment.addPoint(new Vector(51.8,133)); // correct value: 136

        firstSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        firstSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path firstSegmentPath = firstSegment.generatePath();

        PathGenerator backSegmentOne = new PathGenerator(spacing, false);

        backSegmentOne.addPoint(new Vector(51.8,133)); //136
        backSegmentOne.addPoint(new Vector(51.8,120)); //51.8

        backSegmentOne.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backSegmentOne.setVelocities(maxVel, maxAccel, maxVelk);

        Path backOnePath = backSegmentOne.generatePath();

        PathGenerator secondSegment = new PathGenerator(spacing, true);

//        secondSegment.addPoint(new Vector(51.8,131));
//        secondSegment.addPoint(new Vector(45.561465,131.728441));
//        secondSegment.addPoint(new Vector(38.186881,130.927606));
        secondSegment.addPoint(new Vector(51.8,120)); //51.8
        secondSegment.addPoint(new Vector(51.8, 126.438429));
        secondSegment.addPoint(new Vector(25.241012,127.438429));
        secondSegment.addPoint(new Vector(18,125));
        secondSegment.addPoint(new Vector(6.516056,122.121066));
        secondSegment.addPoint(new Vector(0, 118));
//
        secondSegment.addPoint(new Vector(-15.799654,109.752579));
//        secondSegment.addPoint(new Vector(-38.600518,90.818301));
//        secondSegment.addPoint(new Vector(-59.696425,67.062135));
//        secondSegment.addPoint(new Vector(-76.868319,40.723772));
//        secondSegment.addPoint(new Vector(-88.037241,14.476493));
//        secondSegment.addPoint(new Vector(-93.650591,-8.828362));
//        secondSegment.addPoint(new Vector(-95.521699,-10));
//        secondSegment.addPoint(new Vector(-95.521699,-15));
//        secondSegment.addPoint(new Vector(-95.521699,-18));
//        secondSegment.addPoint(new Vector(-95.267022,-21.124425));

        secondSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        secondSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path secondSegmentPath = secondSegment.generatePath();

        PathGenerator backSegmentTwo = new PathGenerator(spacing, false);

        backSegmentTwo.addPoint(new Vector(-95.267022,-21.124425));
        backSegmentTwo.addPoint(new Vector(-95.267022,-15.125752));

        backSegmentTwo.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backSegmentTwo.setVelocities(maxVel, maxAccel, maxVelk);

        Path backTwoPath = backSegmentTwo.generatePath();

        PathGenerator thirdSegment = new PathGenerator(spacing, true);

        thirdSegment.addPoint(new Vector(-94.158554,-15.125752));
        thirdSegment.addPoint(new Vector(-91.230643,4.266916));
        thirdSegment.addPoint(new Vector(-83.852024,28.476564));
        thirdSegment.addPoint(new Vector(-69.303536,53.969837));
        thirdSegment.addPoint(new Vector(-47.113621,76.211246));
        thirdSegment.addPoint(new Vector(-20.898808,92.976969));
        thirdSegment.addPoint(new Vector(2.596718,108.498998));
        thirdSegment.addPoint(new Vector(18.767476,115.545991));
        thirdSegment.addPoint(new Vector(26.209281,123.442865));
        thirdSegment.addPoint(new Vector(28.958754,127));
        thirdSegment.addPoint(new Vector(28.958754,131));
        thirdSegment.addPoint(new Vector(28.958754,133));

        thirdSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        thirdSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path thirdSegmentPath = thirdSegment.generatePath();

        PathGenerator backSegmentThree = new PathGenerator(spacing, false);

        backSegmentThree.addPoint(new Vector(28.958754,133));
        backSegmentThree.addPoint(new Vector(28.958754,127));

        backSegmentThree.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backSegmentThree.setVelocities(maxVel, maxAccel, maxVelk);

        Path backThreePath = backSegmentThree.generatePath();

        centerLeftDoubleCargoHatch = Arrays.asList(firstSegmentPath, backOnePath, secondSegmentPath, backTwoPath, thirdSegmentPath, backThreePath);
        return centerLeftDoubleCargoHatch;
    }

    public static List<Path> getBackwardsAutoPath() {
        if (backwardsAutoPath != null)
            return backwardsAutoPath;

        PathGenerator firstSegment = new PathGenerator(spacing, false);

        firstSegment.addPoint(new Vector(0, 0));
        firstSegment.addPoint(new Vector(0, -30));

        firstSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        firstSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path firstSegmentPath = firstSegment.generatePath();

        PathGenerator secondSegment = new PathGenerator(spacing, true);

        secondSegment.addPoint(new Vector(0,-30));
        secondSegment.addPoint(new Vector(0,0));

        secondSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        secondSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path secondSegmentPath = secondSegment.generatePath();

        backwardsAutoPath = Arrays.asList(firstSegmentPath, secondSegmentPath);
        return backwardsAutoPath;
    }

    public static List<Path> getCenterLeftSingleHatchAuto() {
        if (centerLeftSingleHatchPath != null)
            return centerLeftSingleHatchPath;

        PathGenerator firstSegment = new PathGenerator(spacing, true);

        firstSegment.addPoint(new Vector(0,0));
        firstSegment.addPoint(new Vector(28.9,110));
        firstSegment.addPoint(new Vector(28.9,120));
        firstSegment.addPoint(new Vector(28.9,130));
        firstSegment.addPoint(new Vector(28.9, 133));

        firstSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        firstSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path firstSegmentPath = firstSegment.generatePath();

        PathGenerator backSegOne = new PathGenerator(spacing, false);

        backSegOne.addPoint(new Vector(28.9, 133));
        backSegOne.addPoint(new Vector(28.9, 90));

        backSegOne.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backSegOne.setVelocities(maxVel, maxAccel, maxVelk);

        Path backSegOnePath = backSegOne.generatePath();

        PathGenerator secondSegment = new PathGenerator(spacing, true);

        secondSegment.addPoint(new Vector(0,0));
        secondSegment.addPoint(new Vector(0, 20));
        secondSegment.addPoint(new Vector(0, 30));
        secondSegment.addPoint(new Vector(0, 34.2));
        secondSegment.addPoint(new Vector(-75, 34.2));
        secondSegment.addPoint(new Vector(-85, 34.2));
        secondSegment.addPoint(new Vector(-90, 34.2));

        secondSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        secondSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path secondSegmentPath = secondSegment.generatePath();

        centerLeftSingleHatchPath = Arrays.asList(firstSegmentPath, backSegOnePath, secondSegmentPath);
        return centerLeftSingleHatchPath;
    }

}

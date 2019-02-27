package frc.team3256.robot.auto;

import frc.team3256.warriorlib.auto.purepursuit.Path;
import frc.team3256.warriorlib.auto.purepursuit.PathGenerator;
import frc.team3256.warriorlib.math.Vector;

import java.util.Arrays;
import java.util.List;
import static frc.team3256.robot.constants.DriveTrainConstants.*;

public abstract class Paths {
    public static List<Path> getCenterRightDoubleCargoHatch() {
        PathGenerator firstSegment = new PathGenerator(spacing, true);

        firstSegment.addPoint(new Vector(0,0));
        firstSegment.addPoint(new Vector(0,3.211716));
        firstSegment.addPoint(new Vector(0,10.420763));
//        firstSegment.addPoint(new Vector(-2.621082,23.100732));
//        firstSegment.addPoint(new Vector(-16,62));
//        firstSegment.addPoint(new Vector(-27.160984,83.810593));
//        firstSegment.addPoint(new Vector(-37.658183,105.74539));
//        firstSegment.addPoint(new Vector(-45.461634,125.795271));
//        firstSegment.addPoint(new Vector(-49.781664,142.353346));
        firstSegment.addPoint(new Vector(-51.8,110));
        firstSegment.addPoint(new Vector(-51.8,120));
        firstSegment.addPoint(new Vector(-51.8,130));
        firstSegment.addPoint(new Vector(-51.8,139));

        firstSegment.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        firstSegment.setVelocities(maxVel, maxAccel, maxVelk);

        Path firstSegmentPath = firstSegment.generatePath();

        PathGenerator backTest = new PathGenerator(spacing, false);

        backTest.addPoint(new Vector(-51.8,139));
        backTest.addPoint(new Vector(-51.8,108));

        backTest.setSmoothingParameters(purePursuitA, purePursuitB, smoothingTolerance);
        backTest.setVelocities(maxVel, maxAccel, maxVelk);

        Path backTestPath = backTest.generatePath();

        return Arrays.asList(firstSegmentPath, backTestPath);
    }
}

package frc.team3256.robot.path;

import frc.team3256.robot.math.Vector;
import frc.team3256.robot.operations.Constants;

public class Tester {
    public static void main (String[] args) {
        Path p = new Path(1, 0.78, Constants.spacing, 0.001);
        p.addSegment(new Vector(0, 0), new Vector(0, 60));
        p.addSegment(new Vector(0, 60), new Vector(0, 120));
        p.addLastPoint();
        PurePursuitTracker purePursuitTracker = new PurePursuitTracker(p, 5);

        Vector currPos = new Vector(0, 0);
        //System.out.print(p.calcIntersectionPoint(start, end, currPos, 5));
        //System.out.println(initPurePursuitTracker.calculateVectorLookAheadPoint(start, end, currPos, 20));
        //System.out.println(p.getTotalPathDistance());
        purePursuitTracker.update(currPos, 0, Math.PI/2.0);
        //System.out.println(initPurePursuitTracker.getClosestPoint(currPos));

    }
}

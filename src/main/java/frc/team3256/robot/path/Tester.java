package frc.team3256.robot.path;

import frc.team3256.robot.math.Vector;
import frc.team3256.robot.operations.Constants;

public class Tester {
    public static void main (String[] args) {
        Path p = new Path(1, 0.78, Constants.spacing, 0.001);
        Vector intial = new Vector(0,0);
        Vector two = new Vector(0,100);
        p.robotPath.add(intial);
        p.robotPath.add(two);

        PurePursuitTracker purePursuitTracker = new PurePursuitTracker(p, 5);

        Vector currPos = new Vector(0, 0);
        //System.out.print(p.calcIntersectionPoint(start, end, currPos, 5));
        //System.out.println(purePursuitTracker.calculateVectorLookAheadPoint(start, end, currPos, 20));
        //System.out.println(p.getTotalPathDistance());
        purePursuitTracker.update(currPos, 0, Math.PI/2.0);
        System.out.println(purePursuitTracker.getClosestPoint(currPos));
    }
}

package frc.team3256.robot.path;

import frc.team3256.robot.math.Vector;

public class Tester {
    public static void main (String[] args) {
        Path p = new Path(1, 0.78, 0.001);
        Vector intial = new Vector(0,0);
        Vector start = new Vector(0,15);
        Vector end = new Vector(10,45);
        p.robotPath.add(intial);
        p.robotPath.add(start);
        p.robotPath.add(end);
        PurePursuitTracker purePursuitTracker = new PurePursuitTracker(p, 20, 0);


        Vector currPos = new Vector(5, 33);
        //System.out.print(p.calcIntersectionPoint(start, end, currPos, 5));
        System.out.println(purePursuitTracker.calcVectorLookAheadPoint(start, end, currPos, 20));
        System.out.println(p.getTotalPathDistance());

    }
}

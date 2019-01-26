package frc.team3256.robot.path;

import frc.team3256.robot.math.Vector;
import frc.team3256.robot.operations.Constants;

public class Tester {
    public static void main (String[] args) {
        Path p = new Path(Constants.a, Constants.b, Constants.spacing, 0.001);
        p.addSegment(new Vector(0,0), new Vector(0, 30));
        p.addSegment(new Vector(0, 30), new Vector(70, 60));
        p.addSegment(new Vector(70, 60), new Vector(70, 80));
        p.addSegment(new Vector(70, 80), new Vector(70, 100));
        p.addLastPoint();
        p.smooth(Constants.a, Constants.b, Constants.tolerance);
        p.setTargetVelocities(Constants.maxVel, Constants.maxAccel, Constants.maxVelk);
        for (Vector v : p.robotPath) {
            System.out.println("x=" + v.x + " y=" + v.y + " z=" + v.z + " vel=" + v.getVelocity());
        }
    }
}

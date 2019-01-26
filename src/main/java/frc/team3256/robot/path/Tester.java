package frc.team3256.robot.path;

import frc.team3256.robot.operations.Constants;
import frc.team3256.warriorlib.auto.purepursuit.Path;
import frc.team3256.warriorlib.math.Vector;

public class Tester {
	public static void main(String[] args) {
		Path p = new Path(Constants.spacing);
		p.addSegment(new Vector(0, 0), new Vector(0, 30));
		p.addSegment(new Vector(0, 30), new Vector(70, 60));
		p.addSegment(new Vector(70, 60), new Vector(70, 80));
		p.addSegment(new Vector(70, 80), new Vector(70, 100));
		p.addLastPoint();
		p.smooth(Constants.a, Constants.b, Constants.tolerance);
		p.setTargetVelocities(Constants.maxVel, Constants.maxAccel, Constants.maxVelk);
		for (Vector v : p.getRobotPath()) {
			System.out.println("x=" + v.x + " y=" + v.y + " z=" + v.z + " vel=" + v.getVelocity());
		}
	}
}

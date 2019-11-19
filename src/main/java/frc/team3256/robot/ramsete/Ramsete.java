package frc.team3256.robot.ramsete;

import frc.team3256.robot.path.Waypoint;
import org.opencv.core.Mat;

public class Ramsete {

    public double calculateVelocity(double x, double y, double theta, Waypoint waypoint, double b, double g) {
        double k = (2*g)*(Math.sqrt((Math.pow(waypoint.getAngularVelocity(), 2) + (b*Math.pow(waypoint.getVelocity(), 2)))));
        double v = (waypoint.getVelocity()*Math.cos(waypoint.getHeading() - theta)) + (k*(((waypoint.getX()-x) * Math.cos(theta)) + ((waypoint.getY()-y) * Math.sin(theta))));
        return v;
    }

    public double calculateAngularVelocity(double x, double y, double theta, Waypoint waypoint, double b, double g) {

        double v = waypoint.getVelocity();
        double angV = waypoint.getAngularVelocity();
        double eAngle = waypoint.getHeading() - theta;
        System.out.println("waypoint: " + waypoint);

        double k = (2*g)*(Math.sqrt((Math.pow(waypoint.getAngularVelocity(), 2) + (b*Math.pow(waypoint.getVelocity(), 2)))));
        System.out.println("eAngle = "+eAngle);
        double sinc = (Math.sin(eAngle))/(eAngle);
        if (eAngle == 0.0) {
            sinc = 1;
        }
        double w = angV + b*v*sinc*((waypoint.getY() - y) * Math.cos(theta) - (waypoint.getX() - x) * Math.sin(theta)) + (k*eAngle);
        if (Double.isNaN(w)) {
            return 0.0;
        }
        double sgn = Math.signum(w);
        w = Math.abs(w) % (2* Math.PI);
        w = sgn * w;
        if (w > Math.PI) {
            w -= 2*Math.PI;
        }
        return w;
    }

    public static void main(String args[]) {

        Ramsete r = new Ramsete();
        Waypoint wp = new Waypoint(.02, .745, 9.019, 22.34, 0.0);    // 216
        wp.setAngularVelocity(0.0);
        double angvel = r.calculateAngularVelocity(.68, 9.519, 0.0, wp,0.0508,1.3);
        double linvel = r.calculateVelocity(.68, 9.519, 0.0, wp,0.0508,1.3);
        System.out.println("ang vel: "+angvel);
        System.out.println("lin vel: "+linvel);
        double leftvel = linvel + (angvel * 27.0 / 2.0);
        double rightvel = linvel - (angvel * 27.0 / 2.0);
        System.out.println("left vel: "+leftvel);
        System.out.println("right vel: "+rightvel);

    }
}


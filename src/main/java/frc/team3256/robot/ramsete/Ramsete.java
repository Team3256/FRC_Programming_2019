package frc.team3256.robot.ramsete;

import frc.team3256.robot.path.Waypoint;

public class Ramsete {

    public double calculateVelocity(double x, double y, double theta, Waypoint waypoint, double b, double g) {
        double k = (2*g)*(Math.sqrt((Math.pow(waypoint.getAngularVelocity(), 2) + (b*Math.pow(waypoint.getVelocity(), 2)))));
        double v = (waypoint.getVelocity()*Math.cos(waypoint.getHeading() - theta)) + (k*(((waypoint.getX()-x) * Math.cos(theta)) + ((waypoint.getY()-y) * Math.sin(theta))));
        return v;
    }

    public double calculateAngularVelocity(double x, double y, double theta, Waypoint waypoint, double b, double g) {

        double k = (2*g)*(Math.sqrt((Math.pow(waypoint.getAngularVelocity(), 2) + (b*Math.pow(waypoint.getVelocity(), 2)))));
        double sinc = (Math.sin(waypoint.getHeading()-theta))/(waypoint.getHeading()-theta);
        double w = (waypoint.getAngularVelocity()) + (b*waypoint.getVelocity()*sinc*(((waypoint.getY()-y) * Math.cos(theta)) - ((waypoint.getX()-x) * Math.sin(theta)))) + (k*(waypoint.getHeading()-theta));
        if (Double.isNaN(w)) {
            return 0.0;
        }
        return w;
    }
    public static void main(String args[]) {

        Ramsete r = new Ramsete();
        Waypoint wp = new Waypoint(.02, .745, 9.956, 22.34, .1515);    // 216
        double angvel = r.calculateAngularVelocity(.68, 9.519, .144, wp,2.2,.55);
        double linvel = r.calculateVelocity(.68, 9.519, .144, wp,2.2,.55);
        System.out.println("ang vel: "+angvel);
        System.out.println("lin vel: "+linvel);
        double leftvel = linvel + (angvel * 27.0 / 2.0);
        double rightvel = linvel - (angvel * 27.0 / 2.0);
        System.out.println("left vel: "+leftvel);
        System.out.println("right vel: "+rightvel);

    }
}


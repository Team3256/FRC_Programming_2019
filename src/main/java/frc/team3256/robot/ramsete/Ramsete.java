package frc.team3256.robot.ramsete;

import path.Waypoint;

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
        return w;
    }
}

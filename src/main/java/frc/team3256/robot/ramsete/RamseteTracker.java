package frc.team3256.robot.ramsete;

import frc.team3256.robot.control.DrivePower;
import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.path.Waypoint;

import java.util.ArrayList;

public class RamseteTracker {

    private static RamseteTracker instance;
    private Ramsete ramsete = new Ramsete();
    private double robotTrack = Constants.robotTrack;
    private ArrayList<Waypoint> waypoints;
    private int waypointIndex = -1;

    public static RamseteTracker getInstance() {
        return instance == null ? instance = new RamseteTracker() : instance;
    }

    public void setPath(ArrayList<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     Translation of linear velocity and angular velocity to left wheel and right wheel velocities:
     According to skid-steer robot kinematics:
     V = (L+R)/2
     W = (L-R)/T
     By solving a system of equations, we get:
     L = V + (WT/2)
     R = V - (WT/2)
     Where L is the left wheel velocity, R is the right wheel target velocity, V is the linear velocity, W is
     the angular velocity, and T is the robot track.
     */

        public DrivePower update(double poseX, double poseY, double poseTheta, double b, double g) {
            waypointIndex++;
            Waypoint waypoint = waypoints.get(waypointIndex);
//            System.out.println("Pose: " + poseX + "," + poseY + "," + poseTheta);
            System.out.println(waypoint);
        double velocity = ramsete.calculateVelocity(poseX, poseY, poseTheta, waypoint, b, g);
        //System.out.println("vel: " + velocity);
        double angularVelocity = ramsete.calculateAngularVelocity(poseX, poseY, poseTheta, waypoint, b, g);
        double leftVelocity = velocity + ((angularVelocity * robotTrack)/2);
        double rightVelocity = velocity - ((angularVelocity * robotTrack)/2);
//        System.out.println("left vel: " + leftVelocity);
//        System.out.println("right vel: " + rightVelocity);
//        return new DrivePower(leftVelocity/2.0, rightVelocity/2.0, true);
            return new DrivePower(waypoint.getVelocity(), waypoint.getVelocity(), true);
    }

    public void reset() {
        waypointIndex = -1;
    }

    public boolean isFinished() {
        return waypointIndex == waypoints.size()-1;
    }
}

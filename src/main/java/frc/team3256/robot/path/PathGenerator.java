package frc.team3256.robot.path;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PathGenerator {
    ArrayList<Waypoint> waypoints = new ArrayList<>();
    private String line = "";
    private String splitBy = ",";

    public ArrayList<Waypoint> generatePath(String csvFile) {
        double startIndex = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (startIndex == 0) {
                    startIndex = 1;
                }
                else {
                    String[] pathInfo = line.split(splitBy);
                    double heading;
                    if (360 - Math.toDegrees(Double.valueOf(pathInfo[7])) < 1) {
                        heading = 360 - Math.toDegrees(Double.valueOf(pathInfo[7]));
                    }
                    else {
                        heading = Math.toDegrees(Double.valueOf(pathInfo[7]));
                    }
                    waypoints.add(new Waypoint(Double.valueOf(pathInfo[0]), Double.valueOf(pathInfo[2]), Double.valueOf(pathInfo[1]), Double.valueOf(pathInfo[4]), heading));
                }
            }
            waypoints.get(0).setAngularVelocity(0);
            for (int i = 1; i < waypoints.size(); i++) {
                double initialHeading = waypoints.get(i-1).getHeading();
                double finalHeading = waypoints.get(i).getHeading();
                double deltaTime = waypoints.get(i).getTime();
                waypoints.get(i).setAngularVelocity(initialHeading, finalHeading, deltaTime);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return waypoints;
    }

    public static void main(String[] args) {
        PathGenerator path = new PathGenerator();
        ArrayList<Waypoint> w = path.generatePath("C:\\Users\\WB17\\Documents\\Ramsete Test Paths\\Straight.pf1.csv");
        for (int i = 0; i < w.size(); i++) {
            System.out.println(w.get(i));
        }
    }

}

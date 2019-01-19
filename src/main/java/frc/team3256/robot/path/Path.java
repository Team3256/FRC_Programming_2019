package frc.team3256.robot.path;

import frc.team3256.robot.operations.Constants;
import frc.team3256.robot.math.Vector;
import frc.team3256.robot.operations.Sign;

import java.util.ArrayList;
import java.util.Arrays;


public class Path {

    double spacing = Constants.spacing; //spacing between points in inches
    double distance; //in inches
    double a, b, tolerance;
    ArrayList<frc.team3256.robot.math.Vector> robotPath = new ArrayList<>();

    public Path(double a, double b, double tolerance) {
        this.a = a;
        this.b = b;
        this.tolerance = tolerance;
        distance = 0;
    }
    
    public Vector getStartPoint() {
        return robotPath.get(0);
    }
    
    public Vector getEndPoint() {
        return robotPath.get(robotPath.size()-1);
    }

    public void initializePath() {
        setCurvature();
        setDistances();
        setTargetVelocities(Constants.maxVel, Constants.maxAccel, Constants.maxVelk);
    }

    //adds a start and end point Vector to the robotPath ArrayList

    public void addSegment(Vector start, Vector end) {
        ArrayList<Vector> injectTemp = new ArrayList<>();
        injectPoints(start, end, injectTemp);
        System.out.println(injectTemp);
        //ArrayList<Vector> smoothTemp = smooth(injectTemp, a, b, tolerance);
        //System.out.println(smoothTemp.size());
        if (robotPath.size() == 0) {
            for (int i = 0; i < injectTemp.size(); i++) {
                robotPath.add(injectTemp.get(i));
            }
        }
        else {
            for (int i = 0; i < injectTemp.size() - 1; i++) {
                robotPath.add(injectTemp.get(i));
            }
        }
        for (Vector v : robotPath) {
            System.out.println(v);
        }
        System.out.println("RPath Size: " + robotPath.size());
    }

    //methods to transfer to make arrays -> lists and list -> arrays

    private double [][] makeArray(ArrayList<Vector> pts) {

        double [][] path = new double [pts.size()][2];
        for (int i = 0; i < pts.size(); i++) {
            path[i][0] = pts.get(i).x;
            path[i][1] = pts.get(i).y;
        }

        return path;

    }

    private ArrayList<Vector> makeList(double [][] pts) {

        ArrayList<Vector> path = new ArrayList<>();
        for (int i = 0; i < pts.length; i ++){
            path.add(new Vector(pts[i][0], pts[i][1]));
        }

        return path;
    }

    //makes a copy of a double array

    private double [][] doubleArrayCopy(double [][] array) {
        double [][] newArray = new double[array.length][];
        for(int i = 0; i < array.length; i++)
            newArray[i] = Arrays.copyOf(array[i], array[i].length);
        return newArray;
    }

    //methods to populate the path with more points, then to smooth the points in the path

    private void injectPoints(Vector startPt, Vector endPt, ArrayList<Vector> temp) {

        Vector vector = new Vector(Vector.sub(endPt, startPt, null));
        double num_pts_that_fit = Math.ceil(vector.norm() / spacing);
        Vector unitVector = vector.normalize(null);
        unitVector.mult(vector.norm() / num_pts_that_fit);
        for (int i = 0; i < num_pts_that_fit; i++) {
            Vector newVector = Vector.mult(unitVector, i, null);
            temp.add(Vector.add(startPt, newVector, null));
        }
        temp.add(endPt);
    }


    private ArrayList<Vector> smooth(ArrayList<Vector> vectorPath, double a, double b, double tolerance) {

        double [][] path = makeArray(vectorPath);

        double [][] newPath = doubleArrayCopy(path);
        double change = tolerance;

        while (change >= 0) {
            change = 0;
            for (int i = 1; i < path.length - 1; i++) {
                for (int j = 1; j < path[i].length; j++) {
                    double aux = newPath[i][j];
                    newPath[i][j] += a * (path[i][j] - newPath[i][j]) + b * (newPath[i-1][j] + newPath[i+1][j] - (2.0 * newPath[i][j]));
                    change += Math.abs(aux - newPath[i][j]);
                }
            }
        }
        return makeList(newPath);

    }

    //calculations for point attributes (curvature and max velocity)

    private double calculatePathCurvature(ArrayList<Vector> path, int pointIndex) {
        Vector point = new Vector(path.get(pointIndex));
        Vector prevPoint = new Vector(path.get(pointIndex - 1));
        Vector nextPoint = new Vector(path.get(pointIndex + 1));

        double productOfSides = Vector.dist(point, prevPoint) * Vector.dist(point, nextPoint) * Vector.dist(nextPoint, prevPoint);
        double semiPerimeter = (Vector.dist(point, prevPoint) + Vector.dist(point, nextPoint) + Vector.dist(nextPoint, prevPoint))/2;
        double triangleArea = Math.sqrt(semiPerimeter * (semiPerimeter - Vector.dist(point, prevPoint)) * (semiPerimeter - Vector.dist(point, nextPoint)) * (semiPerimeter - Vector.dist(nextPoint, prevPoint)));

        double radius = (productOfSides)/(4 * triangleArea);
        double curvature = 1/radius;

        return curvature;
    }

    private double calculateMaxVelocity(ArrayList<Vector> path, int point, double pathMaxVel, double k) {
        if (point > 0) {

            double curvature = calculatePathCurvature(path, point);
            return Math.min(pathMaxVel, k/curvature); //k is a constant (generally between 1-5 based on how quickly you want to make the turn)

        }
        return pathMaxVel;
    }

    public double calculateCurrDistance(int point) {
        return robotPath.get(point).getDistance();
    }

    public double getTotalPathDistance() {
        return calculateCurrDistance(robotPath.size()-1);
    }


    //setter methods that iterate through and set attributes to robotPath

    public void setCurvature() {
        getStartPoint().setCurvature(0);
        getEndPoint().setCurvature(0);
        for (int i = 1; i < robotPath.size()-1; i++){
            robotPath.get(i).setCurvature(calculatePathCurvature(robotPath, i));
        }
    }

    public void setTargetVelocities(double maxVel, double maxAccel, double k) {
        robotPath.get(robotPath.size() - 1).setVelocity(0);
        for (int i = robotPath.size() - 2; i >= 0; i--) {
            distance = Vector.dist(robotPath.get(i+1), robotPath.get(i));
            double maxReachableVel = Math.sqrt(Math.pow(robotPath.get(i+1).getVelocity(),2) + (2 * maxAccel * distance));
            robotPath.get(i).setVelocity(Math.min(calculateMaxVelocity(robotPath, i, maxVel, k), maxReachableVel));
        }
    }

    public void setDistances() {
        double distance = 0;
        getStartPoint().setDistance(0);
        for (int i = 1; i < robotPath.size(); i++){
            distance += Vector.sub(robotPath.get(i), robotPath.get(i-1)).norm();
            robotPath.get(i).setDistance(distance);
        }
    }

    //calculating the curvature necessary for a lookahead arc


    public double calculateCurvatureLookAheadArc(Vector currPos, double heading, Vector lookahead, double lookaheadDistance) {
        double a = -Math.tan(heading);
        double b = 1;
        double c = (Math.tan(heading)*currPos.x) - currPos.y;
        double x = Math.abs(a * lookahead.x + b * lookahead.y + c)/Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double cross = (Math.sin(heading) * (lookahead.x - currPos.x)) - (Math.cos(heading) * (lookahead.y - currPos.y));
        double side = Sign.getSign(cross);
        double curvature = (2 * x)/(Math.pow(lookaheadDistance, 2));
        return curvature * side;
    }



}

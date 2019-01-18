package frc.team3256.robot.operations;

public class DrivePower {
    double left;
    double right;
    boolean highGear;

    public DrivePower(double left, double right, boolean highGear) {
        this.left = left;
        this.right = right;
        this.highGear = highGear;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public boolean highGear() {return highGear;}

    //port 1 and 6  for shifter

}

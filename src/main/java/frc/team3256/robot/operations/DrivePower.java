package frc.team3256.robot.operations;

public class DrivePower {
    double left;
    double right;

    public DrivePower(double left, double right) {
        this.left = left;
        this.right = right;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}

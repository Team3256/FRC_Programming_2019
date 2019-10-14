package frc.team3256.robot.path;

public class Waypoint {
    private double dt;
    private double x;
    private double y;
    private double velocity;
    private double heading;
    private double angular_velocity;

    public Waypoint(double dt, double x, double y, double velocity, double heading) {
        this.dt = dt;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.heading = heading;
        angular_velocity = 0;
    }

    public void setAngularVelocity(double headingI, double headingF, double deltaTime) {
        double calc = (headingF - headingI) / (deltaTime);
        this.angular_velocity = calc;
    }

    public void setAngularVelocity(double value) {
        this.angular_velocity = value;
    }

    public double getTime() {
        return dt;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getHeading() {
        return heading;
    }

    public double getAngularVelocity() {
        return angular_velocity;
    }

    @Override
    public String toString() {
        return "dt: "+dt+", x: "+x+", y: "+y+", velocity: "+velocity+", heading: "+heading+", angularVel: "+angular_velocity;
    }
}

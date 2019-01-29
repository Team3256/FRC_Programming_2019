package frc.team3256.robot.operation;

import java.util.Timer;
import java.util.TimerTask;

public class TestController {
    public static void main(String[] args) {
        TeleopUpdater teleopUpdater = TeleopUpdater.getInstance();
        Timer timer = new Timer();
        TimerTask robotTask = new TimerTask() {
            @Override
            public void run() {
                // whatever you need to do every 2 seconds
                teleopUpdater.update();
            }
        };

        timer.schedule(robotTask, 0, 30);
    }
}
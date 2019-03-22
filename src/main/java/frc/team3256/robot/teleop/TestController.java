package frc.team3256.robot.teleop;

import frc.team3256.robot.subsystems.Elevator;

import java.util.Timer;
import java.util.TimerTask;

public class TestController {
    public static void main(String[] args) {
        TeleopUpdater teleopUpdater = TeleopUpdater.getInstance();
        Timer timer = new Timer();
        TimerTask robotTask = new TimerTask() {
            @Override
            public void run() {
                teleopUpdater.update();
                Elevator.getInstance().update(1);
            }
        };

        timer.schedule(robotTask, 0, 300);
    }
}

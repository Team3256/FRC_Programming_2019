package frc.team3256.robot.teleop.control;

import edu.wpi.first.wpilibj.Joystick;
import frc.team3256.robot.auto.ZeroElevatorMode;
import frc.team3256.warriorlib.auto.AutoModeExecuter;
import frc.team3256.warriorlib.control.ControllerObserver;
import frc.team3256.warriorlib.control.XboxListenerBase;

public class GameCubeControllerObserver implements ControllerObserver {
    private static int Y = 1;
    private static int B = 2;
    private static int A = 3;
    private static int X = 4;
    private static int LEFT_BUMPER = 5;
    private static int RIGHT_BUMPER = 6;
    private static int LEFT_TRIGGER = 7;
    private static int RIGHT_TRIGGER = 8;
    private static int SELECT = 9;
    private static int START = 10;

    private Joystick gamecubeController;
    private XboxListenerBase xboxListenerBase;

    private double previousLeftX = 0.0;
    private double previousLeftY = 0.0;

    private double previousRightX = 0.0;
    private double previousRightY = 0.0;

    public GameCubeControllerObserver(int port) {
        gamecubeController = new Joystick(port);
    }

    @Override
    public void setListener(XboxListenerBase xboxListenerBase) {
        this.xboxListenerBase = xboxListenerBase;
    }

    @Override
    public void update() {
        if (gamecubeController.getRawButtonPressed(A)) {
            xboxListenerBase.onAPressed();
        }
        if (gamecubeController.getRawButtonReleased(A)) {
            xboxListenerBase.onAReleased();
        }

        if (gamecubeController.getRawButtonPressed(B)) {
            xboxListenerBase.onBPressed();
        }
        if (gamecubeController.getRawButtonReleased(B)) {
            xboxListenerBase.onBReleased();
        }

        if (gamecubeController.getRawButtonPressed(X)) {
            xboxListenerBase.onXPressed();
        }
        if (gamecubeController.getRawButtonReleased(X)) {
            xboxListenerBase.onXReleased();
        }

        if (gamecubeController.getRawButtonPressed(Y)) {
            xboxListenerBase.onYPressed();
        }
        if (gamecubeController.getRawButtonReleased(Y)) {
            xboxListenerBase.onYReleased();
        }

        if (gamecubeController.getRawButtonPressed(START)) {
            xboxListenerBase.onStartPressed();
        }
        if (gamecubeController.getRawButtonReleased(START)) {
            xboxListenerBase.onStartReleased();
        }

        if (gamecubeController.getRawButtonPressed(SELECT)) {
            xboxListenerBase.onSelectedPressed();
        }
        if (gamecubeController.getRawButtonReleased(SELECT)) {
            xboxListenerBase.onSelectedReleased();
        }

        if (gamecubeController.getRawButtonPressed(LEFT_BUMPER)) {
            xboxListenerBase.onLeftShoulderPressed();
        }
        if (gamecubeController.getRawButtonReleased(LEFT_BUMPER)) {
            xboxListenerBase.onLeftShoulderReleased();
        }

        if (gamecubeController.getRawButtonPressed(RIGHT_BUMPER)) {
            xboxListenerBase.onRightShoulderPressed();
        }
        if (gamecubeController.getRawButtonReleased(RIGHT_BUMPER)) {
            xboxListenerBase.onRightShoulderReleased();
        }

        if (gamecubeController.getRawButtonPressed(LEFT_TRIGGER)) {
            xboxListenerBase.onLeftTrigger(1.0);
        }
        if (gamecubeController.getRawButtonReleased(LEFT_TRIGGER)) {
            xboxListenerBase.onLeftTrigger(0.0);
        }

        if (gamecubeController.getRawButtonPressed(RIGHT_TRIGGER)) {
            xboxListenerBase.onRightTrigger(1.0);
        }
        if (gamecubeController.getRawButtonReleased(RIGHT_TRIGGER)) {
            xboxListenerBase.onRightTrigger(0.0);
        }

        if (gamecubeController.getRawButtonPressed(13)) {
            AutoModeExecuter autoModeExecuter = new AutoModeExecuter();
            autoModeExecuter.setAutoMode(new ZeroElevatorMode());
            autoModeExecuter.start();
        }
        if (gamecubeController.getRawButtonPressed(14)) {
            //Elevator.getInstance().setPositionHome();
        }

        double leftX = gamecubeController.getX();
        double leftY = gamecubeController.getY();

        double rightX = gamecubeController.getZ();
        double rightY = gamecubeController.getThrottle();

        leftX = Math.abs(leftX) > 0.25 ? leftX : 0.0;
        leftY = Math.abs(leftY) > 0.25 ? leftY : 0.0;

        rightX = Math.abs(rightX) > 0.25 ? rightX : 0.0;
        rightY = Math.abs(rightY) > 0.25 ? rightY : 0.0;

        if (leftX != previousLeftX ||
                leftY != previousLeftY) {
            xboxListenerBase.onLeftJoystick(leftX, -leftY);
            previousLeftX = leftX;
            previousLeftY = leftY;
        }

        if (rightX != previousRightX ||
                rightY != previousRightY) {
            xboxListenerBase.onRightJoystick(rightX, -rightY);
            previousRightX = rightX;
            previousRightY = rightY;
        }
    }
}

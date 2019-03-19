package frc.team3256.robot.teleop.control;

import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.XInputButtons;
import com.github.strikerx3.jxinput.XInputComponents;
import com.github.strikerx3.jxinput.XInputDevice;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;

public class DesktopXboxManipulatorController implements IManipulatorController {
    private static DesktopXboxManipulatorController instance;
    public static DesktopXboxManipulatorController getInstance() {
        return instance == null ? instance = new DesktopXboxManipulatorController() : instance;
    }

    XInputButtons buttons;
    XInputAxes axes;
    XInputDevice xboxController;

    private DesktopXboxManipulatorController() {
        try {
            xboxController = XInputDevice.getDeviceFor(0); // or devices[0]
            xboxController.poll();
            XInputComponents components = xboxController.getComponents();
            buttons = components.getButtons();
            axes = components.getAxes();
        } catch (XInputNotLoadedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(10);
                    xboxController.poll();
                    XInputComponents components = xboxController.getComponents();
                    buttons = components.getButtons();
                    axes = components.getAxes();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public boolean getShouldClimb() {
        return buttons.back;
    }

    @Override
    public double getElevatorThrottle() {
        return Math.abs(axes.ly) > 0.15 ? axes.ly : 0;
    }

    @Override
    public double getPivotThrottle() {
        return Math.abs(axes.ry) > 0.15 ? axes.ry : 0;
    }

    @Override
    public boolean shouldCargoIntake() {
        return axes.rt > 0.15;
    }

    @Override
    public boolean shouldCargoOuttake() {
        return axes.lt > 0.15;
    }

    @Override
    public boolean shouldHatchIntake() {
        return buttons.rShoulder;
    }

    @Override
    public boolean shouldHatchOuttake() {
        return buttons.lShoulder;
    }

    @Override
    public boolean goToHigh() {
        return buttons.y;
    }

    @Override
    public boolean goToMid() {
        return buttons.x;
    }

    @Override
    public boolean goToLow() {
        return buttons.a;
    }

    @Override
    public boolean goToCargoShip() {
        return buttons.start;
    }

    @Override
    public boolean goToHome() {
        return buttons.b;
    }
}

package frc.team3256.robot.teleop.control;

import frc.team3256.robot.subsystems.CargoIntake;
import frc.team3256.robot.subsystems.Hanger;
import frc.team3256.robot.teleop.TeleopUpdater;

public class ClimbControlScheme extends CommonControlScheme {
    Hanger hanger = Hanger.getInstance();
    CargoIntake cargoIntake = CargoIntake.getInstance();

    @Override
    public void onAPressed() {
        
    }

    @Override
    public void onBPressed() {

    }

    @Override
    public void onXPressed() {

    }

    @Override
    public void onYPressed() {

    }

    @Override
    public void onAReleased() {

    }

    @Override
    public void onBReleased() {

    }

    @Override
    public void onXReleased() {

    }

    @Override
    public void onYReleased() {

    }

    @Override
    public void onLeftDpadPressed() {

    }

    @Override
    public void onRightDpadPressed() {

    }

    @Override
    public void onUpDpadPressed() {

    }

    @Override
    public void onDownDpadPressed() {

    }

    @Override
    public void onLeftDpadReleased() {

    }

    @Override
    public void onRightDpadReleased() {

    }

    @Override
    public void onUpDpadReleased() {

    }

    @Override
    public void onDownDpadReleased() {

    }

    @Override
    public void onStartPressed() {
        if (getController() != null) {
            Thread thread = new Thread(() -> {
                try {
                    getController().setRumble(1.0);
                    Thread.sleep(120);
                    getController().setRumble(0);
                    Thread.sleep(60);
                    getController().setRumble(1.0);
                    Thread.sleep(120);
                    getController().setRumble(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        TeleopUpdater.getInstance().changeToCargoControlScheme();
    }

    @Override
    public void onSelectedReleased() {

    }

    @Override
    public void onStartReleased() {

    }

    @Override
    public void onLeftJoystickPressed() {

    }

    @Override
    public void onRightJoystickPressed() {

    }

    @Override
    public void onLeftJoystickReleased() {

    }

    @Override
    public void onRightJoystickReleased() {

    }
}

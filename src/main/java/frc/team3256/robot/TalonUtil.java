package frc.team3256.robot;


import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team3256.robot.constants.Constants;

/**
 * Utility class to generate talon objects
 */
public class TalonUtil{

    public static TalonSRX generateGenericTalon(int id) {
        TalonSRX talon = new TalonSRX(id);
        talon.set(ControlMode.PercentOutput, 0);
        //disable brake mode on default
        talon.setNeutralMode(NeutralMode.Brake);
        //no default reverse
        talon.setSensorPhase(false);
        talon.setInverted(false);
        //no current limit on default
        talon.enableCurrentLimit(false);
        //no soft limits on default
        talon.configForwardSoftLimitEnable(false, 10);
        talon.configReverseSoftLimitEnable(false, 10);
        //no hard limits on default
        talon.overrideLimitSwitchesEnable(false);
        //no ramping on default
        talon.configOpenloopRamp(0,10);
        talon.configNominalOutputReverse(0, 10);
        talon.configNominalOutputForward(0, 10);
        talon.configPeakOutputForward(1.0, 10);
        talon.configPeakOutputReverse(-1.0, 10);
        talon.configClosedloopRamp(0, 10);
        return talon;
    }

    public static TalonSRX generateSlaveTalon(int id, int masterId) {
        TalonSRX talon = new TalonSRX(id);
        talon.set(ControlMode.Follower, masterId);
        //we don't really need slaves to update fast, so update at 1hz
        /*talon.setControlFramePeriod(ControlFrame.Control_3_General, 1000);
        talon.setControlFramePeriod(ControlFrame.Control_4_Advanced, 1000);
        talon.setControlFramePeriod(ControlFrame.Control_6_MotProfAddTrajPoint, 1000);
        talon.setStatusFramePeriod(StatusFrame.Status_1_General, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_6_Misc, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_7_CommStatus, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 1000, 10);
        talon.setStatusFramePeriod(StatusFrame.Status_15_FirmwareApiStatus, 1000, 10); */
        //enable brake mode on default
        talon.setNeutralMode(NeutralMode.Brake);
        //no default reverse
        talon.setSensorPhase(false);
        talon.setInverted(false);
        //no current limit on default
        talon.enableCurrentLimit(false);
        //no soft limits on default
        talon.configForwardSoftLimitEnable(false, 10);
        talon.configReverseSoftLimitEnable(false, 10);
        //no hard limits on default
        talon.overrideLimitSwitchesEnable(false);
        //no ramping on default
        talon.configOpenloopRamp(0, 10);
        talon.configClosedloopRamp(0, 10);
        //set minimum and maximum voltage output for talons
        talon.configNominalOutputForward(0, 10);
        talon.configNominalOutputReverse(0, 10);
        talon.configPeakOutputForward(1.0, 10);
        talon.configPeakOutputReverse(-1.0, 10);
        return talon;
    }

    public static void setPIDGains(TalonSRX talon, int slot, double kP, double kI, double kD, double kF){
        talon.config_kP(slot, kP, 0);
        talon.config_kI(slot, kI, 0);
        talon.config_kD(slot, kD, 0);
        talon.config_kF(slot, kF, 0);
    }

    public static void configMagEncoder(TalonSRX talon){
        if (talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)!= ErrorCode.OK){
            DriverStation.reportError("DID NOT DETECT MAG ENCODER ON TALON " + talon.getDeviceID(), false);
        }
        talon.getStatusFramePeriod(StatusFrame.Status_2_Feedback0, (int)(1000* 1/200));
    }

    public static void setCurrentLimit(TalonSRX talon, int peakAmps, int continuousAmps, int continuousDuration){
        talon.configPeakCurrentLimit(peakAmps, 0);
        talon.configContinuousCurrentLimit(continuousAmps, continuousDuration);
    }

    public static void setBrakeMode(TalonSRX... talons){
        for(TalonSRX talon : talons){
            talon.setNeutralMode(NeutralMode.Brake);
        }
    }

    public static void setCoastMode(TalonSRX... talons){
        for(TalonSRX talon : talons){
            talon.setNeutralMode(NeutralMode.Coast);
        }
    }

    public static void setPeakOutput(double peakFwd, double peakRev, TalonSRX... talons){
        for(TalonSRX talon : talons){
            talon.configPeakOutputForward(peakFwd, 0);
            talon.configPeakOutputReverse(peakRev, 0);
        }
    }

    public static void setMinOutput(double minFwd, double minRev, TalonSRX... talons){
        for(TalonSRX talon : talons){
            talon.configNominalOutputForward(minFwd, 0);
            talon.configNominalOutputReverse(minRev, 0);
        }
    }
}
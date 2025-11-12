package org.firstinspires.ftc.teamcode.core.util.pid;

import com.acmerobotics.dashboard.FtcDashboard;

public class PIDRegulator {
    private double KP = 0;
    private double KD = 0;
    private double KI = 0;
    public double setpoint = 0;
    private double iniegral_err = 0;
    private double old_err = 0;

    public PIDRegulator(double KP, double KI, double KD) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
    }
    public PIDRegulator(double KP, double KI, double KD, double setpoint) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.setpoint = setpoint;
    }

    public void setCoefficients(double KP, double KI, double KD) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
    }

    public double PIDGet(double input, double setpoint){
        double err = input - setpoint;
        double d = this.old_err - err;
        double i = this.iniegral_err;
        this.old_err = err;
        this.iniegral_err += err;
        return err * this.KP + d * this.KD + i * this.KI;
    }
    public double PIDGet(double input){
        double err = this.setpoint - input;
        double d = this.old_err - err;
        double i = this.iniegral_err;
        this.old_err = err;
        this.iniegral_err += err;
        return err * this.KP + d * this.KD + i * this.KI;
    }

    public double getOldErr() {
        return old_err;
    }
}
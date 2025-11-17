package org.firstinspires.ftc.teamcode.core.util.pid;

import com.acmerobotics.dashboard.FtcDashboard;

public class PIDRegulator {
    private double KP = 0;
    private double KD = 0;
    private double KI = 0;
    public double setpoint = 0;
    private double iniegral_err = 0;
    private double old_err = 0;
    private double old_time = 0;

    public PIDRegulator() {

    }

    public PIDRegulator(double KP, double KI, double KD) {
        setCoefficients(KP, KI, KD);
    }
    public PIDRegulator(double KP, double KI, double KD, double setpoint) {
        setCoefficients(KP, KI, KD);
        this.setpoint = setpoint;
    }

    public void setCoefficients(double KP, double KI, double KD) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
    }

    public double PIDGet(double input, double setpoint){
        this.setpoint = setpoint;
        return PIDGet(input);
    }
    public double PIDGet(double input){
        double time = System.nanoTime() / 1_000_000_000.0;
        double dt = time - old_time;
        double err = this.setpoint - input;
        double d = (err - old_err) / dt;
        double i = this.iniegral_err;

        if(dt > 0.1) {
            this.iniegral_err = 0;
            d = 0;
            i = 0;
        }

        this.old_err = err;
        this.iniegral_err += err * dt;
        this.old_time = time;

        return err * KP + d * KD * KP + i * KI * KP;
    }

    public double getOldErr() {
        return old_err;
    }
}
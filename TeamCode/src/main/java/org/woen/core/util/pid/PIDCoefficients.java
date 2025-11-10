package org.woen.core.util.pid;


import com.acmerobotics.dashboard.config.Config;


@Config
public /* data */ class PIDCoefficients {
    public static double kP;
    public static double kI;
    public static double kD;


    public PIDCoefficients(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double getKP() {
        return kP;
    }

    public void setKP(double kP) {
        this.kP = kP;
    }

    public double getKI() {
        return kI;
    }

    public void setKI(double kI) {
        this.kI = kI;
    }

    public double getKD() {
        return kD;
    }

    public void setKD(double kD) {
        this.kD = kD;
    }
}

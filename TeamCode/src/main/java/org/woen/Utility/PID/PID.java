package org.woen.Utility.PID;

import static java.lang.Math.abs;

public class PID {
    double kP = 0;
    double kI = 0;
    double kD = 0;
    double ui = 0;
    double errold;
    double told;

    double pos;
    double target;

    double[] kF = new double[3];


    public PID(double p, double i, double d, double f0, double f1, double f2, double f3) {
        kP = p;
        kI = i;
        kD = d;
        kF[0] = f0;
        kF[1] = f1;
        kF[2] = f2;
        kF[3] = f3;

    }



    public void setTarget(double target){
        this.target = target;
    }

    public double update(double err) {


        double time = System.currentTimeMillis() / 1000.0;
        double up = err * kP;
        ui += (err * kI) * (time - told);

        if (abs(ui) > 0.25) {
            ui = 0.25;
        }
        double ud = (err - errold) * kD / (time - told);
        errold = err;
        told = time;

        double uf = kF[0]
                + kF[1] * target
                + kF[2] * target * target
                + kF[3] * target * target * target;

        return ui + ud + up + uf;
    }

    public void reset(){
        ui=0;
        told=System.currentTimeMillis()/1000.0;
    }
}

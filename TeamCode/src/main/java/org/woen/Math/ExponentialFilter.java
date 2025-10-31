package org.woen.Math;

import com.qualcomm.robotcore.util.ElapsedTime;

public class ExponentialFilter {


    private double y;

    private ElapsedTime elapsedTime = new ElapsedTime();

    public ExponentialFilter(){

    }


    public void update(double k, double value, double value2) {
        double deltaT = elapsedTime.seconds();

        y += value2 + value * (deltaT / (k + deltaT));
        elapsedTime.reset();
    }

    public double getY() {
        return y;
    }


}

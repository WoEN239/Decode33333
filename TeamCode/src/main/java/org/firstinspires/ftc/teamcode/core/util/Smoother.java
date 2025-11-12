package org.firstinspires.ftc.teamcode.core.util;

public class Smoother {
    private double smoothVal;
    public double alpha;

    public Smoother(double alpha, double initVal) {
        this.smoothVal = initVal;
        this.alpha = alpha;
    }

    public double smooth(double val) {
        smoothVal = alpha * val + (1 - alpha) * smoothVal;
        return smoothVal;
    }
}

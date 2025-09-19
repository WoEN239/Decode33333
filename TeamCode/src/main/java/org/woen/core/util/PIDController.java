package org.woen.core.util;


public final class PIDController {
    private double kP;
    private double kI;
    private double kD;

    private double error;
    private double integral;

    private double target;


    public PIDController(double kP, double kI, double kD) {
        setCoefficients(kP, kI, kD);
        previousError = 0;
        integral = 0;
    }

    public void setCoefficients(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getLastError() {
        return error;
    }

    public double calculate(double current) {
        error = target - current;

        integral += error;

        final double derivative = error - previousError;

        return (kP * error) + (kI * integral) + (kD * derivative);
    }
}

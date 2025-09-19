package org.woen.core.util;


public final class PIDController {
    private double kP;
    private double kI;
    private double kD;

    private double lastError;
    private double integral;

    private double target;


    public PIDController(double kP, double kI, double kD) {
        setCoefficients(kP, kI, kD);
        lastError = 0;
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
        return lastError;
    }

    public double calculate(double current) {
        final double error = target - current;

        integral += error;

        final double derivative = error - lastError;

        return (kP * error) + (kI * integral) + (kD * derivative);
    }
}

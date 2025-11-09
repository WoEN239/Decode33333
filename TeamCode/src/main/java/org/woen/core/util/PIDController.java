package org.woen.core.util;


public final class PIDController {
    public static final double SECONDS_IN_NANOSECOND = 1.E-9;

    private double kP;
    private double kI;
    private double kD;

    private double lastError;
    private double integral;

    private double target;

    private double integralLimit;

    private double lastNanoTimeStamp;


    public PIDController(double kP, double kI, double kD, double integralLimit) {
        setCoefficients(kP, kI, kD);
        setIntegralLimit(integralLimit);
        setTarget(0);
        lastError = 0;
        integral = 0;
        lastNanoTimeStamp = System.nanoTime();
    }

    public void setCoefficients(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double getIntegralLimit() {
        return integralLimit;
    }

    public void setIntegralLimit(double integral) {
        integralLimit = Math.abs(integral);
    }

    public void resetIntegral() {
        integral = 0;
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

        final double currentNanoTimeStamp = System.nanoTime();
        final double dT = (currentNanoTimeStamp - lastNanoTimeStamp) / SECONDS_IN_NANOSECOND;
        lastNanoTimeStamp = currentNanoTimeStamp;

        integral += error * kI * dT;
        if (Math.abs(integral) > integralLimit) {
            integral = integralLimit * Math.signum(integral);
        }

        final double derivative = (error - lastError) / dT;
        lastError = error;

        return (kP * error) + integral + (kD * derivative);
    }
}

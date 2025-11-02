package org.woen.core.util.pid;


import org.woen.core.util.Constants;


public final class PIDController {
    private PIDCoefficients coefficients;

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

    public PIDController(PIDCoefficients coefficients, double integralLimit) {
        this(coefficients.getKP(),
                coefficients.getKI(),
                coefficients.getKD(),
                integralLimit);
    }

    public PIDCoefficients getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(PIDCoefficients coefficients) {
        this.coefficients = coefficients;
    }

    public void setCoefficients(double kP, double kI, double kD) {
        coefficients.setKP(kP);
        coefficients.setKI(kI);
        coefficients.setKD(kD);
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
        final double dT =
                (currentNanoTimeStamp - lastNanoTimeStamp) / Constants.SECONDS_IN_NANOSECOND;
        lastNanoTimeStamp = currentNanoTimeStamp;

        integral += error * coefficients.getKI() * dT;
        if (Math.abs(integral) > integralLimit) {
            integral = integralLimit * Math.signum(integral);
        }

        final double derivative = (error - lastError) / dT;
        lastError = error;

        return (coefficients.getKP() * error) + integral + (coefficients.getKD() * derivative);
    }
}

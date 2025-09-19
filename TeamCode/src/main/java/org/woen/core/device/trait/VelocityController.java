package org.woen.core.device.trait;


public interface VelocityController {
    enum ControlMode {
        RAW,
        PID,
    }


    ControlMode getVelocityControlMode();
    void setVelocityControlMode(ControlMode mode);

    void setPIDCoefficients(double kP, double kI, double kD);

    double getVelocityTarget();
    void setVelocityTarget(double target);
    void velocityTick();

    double getVelocity();

    /**
     * Combination of setVelocityTarget() und velocityTick()
     */
    void setVelocity(double velocity);
}

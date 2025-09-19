package org.woen.core.device.trait;


public interface VelocityController {
    enum ControlMode {
        RAW,
        PID;

        public boolean isPIDControlled() {
            return this == PID;
        }
    }


    ControlMode getVelocityControlMode();
    void setVelocityControlMode(ControlMode mode);

    void setPIDCoefficients(double kP, double kI, double kD);

    double getTargetVelocity();
    void setTargetVelocity(double target);
    void velocityTick();

    double getVelocity();

    /**
     * Combination of setVelocityTarget() und velocityTick()
     */
    default void setVelocity(double velocity) {
        setTargetVelocity(velocity);
        velocityTick();
    }
}

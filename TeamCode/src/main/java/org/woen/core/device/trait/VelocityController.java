package org.woen.core.device.trait;


public interface VelocityController {
    enum ControlMode {
        /**
         * Control using only sleep()-like functions.
         */
        TIMER,

        /**
         * Control using encoder
         */
        OWN_ENCODER,
        THIRD_PARTY_ENCODER,

        /**
         * Control using current (amperage) and user's coefficients
         */
        AMPERAGE,

        /**
         * No control, just wrappers for xxxPower() methods
         */
        RAW,
    }


    ControlMode getVelocityControlMode();
    void setVelocityControlMode(ControlMode mode) throws UnsupportedOperationException;
    boolean isVelocityControlModeSupported(ControlMode mode);

    void setPIDCoefficients(double kP, double kI, double kD);

    double getVelocity();

    /**
     * @throws InterruptedException only in TIMER control mode
     */
    void setVelocity(double velocity) throws InterruptedException;

    Encoder getLinkedEncoder();
    void linkEncoder(Encoder encoder);
}

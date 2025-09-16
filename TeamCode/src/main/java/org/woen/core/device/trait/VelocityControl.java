package org.woen.core.device.trait;


import org.woen.core.util.NotImplementedException;


public interface VelocityControl {
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

    void setVelocityControlMode(ControlMode mode) throws NotImplementedException;
    ControlMode getVelocityControlMode();
    boolean isVelocityControlModeSupported(ControlMode mode);

    // TODO: void setVelocityCoefficients(...);
    void setThirdPartyEncoder(Encoder encoder);

    double getVelocity();
    void setVelocity(double velocity);
}

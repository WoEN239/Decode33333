package org.woen.core.device.trait;


public interface VelocityControl extends Encoder {
    double getVelocity();
    void setVelocity(double velocity);
}

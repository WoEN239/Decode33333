package org.woen.core.device.trait;

public interface Encoder extends Directional {
    int getEncoderPosition();
    void resetEncoder();
}

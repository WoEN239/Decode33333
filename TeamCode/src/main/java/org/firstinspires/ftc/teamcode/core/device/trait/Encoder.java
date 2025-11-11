package org.firstinspires.ftc.teamcode.core.device.trait;

public interface Encoder extends Directional {
    int getEncoderPosition();
    double getEncoderSpeed();
    void resetEncoder();
}

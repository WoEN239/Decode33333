package org.firstinspires.ftc.teamcode.core.trait.device;


public interface IEncoder extends IDevice, IDirectional {
    int getEncoderPosition();
    double getEncoderVelocity();
    void resetEncoder();
}

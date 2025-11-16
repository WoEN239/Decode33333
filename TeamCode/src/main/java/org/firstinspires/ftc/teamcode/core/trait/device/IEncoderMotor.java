package org.firstinspires.ftc.teamcode.core.trait.device;


public interface IEncoderMotor extends IMotor, IEncoder {
    default double getVelocity() {
        return getEncoderVelocity();
    }

    void setVelocity(double velocity);
}

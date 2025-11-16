/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.implementation;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.trait.device.IEncoder;
import org.firstinspires.ftc.teamcode.core.trait.device.IEncoderMotor;


/**
 * Encoder without a motor, such as an odometer.
 *
 * @see IEncoder
 *
 * @see EncoderMotor
 */
public final class Encoder implements IEncoder {
    /*
     * It's bad to implement a lot of identical methods twice,
     * so we use IEncoderMotor.
     **/
    private final IEncoderMotor encoderMotor;


    public Encoder(String name) {
        encoderMotor = new EncoderMotor(name);
    }

    @Override
    public String getName() {
        return encoderMotor.getName();
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        encoderMotor.initialize(hardwareMap);
    }

    @Override
    public boolean isInitialized() {
        return encoderMotor.isInitialized();
    }

    @Override
    public void enable() {
        encoderMotor.enable();
    }

    @Override
    public void disable() {
        encoderMotor.enable();
    }

    @Override
    public boolean isEnabled() {
        return encoderMotor.isEnabled();
    }

    @Override
    public int getEncoderPosition() {
        return encoderMotor.getEncoderPosition();
    }

    @Override
    public double getEncoderVelocity() {
        return encoderMotor.getEncoderVelocity();
    }

    @Override
    public void resetEncoder() {
        encoderMotor.resetEncoder();
    }

    @Override
    public Direction getDirection() {
        return encoderMotor.getDirection();
    }

    @Override
    public void setDirection(Direction direction) {
        encoderMotor.setDirection(direction);
    }
}

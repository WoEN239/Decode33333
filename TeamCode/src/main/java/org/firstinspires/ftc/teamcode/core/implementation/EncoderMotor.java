/**
 * @authors Arsen Berezin, Timophey Istomin
 */

package org.firstinspires.ftc.teamcode.core.implementation;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.trait.device.IEncoderMotor;
import org.firstinspires.ftc.teamcode.core.util.exception.UnimplementedException;


/**
 * Just motor with an encoder.
 *
 * @see IEncoderMotor
 *
 * @see Encoder
 * @see Motor
 */
public class EncoderMotor extends Motor implements IEncoderMotor {
    /**
     * Encoder offset relative to its standard zero position
     * that may be non-zero after device initialization.
     *
     * @implNote
     *  Should only be set once, not counting the constructor,
     *  and only inside {@link EncoderMotor#initialize(HardwareMap)} method
     */
    protected int encoderOffset;


    public EncoderMotor(String name) {
        super(name);
        encoderOffset = 0;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        assert hardwareMap != null;

        super.initialize(hardwareMap);

        /*
         * We shouldn't multiply current position
         * by direction sign, because in getEncoderPosition
         * we will firstly subtract offset from raw position/velocity
         * and then multiply by direction
         */
        encoderOffset = device.getCurrentPosition();
    }

    @Override
    public void setVelocity(double velocity) throws UnimplementedException {
        throw new UnimplementedException();
    }

    @Override
    public int getEncoderPosition() {
        assert isInitialized();
        return (device.getCurrentPosition() - encoderOffset) * direction.getSign();
    }

    @Override
    public double getEncoderVelocity() {
        assert isInitialized();
        return device.getVelocity() * direction.getSign();
    }

    @Override
    public void resetEncoder() {
        assert isInitialized();

        device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}

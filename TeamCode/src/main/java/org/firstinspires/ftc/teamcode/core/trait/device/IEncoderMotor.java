/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.trait.device;


/**
 * Interface describing a motors with encoder.
 *
 * @see IEncoder
 * @see IMotor
 * @see IDevice
 *
 * @see org.firstinspires.ftc.teamcode.core.implementation
 */
public interface IEncoderMotor extends IMotor, IEncoder {
    default double getVelocity() {
        return getEncoderVelocity();
    }

    void setVelocity(double velocity);
}

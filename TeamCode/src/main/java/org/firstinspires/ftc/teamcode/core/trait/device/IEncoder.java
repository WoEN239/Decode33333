/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.trait.device;


/**
 * Interface describing devices with encoders.
 *
 * @see IDirectional
 * @see IDevice
 *
 * @see org.firstinspires.ftc.teamcode.core.implementation
 */
public interface IEncoder extends IDevice, IDirectional {
    int getEncoderPosition();
    double getEncoderVelocity();
    void resetEncoder();

    void enable();
    void disable();
    boolean isEnabled();
}

/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.trait.device;



/**
 * Interface describing motors.
 *
 * @see IDirectional
 * @see IDevice
 *
 * @see org.firstinspires.ftc.teamcode.core.implementation
 */
public interface IMotor extends IDevice, IDirectional {
    double getPower();
    void setPower(double power);

    void enable();
    void disable();
    boolean isEnabled();
}

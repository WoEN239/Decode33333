/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.trait.device;


import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * Main hardware device root-interface.
 *
 * @see org.firstinspires.ftc.teamcode.core.implementation
 */
public interface IDevice {
    String getName();

    default boolean hasName() {
        return getName() != null;
    }

    void initialize(HardwareMap hardwareMap);
    boolean isInitialized();

    void enable();
    void disable();
    boolean isEnabled();
}

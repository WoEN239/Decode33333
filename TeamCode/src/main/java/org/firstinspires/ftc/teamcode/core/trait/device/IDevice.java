package org.firstinspires.ftc.teamcode.core.trait.device;


import com.qualcomm.robotcore.hardware.HardwareMap;


public interface IDevice {
    void initialize(HardwareMap hardwareMap);
    boolean isInitialized();

    boolean isEnabled();
    void enable();
    void disable();

    String getName();

    default boolean hasName() {
        return getName() != null;
    }
}

package org.woen.core.device.trait;


import com.qualcomm.robotcore.hardware.HardwareMap;


public interface Initializable {
    void initialize(HardwareMap hardwareMap);
    boolean isInitialized();
}

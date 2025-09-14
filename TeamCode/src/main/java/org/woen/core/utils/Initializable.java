package org.woen.core.utils;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;


public interface Initializable {
    void initialize(@NonNull HardwareMap hardwareMap);
    boolean isInitialized();
}

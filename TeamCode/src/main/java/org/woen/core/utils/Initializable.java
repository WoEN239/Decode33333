package org.woen.core.utils;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;


public abstract class Initializable {
    protected boolean initialized = false;


    public abstract void initialize(@NonNull HardwareMap hardwareMap);

    public boolean isInitialized() {
        return initialized;
    }
}

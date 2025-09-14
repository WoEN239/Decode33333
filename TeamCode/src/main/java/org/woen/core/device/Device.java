package org.woen.core.device;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;


public abstract class Device {
    protected boolean initialized;
    protected final String name;


    protected Device(String name) {
        this.name = name;
    }

    public abstract void initialize(HardwareMap hardwareMap);

    public boolean isInitialized() {
        return initialized;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}

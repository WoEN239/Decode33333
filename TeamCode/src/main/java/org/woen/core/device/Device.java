package org.woen.core.device;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.utils.InitializableWith;


public abstract class Device implements InitializableWith<HardwareMap> {
    protected boolean initialized = false;
    protected final String name;


    protected Device(String name) {
        this.name = name;
    }

    @Override
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

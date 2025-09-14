package org.woen.core.device;


import androidx.annotation.NonNull;

import org.woen.core.utils.Initializable;


public abstract class Device implements Initializable {
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

package org.woen.core.device;


import org.woen.core.device.trait.Initializable;


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

    public String getName() {
        return name;
    }
}

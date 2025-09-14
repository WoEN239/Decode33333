package org.woen.core.device;


import androidx.annotation.NonNull;

import org.woen.core.utils.Initializable;


public abstract class Device extends Initializable {
    protected final String name;


    protected Device(String name) {
        this.name = name;
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

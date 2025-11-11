package org.firstinspires.ftc.teamcode.core.device;


import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;


public abstract class Device implements Initializable {
    protected final String name;


    protected Device(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }
}

package org.firstinspires.ftc.teamcode.core.device;


import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;


public abstract class Device implements Initializable {
    /*
     * Can be null if raw device does not have a name
     */
    protected final String name;


    protected Device(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }
}

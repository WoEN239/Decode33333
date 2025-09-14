package org.woen.main.revolver;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.utils.Initializable;


public final class Gun extends Initializable {
    private static final Gun INSTANCE = new Gun();


    Motor motor;


    private Gun() {
        motor = new Motor("gun_motor");
    }

    @NonNull
    public static Gun getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(@NonNull HardwareMap hardwareMap) {
        if (isInitialized()) return;

        motor.initialize(hardwareMap);

        initialized = true;
    }
}

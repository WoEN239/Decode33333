package org.woen.main.revolver;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.device.trait.Initializable;


public final class Gun implements Initializable {
    private static final Gun INSTANCE = new Gun();

    private final Motor motor;


    private Gun() {
        motor = new Motor("gun_motor");
    }

    public static Gun getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        motor.initialize(hardwareMap);
    }

    @Override
    public boolean isInitialized() {
        return motor.isInitialized();
    }
}

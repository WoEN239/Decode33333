package org.woen.main.revolver;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.device.sensors.SensorColor;
import org.woen.core.utils.Initializable;


public final class SortingRoll extends Initializable {
    private static final SortingRoll INSTANCE = new SortingRoll();


    private final Motor motor;
    private final SensorColor colorSensor;


    private SortingRoll() {
        motor = new Motor("sorting_roll_motor");
        colorSensor = new SensorColor("sorting_roll_color_sensor");
    }

    @NonNull
    public static SortingRoll getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(@NonNull HardwareMap hardwareMap) {
        if (isInitialized()) return;

        motor.initialize(hardwareMap);
        colorSensor.initialize(hardwareMap);

        initialized = true;
    }
}

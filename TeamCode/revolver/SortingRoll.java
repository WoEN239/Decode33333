package org.woen.main.revolver;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.device.sensor.SensorColor;
import org.woen.core.device.trait.Initializable;


public final class SortingRoll implements Initializable {
    private static final SortingRoll INSTANCE = new SortingRoll();

    private final Motor motor;
    private final SensorColor colorSensor;


    private SortingRoll() {
        motor = new Motor("sorting_roll_motor");
        colorSensor = new SensorColor("sorting_roll_color_sensor");
    }

    public static SortingRoll getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        motor.initialize(hardwareMap);
        colorSensor.initialize(hardwareMap);
    }

    @Override
    public boolean isInitialized() {
        return motor.isInitialized() && colorSensor.isInitialized();
    }
}

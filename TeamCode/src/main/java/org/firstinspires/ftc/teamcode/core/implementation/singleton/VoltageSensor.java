/**
 * @author Matvey Ivanov
 */

package org.firstinspires.ftc.teamcode.core.implementation.singleton;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.implementation.Motor;
import org.firstinspires.ftc.teamcode.core.trait.device.IDevice;


/**
 * Voltage sensor
 */
public final class VoltageSensor implements IDevice {
    private static final VoltageSensor INSTANCE = new VoltageSensor();

    private com.qualcomm.robotcore.hardware.VoltageSensor device;
    private final double desiredVoltage;


    public static VoltageSensor getInstance() {
        return INSTANCE;
    }

    private VoltageSensor() {
        device = null;
        desiredVoltage = 12.8;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.voltageSensor.iterator().next();
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    public double getVoltage() {
        assert isInitialized();
        return device.getVoltage();
    }

    public double calculateCoefficientVoltage(double power) {
        return Motor.normalizePower(power) * (desiredVoltage / getVoltage());
    }
}

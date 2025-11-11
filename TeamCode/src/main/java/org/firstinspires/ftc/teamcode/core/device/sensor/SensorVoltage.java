package org.firstinspires.ftc.teamcode.core.device.sensor;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public final class SensorVoltage {
    private VoltageSensor voltageSensor;
    private double powerInput;
    private double powerOutput;
    private double desiredVoltage = 12.8;
    private final HardwareMap hardwareMap;

    public SensorVoltage(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.voltageSensor = hardwareMap.voltageSensor.iterator().next();
    }

    public double getVoltageSensor() {
        return voltageSensor.getVoltage();
    }

    public void setDesiredVoltage(double desiredVoltage) {
        this.desiredVoltage = desiredVoltage;
    }

    public double getDesiredVoltage() {
        return desiredVoltage;
    }

    public double calculateCoefficientVoltage(double powerInput) {
        this.powerInput = powerInput;
        powerOutput = this.powerInput * (desiredVoltage / getVoltageSensor());
        if (powerInput > 1.0) powerInput = 1.0;
        if (powerInput < -1.0) powerInput = -1.0;
        return powerOutput;
    }
}

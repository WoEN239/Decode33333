package org.firstinspires.ftc.teamcode.core.device.sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.Device;


public final class SensorVoltage extends Device {
    private VoltageSensor voltageSensor;
    private double powerInput;
    private double powerOutput;
    private double desiredVoltage = 12.8;
    private HardwareMap hardwareMap;

    public SensorVoltage(String name) {
        super(name);
        voltageSensor = null;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;
        this.hardwareMap = hardwareMap;
        this.voltageSensor = hardwareMap.voltageSensor.iterator().next();
    }

    @Override
    public boolean isInitialized() {
        return voltageSensor != null;
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

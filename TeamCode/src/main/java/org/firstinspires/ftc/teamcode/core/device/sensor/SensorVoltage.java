package org.firstinspires.ftc.teamcode.core.device.sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.Device;

// coding by Matvey Ivanovv

public class SensorVoltage extends Device {
    private static final org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage INSTANCE = new org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage();
    protected VoltageSensor voltageSensor;
    private double powerInput;
    private double powerOutput;
    private double desiredVoltage = 12.8;

    public SensorVoltage() {
        super(null);
        voltageSensor = null;
    }

    public static org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;
        voltageSensor = hardwareMap.voltageSensor.iterator().next();
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

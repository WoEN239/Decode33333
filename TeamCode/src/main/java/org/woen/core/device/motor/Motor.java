package org.woen.core.device.motor;


import org.woen.core.device.Device;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device {
    protected DcMotorEx device;


    public Motor(String name) {
        super(name);
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);
        device.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        initialized = true;
    }

    public double getPower() {
        return device.getPower();
    }

    /**
     * Do not use this method for
     * velocity (speed) control
     * because you may break the motor,
     * instead use class EncoderMotor
     * or use class Motor combined
     * with class Odometer (own impl).
     *
     * @param power the new motor's power level in the range [-1; 1]
     */
    public void setPower(double power) {
        device.setPower(power);
    }

    public Direction getDirection() {
        return device.getDirection();
    }

    public void setDirection(Direction direction) {
        device.setDirection(direction);
    }

    public void invertDirection() {
        setDirection(getDirection().inverted());
    }

    public void enable() {
        device.setMotorEnable();
    }

    public void disable() {
        device.setMotorDisable();
    }

    public boolean isEnabled() {
        return device.isMotorEnabled();
    }

    public boolean isBusy() {
        return device.isBusy();
    }
}

package org.woen.core.device.odometer;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.Device;
import org.woen.core.device.trait.Encoder;


public class Odometer extends Device implements Encoder {
    protected DcMotorEx device;


    public Odometer(String name) {
        super(name);
        device = null;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);

        device.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    @Override
    public int getEncoderPosition() {
        return device.getCurrentPosition();
    }

    @Override
    public void resetEncoder() {
        device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        device.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void setDirection(Direction direction) {
        device.setDirection(direction);
    }

    @Override
    public Direction getDirection() {
        return device.getDirection();
    }

    @Override
    public void invertDirection() {
        setDirection(getDirection().inverted());
    }
}

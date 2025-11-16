package org.firstinspires.ftc.teamcode.core.implementation;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.trait.device.IEncoder;


public final class Odometer implements IEncoder {
    private final String name;
    private DcMotorEx device;
    private int offset;
    private Direction direction;


    public Odometer(String name) {
        assert name != null;

        this.name = name;
        device = null;
        offset = 0;
        direction = Direction.FORWARD;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        assert hardwareMap != null;

        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        offset = device.getCurrentPosition() * direction.getSign();
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    @Override
    public boolean isEnabled() {
        assert isInitialized();
        return device.isMotorEnabled();
    }

    @Override
    public void enable() {
        assert isInitialized();
        device.setMotorEnable();
    }

    @Override
    public void disable() {
        assert isInitialized();
        device.setMotorDisable();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getEncoderPosition() {
        assert isInitialized();
        return (device.getCurrentPosition() - offset) * getDirection().getSign();
    }

    @Override
    public double getEncoderVelocity() {
        assert isInitialized();
        return (device.getVelocity() - offset) * getDirection().getSign();
    }

    @Override
    public void resetEncoder() {
        assert isInitialized();

        device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        assert direction != null;
        this.direction = direction;
    }
}

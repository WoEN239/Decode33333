package org.firstinspires.ftc.teamcode.core.device.odometer;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.Device;
import org.firstinspires.ftc.teamcode.core.device.trait.Encoder;


public class Odometer extends Device implements Encoder {
    protected DcMotorEx device;
    private int offset;


    public Odometer(String name) {
        super(name);
        device = null;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);

        offset = device.getCurrentPosition();
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    @Override
    public int getEncoderPosition() {
        return device.getCurrentPosition() - offset;
    }

    @Override
    public double getEncoderSpeed() {
        return device.getVelocity();
    }

    @Override
    public void resetEncoder() {
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

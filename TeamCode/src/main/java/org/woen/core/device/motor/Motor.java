package org.woen.core.device.motor;


import org.woen.core.device.Device;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device {
    public enum Direction {
        FORWARD(1),
        BACKWARD(-1);

        public final int integer;

        Direction(int integer) {
            this.integer = integer;
        }
    }


    private DcMotorEx device = null;
    private Direction direction = Direction.FORWARD;


    public Motor(String name) {
        super(name);
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);

        initialized = true;
    }

    public double getPower() {
        return device.getPower() * direction.integer;
    }

    public void setPower(double power) {
        device.setPower(power * direction.integer);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void reverseDirection() {
        setDirection(direction == Direction.FORWARD
                ? Direction.BACKWARD
                : Direction.FORWARD);
    }
}

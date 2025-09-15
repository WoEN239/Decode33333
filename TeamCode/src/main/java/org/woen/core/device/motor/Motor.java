package org.woen.core.device.motor;


import org.woen.core.device.Device;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device {
    protected DcMotorEx device = null;


    public Motor(String name) {
        super(name);
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);

        device.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        initialized = true;
    }

    public double getPower() {
        return device.getPower();
    }

    /**
     * Do not use this method for
     * velocity (speed) control,
     * because it may break the motor.
     * And do not set power to 0,
     * because it also breaks the motor.
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
}

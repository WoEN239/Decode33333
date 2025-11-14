package org.firstinspires.ftc.teamcode.core.device.motor;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.core.device.Device;
import org.firstinspires.ftc.teamcode.core.device.trait.Directional;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device implements Directional {
    protected DcMotorEx device;


    public Motor(String name) {
        super(name);
        device = null;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);

        setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    public double getPower() {
        return device.getPower();
    }

    public void setPower(double power) {
        device.setPower(power);
    }

    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() {
        return device.getZeroPowerBehavior();
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        device.setZeroPowerBehavior(behavior);
    }

    @Override
    public Direction getDirection() {
        return device.getDirection();
    }

    @Override
    public void setDirection(Direction direction) {
        device.setDirection(direction);
    }

    @Override
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

    /**
     * Get the current (amperage) consumed by this motor.
     * @param unit current units
     * @return the current consumed by this motor.
     */
    public double getCurrent(CurrentUnit unit) {
        return device.getCurrent(unit);
    }


    public static double normalizePower(double power) {
        if (power < -1) return -1;
        if (power > 1) return 1;
        return power;
    }

    public static int getDirectionSign(Direction direction) {
        return direction == Direction.FORWARD ? 1 : -1;
    }
}

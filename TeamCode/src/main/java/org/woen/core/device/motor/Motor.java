package org.woen.core.device.motor;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.woen.core.device.Device;
import org.woen.core.device.trait.Directional;
import org.woen.core.device.trait.Encoder;
import org.woen.core.device.trait.VelocityControl;
import org.woen.core.util.NotImplementedException;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device implements VelocityControl, Directional {
    protected DcMotorEx device = null;
    protected ControlMode velocityControlMode;
    protected Encoder linkedEncoder = null;


    public Motor(String name) {
        super(name);
        velocityControlMode = ControlMode.TIMER;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);

        setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    @Override
    public void linkEncoder(Encoder encoder) {
        linkedEncoder = encoder;
    }

    @Override
    public Encoder getLinkedEncoder() {
        return linkedEncoder;
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

    @Override
    public void setVelocityControlMode(ControlMode mode) throws NotImplementedException {
        if (!isVelocityControlModeSupported(mode)) {
            throw new NotImplementedException();
        }

        velocityControlMode = mode;
    }

    @Override
    public ControlMode getVelocityControlMode() {
        return velocityControlMode;
    }

    @Override
    public double getVelocity() {
        return getPower();
    }

    @Override
    public void setVelocity(double newVelocity) throws NotImplementedException {
        final double previousVelocity = getVelocity();

        if (newVelocity == previousVelocity) return;

        if (velocityControlMode == ControlMode.RAW) {
            setPower(newVelocity);
            return;
        }

        throw new NotImplementedException();
    }

    @Override
    public boolean isVelocityControlModeSupported(ControlMode mode) {
        switch (mode) {
            case RAW:
            case TIMER:
            case AMPERAGE:
            case THIRD_PARTY_ENCODER:
                return true;
        }

        return false;
    }
}

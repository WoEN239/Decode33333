package org.woen.core.device.motor;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.woen.core.device.Device;
import org.woen.core.device.trait.Directional;
import org.woen.core.device.trait.Encoder;
import org.woen.core.device.trait.TickSleeper;
import org.woen.core.device.trait.VelocityController;
import org.woen.core.util.PIDController;
import org.woen.core.util.UnimplementedException;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device implements VelocityController, Directional {
    protected DcMotorEx device;
    protected ControlMode velocityControlMode;
    protected Encoder linkedEncoder;
    protected PIDController pidController;


    public Motor(String name) {
        super(name);
        device = null;
        linkedEncoder = null;
        velocityControlMode = ControlMode.TIMER;
        pidController = new PIDController(1, 1, 1);
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
    public ControlMode getVelocityControlMode() {
        return velocityControlMode;
    }

    @Override
    public void setVelocityControlMode(ControlMode mode) throws UnsupportedOperationException {
        if (!isVelocityControlModeSupported(mode)) {
            throw new UnsupportedOperationException();
        }

        velocityControlMode = mode;
    }

    @Override
    public void setPIDCoefficients(double kP, double kI, double kD) {
        pidController.setCoefficients(kP, kI, kD);
    }

    @Override
    public double getVelocity() {
        return getPower();
    }

    @Override
    public void setVelocity(double newVelocity) throws UnimplementedException, InterruptedException {
        final double previousVelocity = getVelocity();

        if (newVelocity == previousVelocity) return;

        if (velocityControlMode == ControlMode.RAW) {
            setPower(newVelocity);
            return;
        }

        TickSleeper sleeper;

        if (velocityControlMode == ControlMode.TIMER) {
            sleeper = () -> {
                // 2/10 sec
                Thread.sleep(100);
            };
        } else {
            throw new UnimplementedException();
        }

        pidController.setTarget(newVelocity);

        double currentVelocity = previousVelocity;
        while (currentVelocity != newVelocity) {
            currentVelocity = pidController.calculate(currentVelocity);
            setPower(Motor.normalizePower(currentVelocity));
            sleeper.sleep();
        }
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


    public static double normalizePower(double power) {
        if (power < -1) return -1;
        if (power > 1) return 1;
        return power;
    }
}

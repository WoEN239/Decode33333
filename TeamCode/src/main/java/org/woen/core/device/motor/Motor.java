package org.woen.core.device.motor;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.woen.core.device.Device;
import org.woen.core.device.trait.Directional;
import org.woen.core.util.pid.PIDCoefficients;
import org.woen.core.util.pid.PIDController;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device implements Directional {
    public enum RunMode {
        RAW,
        PID;
    }


    protected DcMotorEx device;
    protected RunMode runMode;
    protected PIDController pidController;
    protected double allowedPowerError;


    public Motor(String name, double kP, double kI, double kD) {
        super(name);
        device = null;
        runMode = RunMode.PID;
        allowedPowerError = 0.01;
        pidController = new PIDController(kP, kI, kD, 2);
        pidController.setTarget(0);
    }

    public Motor(String name) {
        this(name, 0, 0, 0);
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

    /**
     * Do not use this method for
     * velocity (speed) control
     * because you may break the motor,
     * instead use setVelocity()
     *
     * @param power the new motor's power level in the range [-1; 1]
     */
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

    public double getVelocity() {
        return getPower();
    }

    /**
     * Combination of setVelocityTarget() und velocityTick()
     * <p>
     * Try use these two above methods instead of setVelocity()
     * if you can
     */
    public void setVelocity(double velocity) {
        setTargetVelocity(velocity);
        velocityTick();
    }

    public double getTargetVelocity() {
        return pidController.getTarget();
    }

    public void setTargetVelocity(double target) {
        pidController.setTarget(Motor.normalizePower(target));
    }

    public void velocityTick() {
        final double oldVelocity = getVelocity();
        final double targetVelocity = getTargetVelocity();

        if (oldVelocity == targetVelocity) return;

        final double newVelocity;

        if (runMode == RunMode.PID) {
            final double calculatedVelocity =
                    Motor.normalizePower(pidController.calculate(oldVelocity));

            newVelocity =
                    (Math.abs(pidController.getLastError()) > allowedPowerError)
                    ? calculatedVelocity
                    : targetVelocity;
        } else {
            // Raw mode, just set power to targetVelocity
            newVelocity = targetVelocity;
        }

        setPower(oldVelocity + newVelocity);
    }

    public PIDCoefficients getPIDCoefficients() {
        return pidController.getCoefficients();
    }

    public void setPIDCoefficients(PIDCoefficients coefficients) {
        pidController.setCoefficients(coefficients);
    }

    public void setPIDCoefficients(double kP, double kI, double kD) {
        pidController.setCoefficients(kP, kI, kD);
    }


    public static double normalizePower(double power) {
        if (power < -1) return -1;
        if (power > 1) return 1;
        return power;
    }
}

package org.woen.core.device.motor;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.woen.core.device.Device;
import org.woen.core.device.trait.Directional;
import org.woen.core.device.trait.VelocityController;
import org.woen.core.util.PIDController;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device implements VelocityController, Directional {
    protected DcMotorEx device;
    protected ControlMode velocityControlMode;
    protected PIDController pidController;
    protected double allowedPowerError;


    public Motor(String name) {
        super(name);
        device = null;
        velocityControlMode = ControlMode.PID;
        pidController = new PIDController(1, 1, 1, 2);
        allowedPowerError = 0.01;
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

    public double getAllowedPowerError() {
        return allowedPowerError;
    }

    public void setAllowedPowerError(double error) {
        allowedPowerError = Math.abs(error);
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

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        device.setZeroPowerBehavior(behavior);
    }

    public void stopMotor() {
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        setPower(0);
    }

    public void brakeMotor() {
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setPower(0);
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
    public void setVelocityControlMode(ControlMode mode) {
        velocityControlMode = mode;
    }

    @Override
    public double getTargetVelocity() {
        return pidController.getTarget();
    }

    @Override
    public void setTargetVelocity(double target) {
        pidController.setTarget(Motor.normalizePower(target));
    }

    @Override
    public void velocityTick() {
        final double currentVelocity = getVelocity();
        final double targetVelocity = getTargetVelocity();

        if (currentVelocity == targetVelocity) return;

        final double newVelocity;

        if (velocityControlMode == ControlMode.PID) {
            final double calculatedVelocity =
                    Motor.normalizePower(pidController.calculate(currentVelocity));

            newVelocity =
                    (Math.abs(pidController.getLastError()) > allowedPowerError)
                    ? calculatedVelocity
                    : targetVelocity;
        } else {
            // Raw mode, just set power to targetVelocity
            newVelocity = targetVelocity;
        }

        setPower(newVelocity);
    }

    @Override
    public void setPIDCoefficients(double kP, double kI, double kD) {
        pidController.setCoefficients(kP, kI, kD);
    }

    @Override
    public double getVelocity() {
        return getPower();
    }


    public static double normalizePower(double power) {
        if (power < -1) return -1;
        if (power > 1) return 1;
        return power;
    }
}

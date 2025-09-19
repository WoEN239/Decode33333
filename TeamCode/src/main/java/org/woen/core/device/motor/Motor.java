package org.woen.core.device.motor;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.woen.core.device.Device;
import org.woen.core.device.trait.Directional;
import org.woen.core.device.trait.Encoder;
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
    protected PIDController pidController;
    protected double powerMistake;


    public Motor(String name) {
        super(name);
        device = null;
        velocityControlMode = ControlMode.PID;
        pidController = new PIDController(1, 1, 1);
        powerMistake = 0.01;
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

    public double getPowerMistake() {
        return powerMistake;
    }

    public void setPowerMistake(double powerMistake) {
        this.powerMistake = powerMistake;
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
    public void setVelocityControlMode(ControlMode mode) {
        velocityControlMode = mode;
    }

    @Override
    public double getVelocityTarget() {
        return pidController.getTarget();
    }

    @Override
    public void setVelocityTarget(double target) {
        pidController.setTarget(Motor.normalizePower(target));
    }

    @Override
    public void velocityTick() {
        final double currentVelocity = getVelocity();
        final double targetVelocity = getVelocityTarget();

        if (currentVelocity == targetVelocity) return;

        final double newVelocity;

        if (velocityControlMode == ControlMode.RAW) {
            newVelocity = targetVelocity;
        } else {
            final double calculatedVelocity =
                    Motor.normalizePower(pidController.calculate(currentVelocity));
            final double moduleOfDifferent =
                    PIDController.getModuleOfDifferent(calculatedVelocity, targetVelocity);

            newVelocity =
                    (moduleOfDifferent > powerMistake)
                    ? calculatedVelocity
                    : targetVelocity;
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

    @Override
    public void setVelocity(double newVelocity) {
        setVelocityTarget(newVelocity);
        velocityTick();
    }



    public static double normalizePower(double power) {
        if (power < -1) return -1;
        if (power > 1) return 1;
        return power;
    }
}

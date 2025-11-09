package org.woen.core.device.motor;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.woen.core.device.Device;
import org.woen.core.device.trait.Directional;
import org.woen.core.device.trait.VelocityController;
import org.woen.core.util.PIDController;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor extends Device implements VelocityController, Directional {
    protected DcMotorEx device;
    protected ControlMode velocityControlMode;
    protected PIDController pidController;
    protected double allowedPowerError;
    protected double oldPower;


    public Motor(String name) {
        super(name);
        device = null;
        oldPower = 0;
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

    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() {
        return device.getZeroPowerBehavior();
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        device.setZeroPowerBehavior(behavior);
    }

    public void stopMotor() {
        final DcMotor.ZeroPowerBehavior previousBehavior = getZeroPowerBehavior();
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        setPower(0);
        setZeroPowerBehavior(previousBehavior);
    }

    public void brakeMotor() {
        final DcMotor.ZeroPowerBehavior previousBehavior = getZeroPowerBehavior();
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setPower(0);
        setZeroPowerBehavior(previousBehavior);
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
        pidController.setTarget(target);
    }

    @Override
    public void velocityTick() {
        final double currentVelocity = device.getVelocity();

        if (currentVelocity == getTargetVelocity()) return;

        final double newVelocity;

        if (velocityControlMode == ControlMode.PID) {
            FtcDashboard.getInstance().getTelemetry().addData("PID", oldPower);
            newVelocity = Motor.normalizePower(pidController.calculate(currentVelocity) + oldPower);
//            newVelocity =
//                    (Math.abs(pidController.getLastError()) > allowedPowerError)
//                    ? calculatedVelocity
//                    : targetVelocity;
        } else {
            // Raw mode, just set power to targetVelocity
            newVelocity = getTargetVelocity();
        }

        oldPower = newVelocity;
        FtcDashboard.getInstance().getTelemetry().addData("oldPower", 0);
        FtcDashboard.getInstance().getTelemetry().update();
        setPower(newVelocity);
    }

    @Override
    public void setPIDCoefficients(double kP, double kI, double kD) {
        pidController.setCoefficients(kP, kI, kD);
    }

    public double getEncoder() {
        return device.getCurrentPosition();
    }

    public double getEncoderVel() {
        return device.getVelocity();
    }
    public static double normalizePower(double power) {
        if (power < -1) return -1;
        if (power > 1) return 1;
        return power;
    }
}

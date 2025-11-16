package org.firstinspires.ftc.teamcode.core.implementation;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.trait.device.IMotor;


public class Motor implements IMotor {
    private final String name;
    private DcMotorEx device;
    private Direction direction;


    public Motor(String name) {
        assert name != null;

        this.name = name;
        device = null;
        direction = Direction.FORWARD;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        assert hardwareMap != null;

        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    @Override
    public boolean isEnabled() {
        assert isInitialized();
        return device.isMotorEnabled();
    }

    @Override
    public void enable() {
        assert isInitialized();
        device.setMotorEnable();
    }

    @Override
    public void disable() {
        assert isInitialized();
        device.setMotorDisable();
    }

    @Override
    public double getPower() {
        assert isInitialized();
        return device.getPower() * direction.getSign();
    }

    @Override
    public void setPower(double power) {
        assert isInitialized();
        device.setPower(IMotor.normalizePower(power) * direction.getSign());
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        assert direction != null;
        this.direction = direction;
    }

    @Override
    public DcMotor.ZeroPowerBehavior getZeroPowerBehaviour() {
        assert isInitialized();
        return device.getZeroPowerBehavior();
    }

    @Override
    public void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior behaviour) {
        assert isInitialized();
        assert behaviour != null;

        device.setZeroPowerBehavior(behaviour);
    }

    @Override
    public String getName() {
        return name;
    }
}

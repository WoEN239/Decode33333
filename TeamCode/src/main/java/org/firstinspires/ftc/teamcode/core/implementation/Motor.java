/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.implementation;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.trait.device.IMotor;


/**
 * Just motor device.
 *
 * @see IMotor
 *
 * @see EncoderMotor
 */
public class Motor implements IMotor {
    protected final String name;
    protected DcMotorEx device;
    protected Direction direction;


    public Motor(String name) {
        assert name != null;

        this.name = name;
        device = null;
        direction = Direction.FORWARD;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        assert hardwareMap != null;

        if (isInitialized()) return;

        device = hardwareMap.get(DcMotorEx.class, name);

        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        device.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public boolean isInitialized() {
        return device != null;
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
    public boolean isEnabled() {
        assert isInitialized();
        return device.isMotorEnabled();
    }

    @Override
    public double getPower() {
        assert isInitialized();
        return device.getPower() * direction.getSign();
    }

    @Override
    public void setPower(double power) {
        assert isInitialized();
        device.setPower(normalizePower(power) * direction.getSign());
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


    /**
     * Normalize the power value if it is outside the possible range.
     */
    public static double normalizePower(double power) {
        if (power > 1) return 1;
        if (power < -1) return -1;
        return power;
    }
}

package org.firstinspires.ftc.teamcode.core.device.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.trait.Encoder;


public class EncoderMotor extends Motor implements Encoder {
    private double power = 0;
    private double targetSpeed = 0;

    public EncoderMotor(String name) {
        super(name);
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        super.initialize(hardwareMap);
        device.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void resetEncoder() {
        device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        device.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public int getEncoderPosition() {
        return device.getCurrentPosition();
    }

    @Override
    public double getEncoderSpeed() {
        return device.getVelocity();
    }

    public void setSpeed(double speed) {
        targetSpeed = speed;
        pidController.setTarget(speed);
    }

    public void speedTick() {
        double dPower = pidController.calculate(getEncoderSpeed());
        power = normalizePower(power + dPower);
        setPower(power);
    }
}

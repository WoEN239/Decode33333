package org.firstinspires.ftc.teamcode.core.device.motor;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.trait.Encoder;
import org.firstinspires.ftc.teamcode.core.util.Smoother;
import org.firstinspires.ftc.teamcode.core.util.pid.PIDRegulator;


public class EncoderMotor extends Motor implements Encoder {
    private double power = 0;
    private Smoother smoother = new Smoother(1, 0);

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
        if(!inverted) {
            return -device.getCurrentPosition();
        } else {
            return device.getCurrentPosition();
        }
    }

    @Override
    public double getEncoderSpeed() {
        if(!inverted) {
            return -device.getVelocity();
        } else {
            return device.getVelocity();
        }
    }

    public void setAlpha(double alpha) {
        smoother.alpha = alpha;
    }

    public void setSpeed(double speed) {
        pidController.setpoint = speed;
    }

    public void speedTick() {
        double spd = getEncoderSpeed();
        double smoothSpd = smoother.smooth(spd);
        double dPower = pidController.PIDGet(smoothSpd);
        power = normalizePower(power + dPower);
        FtcDashboard.getInstance().getTelemetry().addData("Power", power);
        FtcDashboard.getInstance().getTelemetry().addData("Speed", spd);
        FtcDashboard.getInstance().getTelemetry().addData("Speed smooth", smoothSpd);
        FtcDashboard.getInstance().getTelemetry().update();
        setPower(power);
    }
}

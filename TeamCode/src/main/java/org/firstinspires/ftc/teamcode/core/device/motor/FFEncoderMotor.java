package org.firstinspires.ftc.teamcode.core.device.motor;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage;
import org.firstinspires.ftc.teamcode.core.device.trait.Encoder;
import org.firstinspires.ftc.teamcode.core.util.Smoother;
import org.firstinspires.ftc.teamcode.core.util.pid.PIDRegulator;


public class FFEncoderMotor extends Motor implements Encoder {
    private double spdMul = 0;
    private Smoother smoother = new Smoother(1, 0);

    public FFEncoderMotor(String name) {
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
        if(inverted) {
            return -device.getCurrentPosition();
        } else {
            return device.getCurrentPosition();
        }
    }

    @Override
    public double getEncoderSpeed() {
        if(inverted) {
            return -device.getVelocity();
        } else {
            return device.getVelocity();
        }
    }

    public void setSpeed(double speed) {
        pidController.setpoint = speed;
    }

    public void setAlpha(double alpha) {
        smoother.alpha = alpha;
    }

    public void setSpeedMul(double spdMul) {
        this.spdMul = spdMul;
    }

    public void speedTick() {
        double spd = getEncoderSpeed();
        double smoSpd = smoother.smooth(spd);
        double pid_out = pidController.PIDGet(smoSpd);
        double mul_out = spdMul * pidController.setpoint;
        FtcDashboard.getInstance().getTelemetry().addData("PID out", pid_out);
        FtcDashboard.getInstance().getTelemetry().addData("Mul out", mul_out);
        double power = normalizePower(pid_out + mul_out);
        FtcDashboard.getInstance().getTelemetry().addData("Power", power);
        FtcDashboard.getInstance().getTelemetry().addData("Speed err", pidController.setpoint - spd);
        FtcDashboard.getInstance().getTelemetry().addData("Speed err Smooth", pidController.setpoint - smoSpd);
        FtcDashboard.getInstance().getTelemetry().update();
        setPower(SensorVoltage.getInstance().calculateCoefficientVoltage(power));
    }
}

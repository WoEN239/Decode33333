package org.firstinspires.ftc.teamcode.core.device.motor;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage;
import org.firstinspires.ftc.teamcode.core.device.trait.Encoder;
import org.firstinspires.ftc.teamcode.core.util.Smoother;
import org.firstinspires.ftc.teamcode.core.util.pid.PIDController;


public class EncoderMotor extends Motor implements Encoder {
    protected final PIDController pidController;
    protected final Smoother smoother;
    protected final SensorVoltage sensorVoltage;
    protected double velocityMultiplier;


    public EncoderMotor(String name, double kP, double kI, double kD) {
        super(name);
        pidController = new PIDController(kP, kI, kD, 2);
        smoother = new Smoother(1, 0);
        sensorVoltage = new SensorVoltage(null);
        velocityMultiplier = 0;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        super.initialize(hardwareMap);
        sensorVoltage.initialize(hardwareMap);
    }

    @Override
    public void resetEncoder() {
        device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        device.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public int getEncoderPosition() {
        return device.getCurrentPosition() * getDirectionSign();
    }

    @Override
    public double getEncoderVelocity() {
        return device.getVelocity() * getDirectionSign();
    }

    public void setAlpha(double alpha) {
        smoother.alpha = alpha;
    }

    public double getVelocity() {
        return getEncoderVelocity();
    }

    public void setVelocity(double velocity) {
        setTargetVelocity(velocity);
        velocityTick();
    }

    public void setTargetVelocity(double velocity) {
        pidController.setTarget(velocity);
    }

    public void velocityTick() {
        final double oldVelocity = getVelocity();
        final double smoothedOldVelocity = smoother.smooth(oldVelocity);
        final double pidOutput = pidController.calculate(smoothedOldVelocity);
        final double multiplyOutput = velocityMultiplier * pidController.getTarget();
        final double power = normalizePower(pidOutput + multiplyOutput);

        setPower(sensorVoltage.calculateCoefficientVoltage(power));

        FtcDashboard.getInstance().getTelemetry().addData("Power", power);
        FtcDashboard.getInstance().getTelemetry().addData("PID output", pidOutput);
        FtcDashboard.getInstance().getTelemetry().addData("Multiply output", multiplyOutput);
        FtcDashboard.getInstance().getTelemetry().addData("Velocity error", pidController.getTarget() - oldVelocity);
        FtcDashboard.getInstance().getTelemetry().addData("Velocity error smooth", pidController.getTarget() - smoothedOldVelocity);
        FtcDashboard.getInstance().getTelemetry().update();
    }
}

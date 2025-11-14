package org.firstinspires.ftc.teamcode.main.modules;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.motor.FFEncoderMotor;
import org.firstinspires.ftc.teamcode.core.device.servomotor.Servomotor;
import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;
import org.firstinspires.ftc.teamcode.core.device.motor.EncoderMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage;

// coding by Matvey Ivanovv

@Config
public final class GunControl implements Initializable {
    private static final org.firstinspires.ftc.teamcode.main.modules.GunControl INSTANCE = new org.firstinspires.ftc.teamcode.main.modules.GunControl();
//    private final Motor motorRight;
    public ElapsedTime runtime = new ElapsedTime();
    private final EncoderMotor motorLeft;
    private final Servomotor servo;
    private SensorVoltage sensorVoltage;
    public static double velocity = 0;
    public static double degreeTower = 0.35;

    public static double p = 0.009;
    public static double i = 0.0;
    public static double d = 0.0;
//    public static double p = 0.000001;
//    public static double i = 0.0;
//    public static double d = 0.000005;
    public static double alpha = 1;
    public static double spdMul = 0.00025;

    public GunControl() {
        motorLeft = new EncoderMotor("gun_motor_left", 0, 0, 0);
//        motorRight = new Motor("gun_motor_right");
        servo = new Servomotor("servo_turn_tower");
    }

    public static org.firstinspires.ftc.teamcode.main.modules.GunControl getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        motorLeft.initialize(hardwareMap);
//        motorRight.initialize(hardwareMap);
        SensorVoltage.getInstance().initialize(hardwareMap);
        servo.initialize(hardwareMap);
        sensorVoltage.initialize(hardwareMap);
    }

    @Override
    public boolean isInitialized() {
        return motorLeft.isInitialized() && servo.isInitialized() && SensorVoltage.getInstance().isInitialized();
    }

    public double getVelocity() {
        return velocity;
    }

    public void startShot() {
        FtcDashboard.getInstance().getTelemetry().update();
        motorLeft.setPIDCoefficients(p, i, d);
        motorLeft.setSmootherAlpha(alpha);
        motorLeft.setVelocityMultiplier(spdMul);
        motorLeft.setVelocity(velocity);  // sensorVoltage.calculateCoefficientVoltage(velocity)
        FtcDashboard.getInstance().getTelemetry().addData("Speed gun", GunControl.getInstance().getSpeedGun());
        FtcDashboard.getInstance().getTelemetry().update();
//        motorRight.setPower(sensorVoltage.calculateCoefficientVoltage(velocity));
    }

    public void stopShot() {
//        motorRight.stopMotor();
        motorLeft.setPower(0);
    }

    public void setTowerDegree(double degree) {
        degreeTower = degree;
        servo.setServoPosition(degree);
    }

    public double getSpeedGun() {
        return motorLeft.getEncoderVelocity();
    }

    public double getTowerDegree() {
        return degreeTower;
    }
}
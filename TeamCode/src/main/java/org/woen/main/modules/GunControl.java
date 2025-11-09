package org.woen.main.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.servomotor.Servomotor;
import org.woen.core.device.trait.Initializable;
import org.woen.core.device.motor.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.woen.core.device.sensor.SensorVoltage;

@Config
public final class GunControl implements Initializable {
    private static final org.woen.main.modules.GunControl INSTANCE = new org.woen.main.modules.GunControl();
//    private final Motor motorRight;
    public ElapsedTime runtime = new ElapsedTime();
    private final Motor motorLeft;
    private final Servomotor servo;
    private SensorVoltage sensorVoltage;
    public static double velocity = -0.55;
    public static double degreeTower = 0.0;

    public GunControl() {
        motorLeft = new Motor("gun_motor_left");
//        motorRight = new Motor("gun_motor_right");
        servo = new Servomotor("servo_turn_tower");
    }

    public static org.woen.main.modules.GunControl getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        motorLeft.initialize(hardwareMap);
//        motorRight.initialize(hardwareMap);
        servo.initialize(hardwareMap);
        sensorVoltage = new SensorVoltage(hardwareMap);
    }

    @Override
    public boolean isInitialized() {
        return motorLeft.isInitialized() && servo.isInitialized() && sensorVoltage != null;
    }

    public double getVelocity() {
        return velocity;
    }

    public void startShot() {
        motorLeft.setPower(sensorVoltage.calculateCoefficientVoltage(velocity));
//        motorRight.setPower(sensorVoltage.calculateCoefficientVoltage(velocity));
    }

    public void stopShot() {
//        motorRight.stopMotor();
        motorLeft.stopMotor();
    }

    public void setTowerDegree(double degree) {
        degreeTower = degree;
        servo.setServoPosition(degree * 3);
    }

    public double getTowerDegree() {
        return degreeTower;
    }
}
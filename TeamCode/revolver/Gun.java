package org.woen.main.revolver;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.device.trait.Initializable;
import com.qualcomm.robotcore.hardware.DcMotor;


public final class Gun implements Initializable {
    private static final Gun INSTANCE = new Gun();
    private double velocity = 0;
    private final Motor motorLeft;
    private final Motor motorRight;


    public Gun() {
        motorLeft = new Motor("gun_motor_left");
        motorRight = new Motor("gun_motor_right");
    }

    public static Gun getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        motorLeft.initialize(hardwareMap);
        motorRight.initialize(hardwareMap);
    }

    @Override
    public boolean isInitialized() {

        return motorLeft.isInitialized() && motorRight.isInitialized();
    }

    public void invertDirection(Motor motor) {
        motor.setDirection(motor.getDirection().inverted());
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
        motorLeft.setVelocity(velocity);
        motorRight.setVelocity(velocity);
    }

    public double getVelocity() { return velocity; }

    public void startShot() {
        motorRight.setPower(motorLeft.getVelocity());
        motorLeft.setPower(motorLeft.getVelocity());
    }

    public void stopShoot() {
        motorRight.stopMotor();
        motorLeft.stopMotor();
    }
}

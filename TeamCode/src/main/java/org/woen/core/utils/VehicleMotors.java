
package org.woen.core;


import org.woen.core.device.motor.Motor;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


/*
 * This is a template class
 * because we don't know
 * how motors will be placed yet
 */
public final class VehicleMotors {
    private static final VehicleMotors INSTANCE = new VehicleMotors();


    private Motor leftFrontMotor;
    private Motor leftBackMotor;
    private Motor rightFrontMotor;
    private Motor rightBackMotor;


    private VehicleMotors() {
        leftFrontMotor = new Motor("left_front_motor");
        leftBackMotor = new Motor("left_back_motor");
        rightFrontMotor = new Motor("right_front_motor");
        rightBackMotor = new Motor("right_back_motor");
    }

    public static VehicleMotors getInstance() {
        return INSTANCE;
    }

    public void initialize(HardwareMap hardwareMap) {
        leftFrontMotor.initialize(hardwareMap);
        leftBackMotor.initialize(hardwareMap);
        rightFrontMotor.initialize(hardwareMap);
        rightBackMotor.initialize(hardwareMap);

        /*
         * Motor by default rotates clockwise
         * so right motors relative to left 
         * motors will rotate backward. 
         * We should change their direction.
         */
        rightFrontMotor.reverseDirection();
        rightBackMotor.reverseDirection();
    }

    public boolean isInitialized() {
        return leftFrontMotor.isInitialized();
    }

    public void setPower(double power) {
        leftFrontMotor.setPower(power);
        leftBackMotor.setPower(power);
        rightFrontMotor.setPower(power);
        rightBackMotor.setPower(power);
    }

    public double getPower() {
        return leftFrontMotor.getPower();
    }

    public void moveToDirection(double forward, double horizontal, double turn) {
        double frontLeftPower = forward + horizontal + turn;
        double frontRightPower = forward - horizontal - turn;
        double backLeftPower = forward - horizontal + turn;
        double backRightPower = forward + horizontal - turn;

        leftFrontMotor.setPower(frontLeftPower);
        leftBackMotor.setPower(backLeftPower);
        rightFrontMotor.setPower(frontRightPower);
        rightBackMotor.setPower(backRightPower);
    }
}


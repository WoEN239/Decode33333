package org.woen.main.movement;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.utils.Initializable;


public final class Vehicles implements Initializable {
    private static final Vehicles INSTANCE = new Vehicles();


    private final Motor leftFrontMotor;
    private final Motor rightFrontMotor;
    private final Motor leftBackMotor;
    private final Motor rightBackMotor;


    private Vehicles() {
        leftFrontMotor = new Motor("left_front_vehicle_motor");
        rightFrontMotor = new Motor("right_front_vehicle_motor");
        leftBackMotor = new Motor("left_back_vehicle_motor");
        rightBackMotor = new Motor("right_back_vehicle_motor");

        rightFrontMotor.reverseDirection();
        rightBackMotor.reverseDirection();
    }

    @NonNull
    public static Vehicles getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(@NonNull HardwareMap hardwareMap) {
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

    @Override
    public boolean isInitialized() {
        return leftFrontMotor.isInitialized()
                && rightFrontMotor.isInitialized()
                && leftBackMotor.isInitialized()
                && rightBackMotor.isInitialized();
    }

    public void moveLinearly(double power) {
        leftFrontMotor.setPower(power);
        leftBackMotor.setPower(power);
        rightFrontMotor.setPower(power);
        rightBackMotor.setPower(power);
    }

    public void stopAll() {
        moveLinearly(0);
    }
}

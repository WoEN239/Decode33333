package org.woen.core.utils;


import org.woen.core.device.motor.Motor;

import com.qualcomm.robotcore.hardware.HardwareMap;


/*
 * This is a template class
 * because we don't know
 * how motors will be placed yet
 */
public final class GunMotors {
    private static final GunMotors INSTANCE = new GunMotors();


    private final Motor leftMotor;
    private final Motor rightMotor;


    private GunMotors() {
        leftMotor = new Motor("left_gun_motor");
        rightMotor = new Motor("right_gun_motor");
    }

    public static GunMotors getInstance() {
        return INSTANCE;
    }

    public void initialize(HardwareMap hardwareMap) {
        leftMotor.initialize(hardwareMap);
        rightMotor.initialize(hardwareMap);

        /*
         * See VehicleMotors.java for explanation.
         *
         * It is assumed that motor body
         * is placed below rotor's rod:
         *
         *   |          <- rod
         *   |           - ^^^
         * [^^^]        <- body
         * [   ]         - ^^^^
         * [   ]         -
         * [___]         -
         */
        rightMotor.reverseDirection();
    }

    public boolean isInitialized() {
        return leftMotor.isInitialized();
    }

    public void setPower(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public double getPower() {
        return leftMotor.getPower();
    }
}


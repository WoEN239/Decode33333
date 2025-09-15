package org.woen.main.movement;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.utils.InitializableWith;


public final class Vehicles implements InitializableWith<HardwareMap> {
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
    }

    public static Vehicles getInstance() {
        return INSTANCE;
    }

    @Override
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
        rightFrontMotor.invertDirection();
        rightBackMotor.invertDirection();
    }

    @Override
    public boolean isInitialized() {
        return leftFrontMotor.isInitialized()
                && rightFrontMotor.isInitialized()
                && leftBackMotor.isInitialized()
                && rightBackMotor.isInitialized();
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

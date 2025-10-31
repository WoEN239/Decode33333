package org.woen.main.movement;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.motor.Motor;
import org.woen.core.device.trait.Initializable;


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
        leftBackMotor.invertDirection();
        leftFrontMotor.invertDirection();
    }

    @Override
    public boolean isInitialized() {
        return leftFrontMotor.isInitialized()
                && rightFrontMotor.isInitialized()
                && leftBackMotor.isInitialized()
                && rightBackMotor.isInitialized();
    }

    public void moveToDirection(double forward, double horizontal, double turn) {
        //! TODO: code normal implementation
        double deadZone = 0.1;
        if (Math.abs(forward) < deadZone) forward = 0;
        if (Math.abs(horizontal) < deadZone) horizontal = 0;
        if (Math.abs(turn) < deadZone) turn = 0;

        double frontLeftPower = Motor.normalizePower(forward + horizontal + turn);
        double frontRightPower = Motor.normalizePower(forward - horizontal - turn);
        double backLeftPower = Motor.normalizePower(forward - horizontal + turn);
        double backRightPower = Motor.normalizePower(forward + horizontal - turn);

        leftFrontMotor.setPower(frontLeftPower);
        leftBackMotor.setPower(backLeftPower);
        rightFrontMotor.setPower(frontRightPower);
        rightBackMotor.setPower(backRightPower);
    }
}

package org.firstinspires.ftc.teamcode.main.movement;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.motor.Motor;
import org.firstinspires.ftc.teamcode.core.device.odometer.Odometer;
import org.firstinspires.ftc.teamcode.core.device.single.Gyro;
import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;
import org.firstinspires.ftc.teamcode.core.util.pid.PIDRegulator;


public final class Vehicles implements Initializable {
    private static final Vehicles INSTANCE = new Vehicles();

    private final Odometer odometerX;
    private final Odometer odometerY;
    private final Motor leftFrontMotor;
    private final Motor rightFrontMotor;
    private final Motor leftBackMotor;
    private final Motor rightBackMotor;

    private final PIDRegulator xPosPID = new PIDRegulator();
    private final PIDRegulator yPosPID = new PIDRegulator();
    private final PIDRegulator yawPosPID = new PIDRegulator();


    private Vehicles() {
        leftFrontMotor = new Motor("left_front_vehicle_motor");
        rightFrontMotor = new Motor("right_front_vehicle_motor");
        leftBackMotor = new Motor("left_back_vehicle_motor");
        rightBackMotor = new Motor("right_back_vehicle_motor");
        odometerX = new Odometer("OdometerX");
        odometerY = new Odometer("right_back_vehicle_motor");
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
        odometerX.initialize(hardwareMap);
        odometerY.initialize(hardwareMap);

        /*
         * Motor by default rotates clockwise
         * so right motors relative to left
         * motors will rotate backward.
         * We should change their direction.
         */
        leftBackMotor.invertDirection();
        leftFrontMotor.invertDirection();
        odometerX.resetEncoder();
        odometerY.resetEncoder();
    }

    @Override
    public boolean isInitialized() {
        return leftFrontMotor.isInitialized()
                && rightFrontMotor.isInitialized()
                && leftBackMotor.isInitialized()
                && rightBackMotor.isInitialized()
                && odometerX.isInitialized()
                && odometerY.isInitialized();
    }

    public void moveToDirection(double forward, double horizontal, double turn) {
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

    public void moveToDirectionNorm(double forward, double horizontal, double turn) {
        double deadZone = 0.1;
        if (Math.abs(forward) < deadZone) forward = 0;
        if (Math.abs(horizontal) < deadZone) horizontal = 0;
        if (Math.abs(turn) < deadZone) turn = 0;

        double frontLeftPower = forward + horizontal + turn;
        double frontRightPower = forward - horizontal - turn;
        double backLeftPower = forward - horizontal + turn;
        double backRightPower = forward + horizontal - turn;

        double maxSpd = Math.max(Math.max(
                Math.abs(frontLeftPower), Math.abs(frontRightPower)
        ), Math.max(
                Math.abs(backLeftPower), Math.abs(backRightPower)
        ));

        if(maxSpd > 1) {
            FtcDashboard.getInstance().getTelemetry().addData("Max motor speed", 1);
            frontLeftPower /= maxSpd;
            frontRightPower /= maxSpd;
            backLeftPower /= maxSpd;
            backRightPower /= maxSpd;
        } else {
            FtcDashboard.getInstance().getTelemetry().addData("Max motor speed", maxSpd);
        }

        leftFrontMotor.setPower(frontLeftPower);
        leftBackMotor.setPower(backLeftPower);
        rightFrontMotor.setPower(frontRightPower);
        rightBackMotor.setPower(backRightPower);
    }

    // coding by Timofei
    private void goTo(double x, double y, double yaw, boolean posReg, boolean yawReg) {
        double xSpd = 0, ySpd = 0, yawSpd = 0;

        if(posReg) {
            double xErr = x - Odometry.getInstance().getX();
            double yErr = y - Odometry.getInstance().getY();
            double[] errVector = Odometry.rotateVector(xErr, yErr, -Odometry.getInstance().getYaw());
            xErr = errVector[0];
            yErr = errVector[1];
            xSpd = xPosPID.PIDGet(-xErr);
            ySpd = yPosPID.PIDGet(-yErr);
        }

        if(yawReg) {
            double yawErr = Gyro.getShortestPathToAngle(Odometry.getInstance().getYaw(), yaw);
            yawSpd = yawPosPID.PIDGet(-yawErr);
        }

        moveToDirectionNorm(xSpd, ySpd, yawSpd);
    }

    public double getPositionOdometerX() {
        return odometerX.getEncoderPosition();
    }

    public double getPositionOdometerY() {
        return odometerY.getEncoderPosition();
    }
}

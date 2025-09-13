package org.woen.core;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    public DcMotorEx leftFrontMotor, rightFrontMotor;
    public DcMotorEx leftBackMotor, rightBackMotor;
    public DcMotorEx sortMotor, leftGunMotor, rightGunMotor;


    public Motors(HardwareMap hardwareMap) {
        leftFrontMotor = hardwareMap.get(DcMotorEx.class, "left_front_motor");
        rightFrontMotor = hardwareMap.get(DcMotorEx.class, "right_front_motor");
        leftBackMotor = hardwareMap.get(DcMotorEx.class, "left_back_motor");
        rightBackMotor = hardwareMap.get(DcMotorEx.class, "right_back_motor");

        sortMotor = hardwareMap.get(DcMotorEx.class, "sort_motor");

        leftGunMotor = hardwareMap.get(DcMotorEx.class, "left_gun_motor");
        rightGunMotor = hardwareMap.get(DcMotorEx.class, "right_gun_motor");

        rightFrontMotor.setDirection(DcMotorEx.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorEx.Direction.REVERSE);
        rightGunMotor.setDirection(DcMotorEx.Direction.REVERSE);
    }
    public void setPowerBase(double power) {
        leftFrontMotor.setPower(power / 100);
        rightFrontMotor.setPower(power / 100);
        leftBackMotor.setPower(power / 100);
        rightBackMotor.setPower(power / 100);
    }
    public void setPowerSort(double power) {
        sortMotor.setPower(power / 100);
    }
    public void setPowerGun(double power) {
        rightGunMotor.setPower(power / 100);
        leftGunMotor.setPower(power / 100);
    }
    public double getPowerBase() {
        return leftFrontMotor.getPower() * 100;
    }
    public double getPowerSort() {
        return sortMotor.getPower() * 100;
    }
    public double getPowerGun() {
        return leftGunMotor.getPower() * 100;
    }
    public void movementToDirection(double forward, double horizontal, double turn) {
        double frontLeftPower = forward + horizontal + turn;
        double frontRightPower = forward - horizontal - turn;
        double backLeftPower = forward - horizontal + turn;
        double backRightPower = forward + horizontal - turn;

        leftFrontMotor.setPower(frontLeftPower);
        rightFrontMotor.setPower(frontRightPower);
        leftBackMotor.setPower(backLeftPower);
        rightBackMotor.setPower(backRightPower);
    }
}
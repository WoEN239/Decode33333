package org.woen.Modules.Odometery;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.woen.MatchData.MatchData;
import org.woen.Math.ExponentialFilter;
import org.woen.Modules.Interface.RobotModule;
import org.woen.Robot.Robot;



public class Odometry implements RobotModule {


    DcMotorEx leftOdometer;
    DcMotorEx rightOdometer;
    DcMotorEx sideOdometer;

    IMU gyro;


    Robot robot;

    public static double TICK_TO_ENC = 384.5;

    public static double ODOMETER_WHEEL_LENGHT = 4.8;

    public static double LATERAL_DISTANCE = 14; //TODO in new robot normal value

    public static double FORWARD_OFFSET = 14;

    private double crr  = (ODOMETER_WHEEL_LENGHT * Math.PI) / TICK_TO_ENC;
    private static final double CM_TO_ROTATION_DEGREES_RATIO = 180.0 / ((FORWARD_OFFSET / 2.0) * Math.PI);

    private SparkFunOTOS.Pose2D pos = MatchData.startPos;

    double rightOld = 0 ;
    double lefrOld = 0;
    double sideOld = 0;

    public static double K_FOR_FILTER = 0.2;


    ExponentialFilter exponentialFilter = new ExponentialFilter();

    CalcRealVel calcRealVel = new CalcRealVel();




    public Odometry(Robot robot) {
        this.robot = robot;

    }


    @Override
    public void init() {
        leftOdometer = robot.devicePool.leftOdometer;

        rightOdometer = robot.devicePool.rightOdometer;

        sideOdometer = robot.devicePool.sideOdometer;

        gyro = robot.devicePool.gyro;

        reset();
    }

    public void reset(){
        gyro.resetYaw();

        leftOdometer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOdometer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sideOdometer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftOdometer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightOdometer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sideOdometer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void update(){
        double rightOdEnc = rightOdometer.getCurrentPosition();
        double leftOdEnc = leftOdometer.getCurrentPosition();
        double sideOdEnc = sideOdometer.getCurrentPosition();

        double angle = gyro.getRobotYawPitchRollAngles().getYaw();

        double deltaR = rightOdEnc - rightOld;
        double deltaL = leftOdEnc - lefrOld;
        double deltaS = sideOdEnc - sideOld;

        double phi = (deltaL - deltaR) / LATERAL_DISTANCE;
        double deltaLROdo = (deltaL + deltaR) / 2.0;
        double deltaPerpPos = deltaS - FORWARD_OFFSET * phi;


        double deltaX = deltaLROdo * Math.cos(angle) - deltaPerpPos * Math.sin(angle);
        double deltaY = deltaLROdo * Math.sin(angle) + deltaPerpPos * Math.cos(angle);


        exponentialFilter.update(K_FOR_FILTER, angle,phi);

        pos.x += deltaX;
        pos.y += deltaY;
        pos.h += exponentialFilter.getY();

        pos.x /= crr;
        pos.y /= crr;
        pos.h /= CM_TO_ROTATION_DEGREES_RATIO;

        lefrOld = leftOdEnc;
        rightOld = rightOdEnc;
        sideOld = sideOdEnc;
    }

    public SparkFunOTOS.Pose2D getPos(){
        return pos;
    }

    public double getRealVelLeft(){
        return calcRealVel.getCorrectedVel(leftOdometer);
    }
    public double getRealVelRight(){
        return calcRealVel.getCorrectedVel(rightOdometer);
    }
    public double getReavlVelSide(){
        return  calcRealVel.getCorrectedVel(sideOdometer);
    }

    public double getRealVelHeading(){
        double angle = gyro.getRobotYawPitchRollAngles().getYaw();
        double phi = (getRealVelLeft() - getRealVelRight()) / LATERAL_DISTANCE;
        exponentialFilter.update(K_FOR_FILTER, angle,phi);
        return  exponentialFilter.getY();
    }

}



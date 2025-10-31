package org.woen.Pools.DevicePool;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.sun.tools.javac.tree.DCTree;

public class DevicePool {

    public DcMotorEx sideOdometer;
    public DcMotorEx leftOdometer;
    public  DcMotorEx rightOdometer;

    public DcMotorEx rB;

    public DcMotorEx rF;

    public DcMotorEx lB;

    public DcMotorEx lF;

    public IMU gyro;

    private HardwareMap hardwareMap;

    public DevicePool (HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }

    public void init(){
        sideOdometer = hardwareMap.get(DcMotorEx.class, "sideOdometer");

        leftOdometer = hardwareMap.get(DcMotorEx.class, "leftOdometer");

        rightOdometer = hardwareMap.get(DcMotorEx.class, "rightOdometer");

        gyro = hardwareMap.get(IMU.class, "gyro");

        rB = hardwareMap.get(DcMotorEx.class, "rB");
        rF = hardwareMap.get(DcMotorEx.class, "rF");
        lB = hardwareMap.get(DcMotorEx.class, "lB");
        lF = hardwareMap.get(DcMotorEx.class, "lF");
    }
}

package org.woen.Modules.Odometery;

import static java.lang.Math.round;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class CalcRealVel {



    public CalcRealVel(){

    }


    long oldT = System.nanoTime();

    float oldPosition;

    public float getCorrectedVel(DcMotorEx dcMotorEx){

        long t = System.nanoTime();

        float approxVelocity = (float) ((dcMotorEx.getCurrentPosition() - oldPosition) / ((t - oldT)  / 1_000_000_000.0));

        oldPosition = dcMotorEx.getCurrentPosition();

        float rawVel = (float) dcMotorEx.getVelocity();

        int overflowValue = 65536;
        int overflowCount = round((approxVelocity - (float)rawVel) / (float)overflowValue);

        float realVelocity = rawVel + overflowCount * overflowValue;

        return realVelocity;
    }

    private final static int CPS_STEP = 0x10000;

}

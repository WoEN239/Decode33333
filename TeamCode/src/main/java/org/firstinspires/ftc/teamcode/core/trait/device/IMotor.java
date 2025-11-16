package org.firstinspires.ftc.teamcode.core.trait.device;


import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;


public interface IMotor extends IDevice, IDirectional {
    double getPower();
    void setPower(double power);

    ZeroPowerBehavior getZeroPowerBehaviour();
    void setZeroPowerBehaviour(ZeroPowerBehavior behaviour);


    static double normalizePower(double power) {
        if (power > 1) return 1;
        if (power < -1) return -1;
        return power;
    }
}

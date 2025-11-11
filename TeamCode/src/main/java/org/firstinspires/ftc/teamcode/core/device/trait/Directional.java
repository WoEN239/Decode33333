package org.firstinspires.ftc.teamcode.core.device.trait;


import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;


public interface Directional {
    Direction getDirection();
    void setDirection(Direction direction);
    void invertDirection();
}

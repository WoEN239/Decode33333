package org.firstinspires.ftc.teamcode.core.device.trait;


import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;


public interface Directional {
    default int getDirectionSign() {
        return getDirection() == Direction.FORWARD ? 1 : -1;
    }

    Direction getDirection();
    void setDirection(Direction direction);
    void invertDirection();
}

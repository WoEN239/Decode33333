/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.trait.device;


import com.qualcomm.robotcore.hardware.DcMotorSimple;


/**
 * Interface describing devices that has a direction.
 *
 * @see IDevice
 *
 * @see org.firstinspires.ftc.teamcode.core.implementation
 */
public interface IDirectional extends IDevice {
    enum Direction {
        FORWARD(1),
        BACKWARD(-1);

        private final int sign;

        Direction(int sign) {
            this.sign = sign;
        }

        public int getSign() {
            return sign;
        }

        public Direction inverted() {
            return fromRaw(toRaw().inverted());
        }

        public DcMotorSimple.Direction toRaw() {
            return this == FORWARD
                    ? DcMotorSimple.Direction.FORWARD
                    : DcMotorSimple.Direction.REVERSE;
        }

        public static Direction fromRaw(DcMotorSimple.Direction direction) {
            return direction == DcMotorSimple.Direction.FORWARD ? FORWARD : BACKWARD;
        }
    }

    Direction getDirection();
    void setDirection(Direction direction);

    default void invertDirection() {
        setDirection(getDirection().inverted());
    }
}

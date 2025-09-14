package org.woen.core.utils;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Vector2d;

/*
Odometers (from the bottom)
|             |
|  |       |  |
|  ^       ^  |
|  A   *   B  |
|             |
|      _ <- C |
|             |

 */

public class OdometryCalc {

    // odometer value * degToCm = odometer distance
    private double degToCm;

    // Odometer A - Odometer B ~ Rotation
    // fullRotationAB: Change in the difference between the distances
    // of odometers A and B per a 360 rotation
    // Measured not in cm
    private double fullRotationAB;

    // Change in odometer C per a 360 rotation
    private double fullRotationC;

    private double x, y, rot;
    private double prevA, prevB, prevC;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRot() {
        return Math.toDegrees(rot);
    }

    @NonNull
    private static double[] rotateVector(double x, double y, double rot) {
        double cos = Math.cos(rot), sin = Math.sin(rot);
        return new double[]{cos * x + sin * y, cos * y - sin * x};
    }

    public void tick() {

    }
}

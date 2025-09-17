package org.woen.core.util;

/*
Odometers (from the bottom)
|             |
|  |       |  |
|  ^       ^  |
|  A   *   B  |
|             |
|      _ <- C |
|             |

Odometers (from the top)
|             |
|  |       |  |
|  ^       ^  |
|  B   *   A  |
|             |
|      _ <- C |
|             |
 */
// When the robot moves forward, B and A increase, and C remains the same
// When it moves right, C increases
// When it rotates right, C and A decrease, and B increases

public class OdometryCalc {

    // odometer value * ticksToCm = odometer distance
    private final double ticksToCm;

    // Odometer A - Odometer B ~ Rotation
    // fullRotationAB: Change in the difference between the distances
    // of odometers A and B per a 360 rotation
    // Measured not in cm
    private final double ticksPerRotAB;

    // Change in odometer C per a 360 rotation
    private final double ticksPerRotC;

    private double x, y, rot;
    private double prevA, prevB, prevC;

    public OdometryCalc(double ticksToCm, double ticksPerRotAB, double ticksPerRotC) {
        this.ticksToCm = ticksToCm;
        this.ticksPerRotAB = ticksPerRotAB;
        this.ticksPerRotC = ticksPerRotC;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRot() {
        return Math.toDegrees(rot);
    }

    // rotates vector clockwise in right-sided coordinate system
    public static double[] rotateVector(double x, double y, double rot) {
        double cos = Math.cos(rot), sin = Math.sin(rot);
        return new double[]{cos * x + sin * y, cos * y - sin * x};
    }

    public static double angleNorm(double angle) {
        angle = Math.IEEEremainder(angle, 2 * Math.PI); // normalization to (-π, π]
        return angle;
    }

    public void tick(double a, double b, double c) {
        double deltaA = a - prevA, deltaB = b - prevB, deltaC = c - prevC;
        prevA = a;
        prevB = b;
        prevC = c;
        double forward = (deltaA + deltaB) * ticksToCm / 2;
        double rotation = (deltaB - deltaA) / ticksPerRotAB;  // measured in circles
        double right = (deltaC - (rotation * ticksPerRotC)) * ticksToCm;
        rotation = rotation * 2 * Math.PI;  // converted to radians
        rot += rotation;
        rot = angleNorm(rot);
        double[] deltaPos = rotateVector(right, forward, rot);
        x += deltaPos[0];
        y += deltaPos[1];
    }
}

package org.firstinspires.ftc.teamcode.core.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.teamcode.core.device.single.Gyro;
import org.firstinspires.ftc.teamcode.main.movement.Odometry;


// coding by Timofei
// Untested
public final class FieldRenderer {
    public final static double inchesPerCm = 1.0 / 2.54;

    public static double robotWidth = 45.0;
    public static double robotLength = 45.0;

    public static double halfWidth = robotWidth * 0.5;
    public static double halfLength = robotLength * 0.5;

    private static void rotatePoints(double[] xPoints, double[] yPoints, double angle) {
        for (int i = 0; i < xPoints.length; i++) {
            double x = xPoints[i];
            double y = yPoints[i];
            double[] p = Odometry.rotateVector(x, y, angle);
            xPoints[i] = p[0];
            yPoints[i] = p[1];
        }
    }

    private static void shiftPoints(double[] xPoints, double[] yPoints, double shiftX, double shiftY) {
        for (int j = 0; j < xPoints.length; j++) {
            xPoints[j] += shiftX;
            yPoints[j] += shiftY;
        }
    }

    public static void renderRobot(double robotX, double robotY, double robotYaw) {
        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setScale(inchesPerCm, inchesPerCm);

        double[] xPoints = new double[]{
                +halfWidth,  // front right
                +halfWidth,  // back right
                -halfWidth,  // back left
                -halfWidth};  // front left
        double[] yPoints = new double[]{
                +halfLength,  // front right
                -halfLength,  // back right
                -halfLength,  // back left
                +halfLength};  // front left
        rotatePoints(xPoints, yPoints, robotYaw*Gyro.DEG_TO_RAD);
        shiftPoints(xPoints, yPoints, robotX, robotY);

        packet.fieldOverlay().setFill("blue");
        packet.fieldOverlay().fillPolygon(xPoints, yPoints);
        packet.fieldOverlay().strokeLine(xPoints[0], yPoints[0], xPoints[3], yPoints[3]);

        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }

    public static void renderRobot() {
        renderRobot(
                Odometry.getInstance().getX(),
                Odometry.getInstance().getY(),
                Gyro.getInstance().getYaw()
        );
    }
}

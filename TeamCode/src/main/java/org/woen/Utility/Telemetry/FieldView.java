package org.woen.Utility.Telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.woen.Math.Position;


public class FieldView {

    public TelemetryPacket packet = new TelemetryPacket();
    private final double smPerInch = 1.0/2.54;

    public double height = 40.24 / 2.0;
    public double width =  39. / 2.0;

    public Position circle = new Position();
    public Position position = new Position();

    public com.sun.tools.javac.util.Position xui;

    private void rotatePoints(double[] xPoints, double[] yPoints, double angle) {
        for (int i = 0; i < xPoints.length; i++) {
            double x = xPoints[i];
            double y = yPoints[i];
            Position p = new Position(x,y,0);
            p.rotateVector(angle);
            xPoints[i]= p.x;
            yPoints[i]= p.y;
        }
    }
    private void plusVector(double [] x, double [] y, Position p){
        for (int j = 0; j < x.length; j++) {
            x[j] += p.x;
            y[j] += p.y;
        }
    }

    public  void updateField() {
        double[] xPoints;
        double[] yPoints;

        Position rect = new Position(height,width,0);
        rect.rotateVector(position.h);

        xPoints = new double[]{
                + height,
                + height,
                - height,
                - height};
        yPoints = new double[]{
                (+ width),
                (- width),
                (- width),
                (+ width)};

        rotatePoints(xPoints,yPoints,position.h);
        plusVector(xPoints,yPoints,position);

        packet.fieldOverlay().setScale(smPerInch, smPerInch);

        packet.fieldOverlay().setFill("blue");
        packet.fieldOverlay().fillPolygon(xPoints, yPoints);

        packet.fieldOverlay().setFill("green");
        packet.fieldOverlay().fillCircle(circle.x, circle.y,5);

        packet.fieldOverlay().setFill("green");
        packet.fieldOverlay().strokeLine(position.x,position.y, position.x + rect.x, position.y + rect.y);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }

    private void drawCircle(double[] xPos, double[] yPos) {
        for (int i = 0; i < xPos.length; i++) {
            packet.fieldOverlay().setFill("green");
            packet.fieldOverlay().fillCircle(xPos[i], yPos[i],5);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }
    }

}
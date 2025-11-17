package org.firstinspires.ftc.teamcode.main.movement;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.core.device.single.Gyro;


// coding by Timofei

@Config
public final class Odometry {
    private static final Odometry instance = new Odometry();

    private final Gyro gyro = Gyro.getInstance();
    public double ticksX, ticksY, yaw;
    private double oldXOd, oldYOd;
    public static double ticksPerCm = 514;
    private boolean started = false;
    private double yawOffset = 0;
    private double dX = 0;
    private double dY = 0;

    // Сколько лишних тиков набегает при полном обороте робота на месте
    public static double ticksPerRotX = -54234.72927210026;
    public static double ticksPerRotY = -38918.859674602405;


    public static Odometry getInstance() {
        return instance;
    }

    private Odometry() {
    }

    // rotates vector clockwise in right-sided coordinate system
    @NonNull
    public static double[] rotateVector(double x, double y, double rot) {
        double cos = Math.cos(rot*Gyro.DEG_TO_RAD), sin = Math.sin(rot*Gyro.DEG_TO_RAD);
        return new double[]{cos * x + sin * y, cos * y - sin * x};
    }

    public void setPosition(double x, double y) {
        ticksX = x;
        ticksY = y;
        started = false;
    }

    public void setPositionCm(double x, double y) {
        setPosition(x * ticksPerCm, y * ticksPerCm);
    }

    public void setYaw(double yaw) {
        yawOffset = yaw - this.yaw;
        started = false;
    }

    public void odometryTick() {
        if(!started) {
            yaw = gyro.getYaw();
            oldXOd = Vehicles.getInstance().getPositionOdometerX();
            oldYOd = Vehicles.getInstance().getPositionOdometerY();
            started = true;
            return;
        }
        double newTicksX = Vehicles.getInstance().getPositionOdometerX();
        double newTicksY = Vehicles.getInstance().getPositionOdometerY();
        double newYaw = gyro.getYaw();

        dX = newTicksX - oldXOd;
        dY = newTicksY - oldYOd;
        double dYaw = newYaw - yaw;
        dX -= ticksPerRotX * dYaw / 360;
        dY -= ticksPerRotY * dYaw / 360;
        double[] deltaPos = rotateVector(dX, dY, newYaw + yawOffset);

        ticksX += deltaPos[0];
        ticksY += deltaPos[1];
        yaw = newYaw;

        oldXOd = newTicksX;
        oldYOd = newTicksY;
    }

    public double getX() {
        return ticksX / ticksPerCm;
    }

    public double getY() {
        return ticksY / ticksPerCm;
    }

    public double getXSpeed() {
        return dX;
    }

    public double getYSpeed() {
        return dY;
    }

    public double getXSpeedCm() {
        return dX / ticksPerCm;
    }

    public double getYSpeedCm() {
        return dY / ticksPerCm;
    }

    public double getYaw() {
        return yaw + yawOffset;
    }

}

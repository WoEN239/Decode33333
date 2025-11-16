package org.firstinspires.ftc.teamcode.main.movement;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.core.device.single.Gyro;


// coding by Timofei

@Config
public class Odometry {
    private static final Odometry instance = new Odometry();

    private final Vehicles vehicles = Vehicles.getInstance();
    private final Gyro gyro = Gyro.getInstance();
    public double ticksX, ticksY, yaw;
    private double oldXOd, oldYOd;
    public static double ticksPerCm = 543.6;
    private boolean started = false;
    private double yawOffset = 0;

    // Сколько лишних тиков набегает при полном обороте робота на месте
    public static double ticksPerRotX = -50580.434823117714;
    public static double ticksPerRotY = -38074.287148693176;


    public static Odometry getInstance() {
        return instance;
    }

    private Odometry() {
    }

    // rotates vector clockwise in right-sided coordinate system
    @NonNull
    public static double[] rotateVector(double x, double y, double rot) {
        double cos = Math.cos(rot), sin = Math.sin(rot);
        return new double[]{cos * x + sin * y, cos * y - sin * x};
    }

    public void setPosition(double x, double y) {
        ticksX = x;
        ticksY = y;
    }

    public void setPositionCm(double x, double y) {
        setPosition(x * ticksPerCm, y * ticksPerCm);
    }

    public void setYaw(double yaw) {
        yawOffset = yaw - this.yaw;
    }

    public void odometryTick() {
        if(!started) {
            yaw = gyro.getYaw();
            oldXOd = vehicles.getPositionOdometerX();
            oldYOd = vehicles.getPositionOdometerY();
            started = true;
            return;
        }
        double newTicksX = vehicles.getPositionOdometerX();
        double newTicksY = vehicles.getPositionOdometerY();
        double newYaw = gyro.getYaw();

        double dX = newTicksX - oldXOd, dY = newTicksY - oldYOd, dYaw = newYaw - yaw;
        dX -= ticksPerRotX * dYaw / 360;
        dY -= ticksPerRotY * dYaw / 360;
        double[] deltaPos = rotateVector(dX, dY, (newYaw + yawOffset) * Gyro.DEG_TO_RAD);

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

    public double getYaw() {
        return yaw + yawOffset;
    }

}

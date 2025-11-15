package org.firstinspires.ftc.teamcode.main.movement;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.core.device.single.Gyro;


// Untested
// coding by Timofei

@Config
public class Odometry {
    private static final Odometry instance = new Odometry();

    private final Vehicles vehicles = Vehicles.getInstance();
    private final Gyro gyro = Gyro.getInstance();
    public double ticksX, ticksY, yaw;
    public static double ticksPerCm = 1;
    private boolean started = false;

    // Сколько лишних тиков набегает при полном обороте робота на месте
    public static double ticksPerRotX = 0;
    public static double ticksPerRotY = 0;


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

    public void odometryTick() {
        if(!started) {
            yaw = gyro.getYaw();
            ticksX = vehicles.getPositionOdometerX();
            ticksY = vehicles.getPositionOdometerY();
            started = true;
            return;
        }
        double newTicksX = vehicles.getPositionOdometerX();
        double newTicksY = vehicles.getPositionOdometerY();
        double newYaw = gyro.getYaw();

        double dX = newTicksX - ticksX, dY = newTicksY - ticksY, dYaw = newYaw - yaw;
        dX -= ticksPerRotX * dYaw / 360;
        dY -= ticksPerRotY * dYaw / 360;
        double[] deltaPos = rotateVector(dX, dY, newYaw * Gyro.DEG_TO_RAD);

        ticksX += deltaPos[0];
        ticksY += deltaPos[1];
        yaw = newYaw;
    }

    double getX() {
        return ticksX / ticksPerCm;
    }

    double getY() {
        return ticksY / ticksPerCm;
    }

}

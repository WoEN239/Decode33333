package org.firstinspires.ftc.teamcode.main.movement;
import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.core.device.odometer.Odometer;
import org.firstinspires.ftc.teamcode.core.device.single.Gyro;
import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;


// Unfinished
@Config
public class Odometry {
    private static final Odometry instance = new Odometry();

    private final Vehicles vehicles = Vehicles.getInstance();
    private final Gyro gyro = Gyro.getInstance();
    public double ticksX, ticksY, yaw;
    public static double cmPerTick = 1;
    private boolean started = false;


    public static Odometry getInstance() {
        return instance;
    }

    private Odometry() {
    }

    public void odometryTick() {
//        if(!started) {
            yaw = gyro.getYaw();
            ticksX = vehicles.getPositionOdometerX();
            ticksY = vehicles.getPositionOdometerY();
//            started = true;
//            return;
//        }
//        double newTicksX = vehicles.getPositionOdometerX();
//        double newTicksY = vehicles.getPositionOdometerY();
//        double newYaw = gyro.getYaw();
    }

}

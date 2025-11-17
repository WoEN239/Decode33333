package org.firstinspires.ftc.teamcode.main.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.core.device.single.Gyro;
import org.firstinspires.ftc.teamcode.core.util.FieldRenderer;
import org.firstinspires.ftc.teamcode.main.movement.Odometry;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;

// coding by Timofei

@TeleOp(name="Position PID tuner", group="Calibrate")
@Config
public class PosPIDTuner extends OpMode
{

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        Vehicles.getInstance().initialize(hardwareMap);
        Gyro.getInstance().initialize(hardwareMap);
        if (Vehicles.getInstance().isInitialized() &&
                Gyro.getInstance().isInitialized())
        { telemetry.addData("Status", "Initialized"); }
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        Odometry.getInstance().odometryTick();
        FtcDashboard.getInstance().getTelemetry().addData("X Pos:", Odometry.getInstance().getX());
        FtcDashboard.getInstance().getTelemetry().addData("Y Pos:", Odometry.getInstance().getY());
        FtcDashboard.getInstance().getTelemetry().addData("Yaw:", Gyro.getInstance().getYaw());
        FieldRenderer.renderRobot();
        FtcDashboard.getInstance().getTelemetry().update();

        if (gamepad1.square) {
            Vehicles.getInstance().goTo(0, 0);
        } else if (gamepad1.circle) {
            Vehicles.getInstance().rotateTo(0);
        } else if (gamepad1.triangle) {
            Vehicles.getInstance().goTo(0, 0, 0);
        } else {
            Vehicles.getInstance().moveToDirection(-gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x);
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        Vehicles.getInstance().moveToDirection(0, 0, 0);
    }

}

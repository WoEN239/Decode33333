package org.firstinspires.ftc.teamcode.main.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.device.single.Gyro;
import org.firstinspires.ftc.teamcode.main.modules.GunControl;
import org.firstinspires.ftc.teamcode.main.modules.TransferBall;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;
import org.firstinspires.ftc.teamcode.main.opencv.Vision;

// coding by Timofei

@TeleOp(name="Odometer A", group="Calibrate")
public class OdometryCalibrateA extends OpMode
{
    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    private GunControl gun;
    private TransferBall transfer;
    private Vision vision;
    public static double degreeGunTower = 0;
    public boolean stateFlow = false;
    private double zeroOdometerX, zeroOdometerY, zeroYaw;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        Vehicles.getInstance().initialize(hardwareMap);
        TransferBall.getInstance().initialize(hardwareMap);
        GunControl.getInstance().initialize(hardwareMap);
        Gyro.getInstance().initialize(hardwareMap);
        if (Vehicles.getInstance().isInitialized() &&
                TransferBall.getInstance().isInitialized() &&
                GunControl.getInstance().isInitialized() &&
                Vision.getInstance().isInitialized() &&
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
        zeroOdometerX = Vehicles.getInstance().getPositionOdometerX();
        zeroOdometerY = Vehicles.getInstance().getPositionOdometerY();
        zeroYaw = Gyro.getInstance().getYaw();
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        FtcDashboard.getInstance().getTelemetry().addData("OdometerX:", Vehicles.getInstance().getPositionOdometerX() - zeroOdometerX);
        FtcDashboard.getInstance().getTelemetry().addData("OdometerY:", Vehicles.getInstance().getPositionOdometerY() - zeroOdometerY);
        FtcDashboard.getInstance().getTelemetry().addData("Yaw:", Gyro.getInstance().getYaw() - zeroYaw);
        FtcDashboard.getInstance().getTelemetry().addData("TicksPerRotX:",
                (Vehicles.getInstance().getPositionOdometerX() - zeroOdometerX) / (Gyro.getInstance().getYaw() - zeroYaw) * 360);
        FtcDashboard.getInstance().getTelemetry().addData("TicksPerRotY:",
                (Vehicles.getInstance().getPositionOdometerX() - zeroOdometerY) / (Gyro.getInstance().getYaw() - zeroYaw) * 360);
        FtcDashboard.getInstance().getTelemetry().update();

        Vehicles.getInstance().moveToDirection(0, 0, gamepad1.right_stick_x);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        Vehicles.getInstance().moveToDirection(0, 0, 0);
        GunControl.getInstance().stopShot();
        TransferBall.getInstance().stopBrush();
        TransferBall.getInstance().stopFlow();
        GunControl.getInstance().stopShot();
//        Vision.getInstance().stopStreaming();
    }

}

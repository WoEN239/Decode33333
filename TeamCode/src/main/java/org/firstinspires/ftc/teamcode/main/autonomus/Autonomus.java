package org.firstinspires.ftc.teamcode.main.autonomus;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage;
import org.firstinspires.ftc.teamcode.main.modules.GunControl;
import org.firstinspires.ftc.teamcode.main.modules.TransferBall;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;
import org.firstinspires.ftc.teamcode.main.opencv.AprilTag;
import org.firstinspires.ftc.teamcode.main.opencv.Vision;


@Autonomous(name="Basic gamepad", group="Dev")
public class Autonomus extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public SensorVoltage voltageSensor;
    public AprilTag aprilTag;
    private GunControl gun;
    private TransferBall transfer;

    @Override
    public void runOpMode() {

        waitForStart();
        AprilTag.getInstance().initialize(hardwareMap);
        FtcDashboard.getInstance().startCameraStream(AprilTag.getInstance().visionPortal, 30);
        TransferBall.getInstance().initialize(hardwareMap);
        GunControl.getInstance().initialize(hardwareMap);
        voltageSensor = new SensorVoltage(hardwareMap);
        if (Vehicles.getInstance().isInitialized() && TransferBall.getInstance().isInitialized() && GunControl.getInstance().isInitialized() && Vision.getInstance().isInitialized()) { telemetry.addData("Status", "Initialized"); }
        Vehicles.getInstance().initialize(hardwareMap);

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                while (AprilTag.getInstance().getPosAprilY() < 37) {
                    GunControl.getInstance().startShot();
//                    Vehicles.getInstance().moveToDirection(-0.3,
//                            0.3,
//                            0.0);
                    AprilTag.getInstance().telemetryAprilTag();
                    FtcDashboard.getInstance().getTelemetry().addData("Encoder gun", GunControl.getInstance().getEncoderPos());
                    FtcDashboard.getInstance().getTelemetry().update();
                }
                GunControl.getInstance().startShot();
//                if (GunControl.getInstance().getPower() < )
                Vehicles.getInstance().moveToDirection(0.0,
                        0.0,
                        0.0);
                TransferBall.getInstance().startFlow();
                AprilTag.getInstance().telemetryAprilTag();
//                FtcDashboard.getInstance().getTelemetry().addData("Go back", aprilPosY);
                FtcDashboard.getInstance().getTelemetry().addData("Encoder gun", GunControl.getInstance().getEncoderPos());
                FtcDashboard.getInstance().getTelemetry().update();
            }
        }
        Vehicles.getInstance().moveToDirection(0.0,
                0.0,
                0.0);
        AprilTag.getInstance().visionPortal.close();
    }
}

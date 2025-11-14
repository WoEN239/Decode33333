package org.firstinspires.ftc.teamcode.main.autonomus;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
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

// coding by Matvey Ivanovv

@Autonomous(name="Autonomus", group="Dev")
@Config
public class Autonomus extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public SensorVoltage voltageSensor;
    public AprilTag aprilTag;
    private GunControl gun;
    private TransferBall transfer;

    double drive;
    double turn;
    double strafe;

    public static double kpForward = 0.0;
    public static double kpHorizontal = 0.0;
    public static double kpTurn = 0.0;

    @Override
    public void runOpMode() {

        waitForStart();
        AprilTag.getInstance().initialize(hardwareMap);
        FtcDashboard.getInstance().startCameraStream(AprilTag.getInstance().visionPortal, 30);
        TransferBall.getInstance().initialize(hardwareMap);
        GunControl.getInstance().initialize(hardwareMap);
        voltageSensor.initialize(hardwareMap);
        if (Vehicles.getInstance().isInitialized() && TransferBall.getInstance().isInitialized() && GunControl.getInstance().isInitialized() && Vision.getInstance().isInitialized()) { telemetry.addData("Status", "Initialized"); }
        Vehicles.getInstance().initialize(hardwareMap);

        if (opModeIsActive()) {
            while (opModeIsActive()) {
//                while (AprilTag.getInstance().getPosAprilY() < 37) {
                    GunControl.getInstance().startShot();
                    Vehicles.getInstance().moveToDirection(drive,
                            strafe,
                            turn);
                    AprilTag.getInstance().telemetryAprilTag();
                    calculateRegulatorTag(AprilTag.getInstance().getRange(), AprilTag.getInstance().getYaw(), AprilTag.getInstance().getPosAprilX());
                    FtcDashboard.getInstance().getTelemetry().update();
//                }
//                GunControl.getInstance().startShot();
////                if (GunControl.getInstance().getPower() < )
//                Vehicles.getInstance().moveToDirection(0.0,
//                        0.0,
//                        0.0);
//                TransferBall.getInstance().startFlow();
//                AprilTag.getInstance().telemetryAprilTag();
//                FtcDashboard.getInstance().getTelemetry().update();
//                AprilTag.getInstance().telemetryAprilTag();
//                FtcDashboard.getInstance().getTelemetry().update();
            }
        }
        Vehicles.getInstance().moveToDirection(0.0,
                0.0,
                0.0);
        AprilTag.getInstance().visionPortal.close();
    }

    public void calculateRegulatorTag(double range, double yaw, double posX) {
        double rangeError = range - AprilTag.getInstance().getRange();
        double yawError = yaw - AprilTag.getInstance().getYaw();
        double xError = posX - AprilTag.getInstance().getPosAprilX();

        drive = rangeError * kpForward;
        turn = yawError * kpTurn;
        strafe = xError * kpHorizontal;

        drive = Math.max(-1, Math.min(drive, 1));
        turn = Math.max(-1, Math.min(turn, 1));
        strafe = Math.max(-1, Math.min(strafe, 1));
    }
}

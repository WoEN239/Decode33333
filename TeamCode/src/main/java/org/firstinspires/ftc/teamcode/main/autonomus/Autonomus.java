package org.firstinspires.ftc.teamcode.main.autonomus;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage;
import org.firstinspires.ftc.teamcode.main.modules.GunControl;
import org.firstinspires.ftc.teamcode.main.modules.TransferBall;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;
import org.firstinspires.ftc.teamcode.main.opencv.AprilTag;

@Autonomous(name="Autonomus", group="Dev")
@Config
public class Autonomus extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public AprilTag aprilTag;
    private GunControl gun;
    private TransferBall transfer;

    double drive;
    double turn;
    double strafe;

    public static double kpForward = 0.01;
    public static double kpHorizontal = 0.01;
    public static double kpTurn = 0.01;
    public static double deadZone = 2.0;
    public static double smoothingFactor = 0.3;

    private double prevDrive = 0;
    private double prevTurn = 0;
    private double prevStrafe = 0;

    @Override
    public void runOpMode() {
        AprilTag.getInstance().initialize(hardwareMap);
        Vehicles.getInstance().initialize(hardwareMap);
        TransferBall.getInstance().initialize(hardwareMap);
        GunControl.getInstance().initialize(hardwareMap);
        SensorVoltage.getInstance().initialize(hardwareMap);

        FtcDashboard.getInstance().startCameraStream(AprilTag.getInstance().visionPortal, 30);

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                GunControl.getInstance().startShot();
                AprilTag.getInstance().telemetryAprilTag();
                TransferBall.getInstance().startFlow();
                calculateRegulatorTag(41.5, -2.5, -0.35);

                Vehicles.getInstance().moveToDirection(drive, strafe, turn);

                FtcDashboard.getInstance().getTelemetry().update();
            }
        }

        Vehicles.getInstance().moveToDirection(0.0, 0.0, 0.0);
        AprilTag.getInstance().visionPortal.close();
    }

    public void calculateRegulatorTag(double range, double yaw, double posX) {
        double rangeError = range - AprilTag.getInstance().getRange();
        double yawError = yaw - AprilTag.getInstance().getYaw();
        double xError = posX - AprilTag.getInstance().getPosAprilX();

//        if (Math.abs(rangeError) < deadZone) rangeError = 0;
//        if (Math.abs(yawError) < deadZone) yawError = 0;
//        if (Math.abs(xError) < deadZone) xError = 0;

        double rawDrive = rangeError * kpForward;
        double rawTurn = yawError * kpTurn;
        double rawStrafe = xError * kpHorizontal;

        drive = smoothingFactor * (rawDrive - prevDrive);
        turn = smoothingFactor * (rawTurn - prevTurn);
        strafe = smoothingFactor * (rawStrafe - prevStrafe);

        prevDrive = drive;
        prevTurn = turn;
        prevStrafe = strafe;

        drive = Math.max(-1, Math.min(drive, 1));
        turn = Math.max(-1, Math.min(turn, 1));
        strafe = Math.max(-1, Math.min(strafe, 1));

        FtcDashboard.getInstance().getTelemetry().addData("Range", AprilTag.getInstance().getRange());
        FtcDashboard.getInstance().getTelemetry().addData("Yaw", AprilTag.getInstance().getYaw());
        FtcDashboard.getInstance().getTelemetry().addData("X", AprilTag.getInstance().getPosAprilX());
        FtcDashboard.getInstance().getTelemetry().addData("drive", drive);
        FtcDashboard.getInstance().getTelemetry().addData("turn", turn);
        FtcDashboard.getInstance().getTelemetry().addData("strafe", strafe);
    }
}
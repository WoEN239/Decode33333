package org.firstinspires.ftc.teamcode.main.autonomus;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.device.sensor.SensorVoltage;
import org.firstinspires.ftc.teamcode.main.modules.gun.GunControl;
import org.firstinspires.ftc.teamcode.main.modules.transfer.TransferBall;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;
import org.firstinspires.ftc.teamcode.main.opencv.AprilTag;

@Autonomous(name="AutonomusRedTower", group="Dev")
@Config
public class AutonomusRed extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public AprilTag aprilTag;
    private GunControl gun;
    private TransferBall transfer;
    private int countBall = 0;

    double drive;
    double turn;
    double strafe;

    public static double kpForward = 0.1;
    public static double kpHorizontal = 0.1;
    public static double kpTurn = 0.1;
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
            AprilTag.getInstance().resetApril();
            FtcDashboard.getInstance().getTelemetry().addData("AprilY", AprilTag.getInstance().getPosAprilY());
            FtcDashboard.getInstance().getTelemetry().update();
            while (opModeIsActive()) {
                FtcDashboard.getInstance().getTelemetry().addData("OpMode start", AprilTag.getInstance().getPosAprilY());
                FtcDashboard.getInstance().getTelemetry().update();
                runtime.reset();
                while (runtime.milliseconds() < 500 && opModeIsActive()) { FtcDashboard.getInstance().getTelemetry().addData("Time", runtime.milliseconds()); }
                if (opModeIsActive()) {
                    while (opModeIsActive() && AprilTag.getInstance().getId() != 24 && AprilTag.getInstance().getPosAprilY() < 33) {
                        AprilTag.getInstance().telemetryAprilTag();
                        GunControl.getInstance().startShot();
                        calculateRegulatorTag(-0.4, 41.7, 4.5, -2.5);
                        Vehicles.getInstance().moveToDirection(-0.2, 0.0, 0);
                        FtcDashboard.getInstance().getTelemetry().addData("Is target position", isInTargetPosition(30));
                        FtcDashboard.getInstance().getTelemetry().update();
                    }
                }
                FtcDashboard.getInstance().getTelemetry().addData("To position", true);
                FtcDashboard.getInstance().getTelemetry().addData("AprilY", AprilTag.getInstance().getPosAprilY());
                FtcDashboard.getInstance().getTelemetry().update();
                Vehicles.getInstance().moveToDirection(0.0, 0.0, 0.0);

                while (AprilTag.getInstance().getPosAprilY() >= 30 && opModeIsActive() && countBall < 2) {
                    GunControl.getInstance().startShot();
                    TransferBall.getInstance().startFlow();

                    if (GunControl.getInstance().getSpeedGun() < -860) {
                        runtime.reset();
                        while (runtime.milliseconds() < 1200) {
                            GunControl.getInstance().setVelocity(-900);
                            GunControl.getInstance().startShot();
                            TransferBall.getInstance().startFlow();
                        }
                        countBall++;
                        FtcDashboard.getInstance().getTelemetry().addData("GunSpeed", GunControl.getInstance().getSpeedGun());
                        FtcDashboard.getInstance().getTelemetry().addData("Count Ball", countBall);
                        FtcDashboard.getInstance().getTelemetry().update();
                    }
                    runtime.reset();
                    while (runtime.milliseconds() < 2000 && opModeIsActive()) {
                        Vehicles.getInstance().moveToDirection(0.0, 0.0, 0.0);
                    }
                }

                if (countBall == 2) {
                    TransferBall.getInstance().setDegreeServo(0.0);
                }


            }
            GunControl.getInstance().stopShot();
            TransferBall.getInstance().setDegreeServo(0.5);
            runtime.reset();
            while (runtime.milliseconds() < 2000 && opModeIsActive()) {
                Vehicles.getInstance().moveToDirection(0.0, 0.3, 0.0);
            }
            Vehicles.getInstance().moveToDirection(0.0, 0.0, 0.0);
            AprilTag.getInstance().visionPortal.close();
        }
        GunControl.getInstance().stopShot();
        GunControl.getInstance().setVelocity(-1100);
        Vehicles.getInstance().moveToDirection(0.0, 0.0, 0.0);
        AprilTag.getInstance().visionPortal.close();
    }

    private boolean isInTargetPosition(double targetY) {
        double yError = Math.abs(targetY - AprilTag.getInstance().getPosAprilY());

        return (yError < deadZone);
    }

    public void calculateRegulatorTag(double targetX, double targetY, double targetZ, double targetYaw) {
        double xError = targetX - AprilTag.getInstance().getPosAprilX();
        double yError = targetY - AprilTag.getInstance().getPosAprilY();
        double zError = targetZ - AprilTag.getInstance().getRange();
        double yawError = targetYaw - AprilTag.getInstance().getYaw();

        if (Math.abs(xError) < deadZone) xError = 0;
        if (Math.abs(yError) < deadZone) yError = 0;
        if (Math.abs(zError) < deadZone) zError = 0;
        if (Math.abs(yawError) < deadZone) yawError = 0;

        double rawHorizontal = xError * kpHorizontal;
        double rawForward = yError * kpForward;
        double rawTurn = yawError * kpTurn;

        drive = smoothingFactor * (rawForward - prevDrive);
        turn = smoothingFactor * (rawTurn - prevTurn);
        strafe = smoothingFactor * (rawHorizontal - prevStrafe);

        prevDrive = drive;
        prevTurn = turn;
        prevStrafe = strafe;

        drive = Math.max(-1, Math.min(drive, 1));
        turn = Math.max(-1, Math.min(turn, 1));
        strafe = Math.max(-1, Math.min(strafe, 1));

        FtcDashboard.getInstance().getTelemetry().addData("X", AprilTag.getInstance().getPosAprilX());
        FtcDashboard.getInstance().getTelemetry().addData("Y", AprilTag.getInstance().getPosAprilY());
        FtcDashboard.getInstance().getTelemetry().addData("Z", AprilTag.getInstance().getRange());
        FtcDashboard.getInstance().getTelemetry().addData("Yaw", AprilTag.getInstance().getYaw());
        FtcDashboard.getInstance().getTelemetry().addData("drive", drive);
        FtcDashboard.getInstance().getTelemetry().addData("turn", turn);
        FtcDashboard.getInstance().getTelemetry().addData("strafe", strafe);
    }
}
package org.firstinspires.ftc.teamcode.main.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.core.device.single.Gyro;
import org.firstinspires.ftc.teamcode.main.movement.Odometry;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;
import org.firstinspires.ftc.teamcode.main.modules.gun.GunControl;
import org.firstinspires.ftc.teamcode.main.modules.transfer.TransferBall;
import org.firstinspires.ftc.teamcode.main.opencv.Vision;

// coding by Matvey Ivanovv

/*
    EDGE - ПОБЕДА!
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Dev")
public class TeleOp extends OpMode
{
    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    public static double degreeGunTower = 0;
    public boolean stateFlow = false;
    private int countBall = 0;
    private double kDrive = 1.0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        Vehicles.getInstance().initialize(hardwareMap);
        TransferBall.getInstance().initialize(hardwareMap);
        GunControl.getInstance().initialize(hardwareMap);
        Gyro.getInstance().initialize(hardwareMap);
//        Vision.getInstance().initialize(hardwareMap);
        if (Vehicles.getInstance().isInitialized() &&
                TransferBall.getInstance().isInitialized() &&
                GunControl.getInstance().isInitialized() &&
                Vision.getInstance().isInitialized() &&
                Gyro.getInstance().isInitialized())
        { FtcDashboard.getInstance().getTelemetry().addData("Status", "Initialized"); }
//        Vision.getInstance().startStreaming();
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
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        Odometry.getInstance().odometryTick();
        FtcDashboard.getInstance().getTelemetry().addData("X Pos:", Odometry.getInstance().getX());
        FtcDashboard.getInstance().getTelemetry().addData("Y Pos:", Odometry.getInstance().getY());


        FtcDashboard.getInstance().getTelemetry().addData("Velocity Gun:", GunControl.getInstance().getVelocity());
        FtcDashboard.getInstance().getTelemetry().addData("Velocity Flow:", TransferBall.getInstance().getVelocityFlow());
        FtcDashboard.getInstance().getTelemetry().addData("Velocity Brush:", TransferBall.getInstance().getVelocityBrush());
        FtcDashboard.getInstance().getTelemetry().addData("Degree kick:", TransferBall.getInstance().getDegreeServo());
        FtcDashboard.getInstance().getTelemetry().addData("Degree tower:", GunControl.getInstance().getTowerDegree());
        FtcDashboard.getInstance().getTelemetry().addData("OdometerX:", Vehicles.getInstance().getPositionOdometerX());
        FtcDashboard.getInstance().getTelemetry().addData("OdometerY:", Vehicles.getInstance().getPositionOdometerY());
        FtcDashboard.getInstance().getTelemetry().addData("Yaw:", Gyro.getInstance().getYaw());
        FtcDashboard.getInstance().getTelemetry().update();

        Vehicles.getInstance().moveToDirection(-gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x);

        GunControl.getInstance().startShot();
        TransferBall.getInstance().startBrush();

//        if (gamepad1.left_bumper) {
//            degreeGunTower += 0.05;
//            GunControl.getInstance().setTowerDegree(degreeGunTower);
//        } else if (gamepad1.right_bumper){
//            degreeGunTower -= 0.05;
//            GunControl.getInstance().setTowerDegree(degreeGunTower);
//        }
//        GunControl.getInstance().setTowerDegree(GunControl.getInstance().getTowerDegree());

        if (gamepad1.square) {
            TransferBall.getInstance().setDegreeServo(0.0);
        } else {
            TransferBall.getInstance().setDegreeServo(0.5);
        }
        if (gamepad1.left_bumper) {
            kDrive = 0.3;
        }  else {
            kDrive = 1;
        }

        if (gamepad1.circle) {
            TransferBall.getInstance().startFlow();
        } else {
            TransferBall.getInstance().stopFlow();
        }
//        if (gamepad1.right_bumper) {
//            autoGunBall(-860, 1200);
//        } else {
//            TransferBall.getInstance().stopFlow();
//        }
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

    private void autoGunBall(double time, int drawdown) {
        while (countBall < 2) {
            GunControl.getInstance().startShot();
            TransferBall.getInstance().startFlow();
            if (GunControl.getInstance().getSpeedGun() < -860) {
                runtime.reset();
                while (runtime.milliseconds() < 600) {
                    GunControl.getInstance().startShot();
                    TransferBall.getInstance().startFlow();
                }
                countBall++;
                FtcDashboard.getInstance().getTelemetry().addData("GunSpeed", GunControl.getInstance().getSpeedGun());
                FtcDashboard.getInstance().getTelemetry().addData("Count Ball", countBall);
                FtcDashboard.getInstance().getTelemetry().update();
            }
        }

        if (countBall == 2) {
            TransferBall.getInstance().setDegreeServo(0.0);
        }
    }

}
//
//@TeleOp(name="Basic gamepad", group="Dev")
//public class BasicMovement extends OpMode
//{
//    // Declare OpMode members.
//    private final ElapsedTime runtime = new ElapsedTime();
//    IntegratingGyroscope gyro;
//    IMU imu;
//    private GunControl gun;
//    private TransferBall transfer;
//    public double degreeGunTower;
//    public boolean stateFlow = false;
//    public boolean stateGun = false;
//    public double velocityGun = 0.6;
//
//    /*
//     * Code to run ONCE when the driver hits INIT
//     */
//    @Override
//    public void init() {
//        Vehicles.getInstance().initialize(hardwareMap);
//        TransferBall.getInstance().initialize(hardwareMap);
//        GunControl.getInstance().initialize(hardwareMap);
//        telemetry.addData("Status", "Initialized");
//    }
//
//    /*
//     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
//     */
//    @Override
//    public void init_loop() {
//    }
//
//    /*
//     * Code to run ONCE when the driver hits START
//     */
//    @Override
//    public void start() {
//        runtime.reset();
//    }
//
//    /*
//     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
//     */
//    @Override
//    public void loop() {
//        FtcDashboard.getInstance().getTelemetry().addData("Velocity Gun:", GunControl.getInstance().getVelocity());
//        FtcDashboard.getInstance().getTelemetry().update();
//        if (gamepad1.triangle) { stateFlow = true; }
//        if (gamepad1.circle) { stateFlow = false; }
//        if (gamepad1.dpad_up) { stateGun = true; }
//        if (gamepad1.dpad_down) { stateGun = false; }
//        telemetry.addData("Status", "Run Time: " + runtime.toString());
//        telemetry.addData("GPX", gamepad1.left_stick_x);
//        telemetry.addData("GPY", -gamepad1.left_stick_y);
//        telemetry.update();
//        Vehicles.getInstance().moveToDirection(-gamepad1.left_stick_y,
//                gamepad1.left_stick_x,
//                gamepad1.right_stick_x);
//        TransferBall.getInstance().startBrush();
//        GunControl.getInstance().startShot();
//        if (stateGun) {
//            GunControl.getInstance().startShot();
//        } else { GunControl.getInstance().stopShot(); }
//        if (gamepad1.left_bumper) {
//            degreeGunTower += 0.05;
//            GunControl.getInstance().setTowerDegree(degreeGunTower);
//        } else if (gamepad1.right_bumper){
//            degreeGunTower -= 0.05;
//            GunControl.getInstance().setTowerDegree(degreeGunTower);
//        }
//        if (gamepad1.square) {
//            TransferBall.getInstance().setDegreeServo(0.0);
//        } else {
//            TransferBall.getInstance().setDegreeServo(0.5);
//        }
//        if (gamepad1.circle) {
//            TransferBall.getInstance().startFlow();
//        } else {
//            TransferBall.getInstance().stopFlow();
//        }
//
//       /* if (gamepad1.dpad_left) {
//            velocityGun += 0.05;
//            if (velocityGun > 1) velocityGun = 1;
//            GunControl.getInstance().setVelocity(velocityGun);
//        } else if (gamepad1.dpad_right) {
//            velocityGun -= 0.05;
//            if (velocityGun < 0) velocityGun = 0;
//            GunControl.getInstance().setVelocity(velocityGun);
//        }
//
//        */
//
//
//    }
//
//    /*
//     * Code to run ONCE after the driver hits STOP
//     */
//    @Override
//    public void stop() {
//        Vehicles.getInstance().moveToDirection(0, 0, 0);
//        GunControl.getInstance().stopShot();
//        TransferBall.getInstance().stopBrush();
//        TransferBall.getInstance().stopFlow();
//        GunControl.getInstance().stopShot();
//    }
//
//}

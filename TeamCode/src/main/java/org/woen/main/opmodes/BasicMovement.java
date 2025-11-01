package org.woen.main.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.woen.main.movement.Vehicles;
import org.woen.main.gun.GunControl;
import org.woen.main.modules.TransferBall;

@TeleOp(name="Basic gamepad", group="Dev")
public class BasicMovement extends OpMode
{
    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    IntegratingGyroscope gyro;
    IMU imu;
    private GunControl gun;
    private TransferBall transfer;
    public double degreeGunTower;
    public boolean stateFlow = false;
    public boolean stateGun = false;
    public double velocityGun = 0.6;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        Vehicles.getInstance().initialize(hardwareMap);
        TransferBall.getInstance().initialize(hardwareMap);
        GunControl.getInstance().initialize(hardwareMap);
        telemetry.addData("Status", "Initialized");
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
        FtcDashboard.getInstance().getTelemetry().addData("Velocity Gun:", GunControl.getInstance().getVelocity());
        FtcDashboard.getInstance().getTelemetry().update();
        if (gamepad1.triangle) { stateFlow = true; }
        if (gamepad1.circle) { stateFlow = false; }
        if (gamepad1.dpad_up) { stateGun = true; }
        if (gamepad1.dpad_down) { stateGun = false; }
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("GPX", gamepad1.left_stick_x);
        telemetry.addData("GPY", -gamepad1.left_stick_y);
        telemetry.update();
        Vehicles.getInstance().moveToDirection(-gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x);
        TransferBall.getInstance().startBrush();
        GunControl.getInstance().setVelocity(velocityGun);
        if (stateGun) {
            GunControl.getInstance().setVelocity(GunControl.getInstance().getVelocity());
        } else { GunControl.getInstance().stopShot(); }
        if (gamepad1.left_bumper) {
            degreeGunTower += 0.05;
            GunControl.getInstance().setTowerDegree(degreeGunTower);
        } else if (gamepad1.right_bumper){
            degreeGunTower -= 0.05;
            GunControl.getInstance().setTowerDegree(degreeGunTower);
        }
        if (gamepad1.square) {
            TransferBall.getInstance().setDegreeServo(0.0);
        } else {
            TransferBall.getInstance().setDegreeServo(0.5);
            }
        if (gamepad1.circle) {
            TransferBall.getInstance().startFlow();
        } else {
            TransferBall.getInstance().stopFlow();
        }

       /* if (gamepad1.dpad_left) {
            velocityGun += 0.05;
            if (velocityGun > 1) velocityGun = 1;
            GunControl.getInstance().setVelocity(velocityGun);
        } else if (gamepad1.dpad_right) {
            velocityGun -= 0.05;
            if (velocityGun < 0) velocityGun = 0;
            GunControl.getInstance().setVelocity(velocityGun);
        }

        */


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
    }

}

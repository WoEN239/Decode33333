package org.woen.main.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.woen.core.device.odometer.Odometer;
import org.woen.main.movement.Vehicles;
import org.woen.main.modules.GunControl;
import org.woen.main.modules.TransferBall;
import org.woen.main.opencv.Vision;

@TeleOp(name="Basic gamepad", group="Dev")
public class BasicMovement extends OpMode
{
    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    private GunControl gun;
    private TransferBall transfer;
    private Vision vision;
    public double degreeGunTower;
    public boolean stateFlow = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        Vehicles.getInstance().initialize(hardwareMap);
        TransferBall.getInstance().initialize(hardwareMap);
        GunControl.getInstance().initialize(hardwareMap);
//        Vision.getInstance().initialize(hardwareMap);
        if (Vehicles.getInstance().isInitialized() && TransferBall.getInstance().isInitialized() && GunControl.getInstance().isInitialized() && Vision.getInstance().isInitialized()) { telemetry.addData("Status", "Initialized"); }
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
        FtcDashboard.getInstance().getTelemetry().addData("Velocity Gun:", GunControl.getInstance().getVelocity());
        FtcDashboard.getInstance().getTelemetry().addData("Velocity Flow:", TransferBall.getInstance().getVelocityFlow());
        FtcDashboard.getInstance().getTelemetry().addData("Velocity Brush:", TransferBall.getInstance().getVelocityBrush());
        FtcDashboard.getInstance().getTelemetry().addData("Degree kick:", TransferBall.getInstance().getDegreeServo());
        FtcDashboard.getInstance().getTelemetry().addData("Degree tower:", GunControl.getInstance().getTowerDegree());
        FtcDashboard.getInstance().getTelemetry().addData("OdometerX:", Vehicles.getInstance().getPositionOdometerX());
        FtcDashboard.getInstance().getTelemetry().addData("OdometerY:", Vehicles.getInstance().getPositionOdometerY());
        FtcDashboard.getInstance().getTelemetry().update();

        Vehicles.getInstance().moveToDirection(-gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x);

        GunControl.getInstance().startShot();
        TransferBall.getInstance().startBrush();

        if (gamepad1.left_bumper) {
            degreeGunTower += 0.05;
            GunControl.getInstance().setTowerDegree(degreeGunTower);
        } else if (gamepad1.right_bumper){
            degreeGunTower -= 0.05;
            GunControl.getInstance().setTowerDegree(degreeGunTower);
        }

        if (gamepad1.square) {
            TransferBall.getInstance().setDegreeServo(TransferBall.getInstance().getDegreeServo() - 0.5);
        } else {
            TransferBall.getInstance().setDegreeServo(TransferBall.getInstance().getDegreeServo());
        }

        if (gamepad1.circle) {
            TransferBall.getInstance().startFlow();
        } else {
            TransferBall.getInstance().stopFlow();
        }
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

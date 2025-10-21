package org.woen.main.opmodes;

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


@TeleOp(name="Basic gamepad", group="Dev")
public class BasicMovement extends OpMode
{
    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    IntegratingGyroscope gyro;
    IMU imu;
    private GunControl gun;
    public double count;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        Vehicles.getInstance().initialize(hardwareMap);
        gun = new GunControl();

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
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
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("GPX", gamepad1.left_stick_x);
        telemetry.addData("GPY", -gamepad1.left_stick_y);
        telemetry.addData("GPR", gamepad1.right_trigger - gamepad1.left_trigger);
        telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
        telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
        telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));

        Vehicles.getInstance().moveToDirection(gamepad1.left_stick_x,
                -gamepad1.left_stick_y,
                gamepad1.right_trigger - gamepad1.left_trigger);
        if (gamepad1.dpad_up) {
            gun.setVelocity(0.6);
            gun.startShot();
        } else if (gamepad1.dpad_down) { gun.stopShot(); }
        if (gamepad1.left_bumper) {
            count += 0.05;
            gun.setTowerDegree(count);
        } else if (gamepad1.right_bumper){
            count -= 0.05;
            gun.setTowerDegree(count);
        }

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        Vehicles.getInstance().moveToDirection(0, 0, 0);
        gun.stopShot();
    }

}

package org.woen.main.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.woen.main.movement.Vehicles;


@TeleOp(name="Basic gamepad", group="Dev")
public class BasicMovement extends OpMode
{
    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        Vehicles.getInstance().initialize(hardwareMap);

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
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("GPX", gamepad1.left_stick_x);
        telemetry.addData("GPY", -gamepad1.left_stick_y);
        telemetry.addData("GPR", gamepad1.right_trigger - gamepad1.left_trigger);

        Vehicles.getInstance().moveToDirection(gamepad1.left_stick_x,
                -gamepad1.left_stick_y,
                gamepad1.right_trigger - gamepad1.left_trigger);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        Vehicles.getInstance().moveToDirection(0, 0, 0);
    }

}

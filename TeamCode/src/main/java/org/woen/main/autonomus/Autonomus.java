package org.woen.main.autonomus;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.woen.main.movement.Vehicles;


@Autonomous(name="Basic gamepad", group="Dev")
public class Autonomus extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Vehicles.getInstance().initialize(hardwareMap);
        waitForStart();
        runtime.reset();
        while (opModeIsActive() && runtime.milliseconds() < 300) {
            Vehicles.getInstance().moveToDirection(1, 0, 0);
        }
        Vehicles.getInstance().moveToDirection(0, 0, 0);
    }
}

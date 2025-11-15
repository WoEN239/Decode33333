package org.firstinspires.ftc.teamcode.main.opencv;

import android.util.Size;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;
import org.firstinspires.ftc.teamcode.main.modules.GunControl;
import org.firstinspires.ftc.teamcode.main.modules.TransferBall;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;

import java.util.List;

// coding by Matvey Ivanovv

public class AprilTag implements Initializable {

    private static final org.firstinspires.ftc.teamcode.main.opencv.AprilTag INSTANCE = new org.firstinspires.ftc.teamcode.main.opencv.AprilTag();
    private static final boolean USE_WEBCAM = true;
    public ElapsedTime runtime = new ElapsedTime();

    private AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    private GunControl gun;
    private TransferBall transfer;
    private double posAprilX = 0;
    private double posAprilY = 0;
    private double posAprilZ = 0;
    private double id = 0;

    private double bearing;

    private double yaw;

    private double range;

    private boolean isInitialized = false;

    @Override
    public void initialize(HardwareMap hardwareMap) {
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();

        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        builder.setCameraResolution(new Size(640, 480));
        builder.enableLiveView(true);
        builder.addProcessor(aprilTag);

        visionPortal = builder.build();

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    public static org.firstinspires.ftc.teamcode.main.opencv.AprilTag getInstance() { return INSTANCE; }

    public double getPosAprilX() {
        return posAprilX;
    }

    public double getPosAprilY() {
        return posAprilY;
    }

    public double getPosAprilZ() {
        return posAprilZ;
    }

    public double getId() { return id; }

    public double getBearing() { return bearing; }

    public double getRange() { return range; }

    public double getYaw() { return yaw; }

    public void resetApril() {
        posAprilX = 0;
        posAprilZ = 0;
        posAprilY = 0;
        id = 0;
    }

    public void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        FtcDashboard.getInstance().getTelemetry().addData("# AprilTags Detected", currentDetections.size());
        posAprilY = 0;
        posAprilX = 0;
        posAprilZ = 0;

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                posAprilY = detection.ftcPose.y;
                posAprilX = detection.ftcPose.x;
                posAprilZ = detection.ftcPose.z;
                id = detection.id;
                bearing = detection.ftcPose.bearing;
                range = detection.ftcPose.range;
                yaw = detection.ftcPose.yaw;
                FtcDashboard.getInstance().getTelemetry().addData("ID", detection.id);
                FtcDashboard.getInstance().getTelemetry().addLine(String.format("XYZ", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
            } else {
                FtcDashboard.getInstance().getTelemetry().addData("ID", detection.id);
                FtcDashboard.getInstance().getTelemetry().addLine(String.format("Center", detection.center.x, detection.center.y));
            }
        }
    }

}
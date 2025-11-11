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

public class AprilTag implements Initializable {

    private static final org.firstinspires.ftc.teamcode.main.opencv.AprilTag INSTANCE = new org.firstinspires.ftc.teamcode.main.opencv.AprilTag();
    private static final boolean USE_WEBCAM = true;
    public ElapsedTime runtime = new ElapsedTime();

    private AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    private GunControl gun;
    private TransferBall transfer;
    public double PosAprilX;
    public double PosAprilY;
    public double PosAprilZ;

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

    public static org.firstinspires.ftc.teamcode.main.opencv.AprilTag getInstance() {
        return INSTANCE;
    }

    public double getPosAprilX() {
        return PosAprilX;
    }

    public double getPosAprilY() {
        return PosAprilY;
    }

    public double getPosAprilZ() {
        return PosAprilZ;
    }
    public void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        FtcDashboard.getInstance().getTelemetry().addData("# AprilTags Detected", currentDetections.size());

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                PosAprilY = detection.ftcPose.y;
                PosAprilX = detection.ftcPose.x;
                PosAprilZ = detection.ftcPose.z;
                FtcDashboard.getInstance().getTelemetry().addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                FtcDashboard.getInstance().getTelemetry().addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//                FtcDashboard.getInstance().getTelemetry().addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
//                FtcDashboard.getInstance().getTelemetry().addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                FtcDashboard.getInstance().getTelemetry().addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                FtcDashboard.getInstance().getTelemetry().addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }

        FtcDashboard.getInstance().getTelemetry().addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        FtcDashboard.getInstance().getTelemetry().addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
//        FtcDashboard.getInstance().getTelemetry().addLine("RBE = Range, Bearing & Elevation");
    }

}
package org.firstinspires.ftc.teamcode.main.opencv;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfPoint;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;

@Config
public class Vision implements Initializable {
    private static final org.firstinspires.ftc.teamcode.main.opencv.Vision INSTANCE = new org.firstinspires.ftc.teamcode.main.opencv.Vision();

    OpenCvCamera camera;
    private AprilTagProcessor aprilTag;

    private VisionPortal visionPortal;
    private WebcamName webcamName;
    public Telemetry telemetry;
    private boolean isInitialized = false;

    public static org.firstinspires.ftc.teamcode.main.opencv.Vision getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        isInitialized  = true;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    public void startStreaming() {
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", "Error code: " + errorCode);
                telemetry.update();
            }
        });
        camera.setPipeline(new PipeLine());
        FtcDashboard.getInstance().startCameraStream(camera , 30);
    }

    public void stopStreaming() {
        camera.stopStreaming();
    }

}

@Config
class PipeLine extends OpenCvPipeline {
    private boolean viewportPaused = false;
    private OpenCvWebcam cam;
    Mat hsvMat = new Mat();
    Mat maskPurple = new Mat();
    Mat hierarchy = new Mat();
    List<MatOfPoint> contoursPurple = new ArrayList<>();
    public static final Scalar LOWER_PURPLE = new Scalar(120, 50, 50);
    public static final Scalar UPPER_PURPLE = new Scalar(160, 255, 255);

    public static final Scalar LOWER_GREEN = new Scalar(0, 50, 0);
    public static final Scalar UPPER_GREEN = new Scalar(0, 255, 0);
    public volatile double largestObjectCenterX = 0.0;
    public volatile double largestObjectCenterY = 0.0;
    public volatile String largestObjectType = "None";


//    @Override
//    public void init(Mat input) {
//    }

//    @Override
//    public Mat processFrame(Mat input) {
//        Imgproc.cvtColor(input, newImg, Imgproc.COLOR_RGB2YCrCb);
//        return input;
//    }

    @Override
    public Mat processFrame(Mat input)
    {
        contoursPurple.clear();
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsvMat, LOWER_PURPLE, UPPER_PURPLE, maskPurple);
        Imgproc.findContours(maskPurple, contoursPurple, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        if (!contoursPurple.isEmpty()) {
//            Imgproc.drawContours(input, contoursPurple, -1, new Scalar(0, 255, 0), 3);
        }
        MatOfPoint largestPurpleContour = findLargestContour(maskPurple);
        double purpleArea = (largestPurpleContour != null) ? Imgproc.contourArea(largestPurpleContour) : 0;
        largestObjectType = "Purple";
        processLargestObject(input, largestPurpleContour, new Scalar(255, 0, 255));
        hsvMat.release();
        FtcDashboard.getInstance().getTelemetry().addData("Purple Large X", largestObjectCenterX);
        FtcDashboard.getInstance().getTelemetry().addData("Purple Large Y", largestObjectCenterY);
        FtcDashboard.getInstance().getTelemetry().update();
        return input;
    }

    private MatOfPoint findLargestContour(Mat mask) {
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        MatOfPoint largestContour = null;

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestContour = contour;
            }
        }
        return largestContour;
    }
    private void processLargestObject(Mat input, MatOfPoint contour, Scalar color) {
        Imgproc.drawContours(input, java.util.Arrays.asList(contour), -1, color, 3);
        Rect boundingBox = Imgproc.boundingRect(contour);
        largestObjectCenterX = boundingBox.x + boundingBox.width / 2.0;
        largestObjectCenterY = boundingBox.y + boundingBox.height / 2.0;
    }

    @Override
    public void onViewportTapped()
    {
        viewportPaused = !viewportPaused;

        if(viewportPaused)
        {
            cam.pauseViewport();
        }
        else
        {
            cam.resumeViewport();
        }
    }
}

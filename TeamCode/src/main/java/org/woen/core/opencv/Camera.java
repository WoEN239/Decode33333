package org.woen.core.opencv;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

public class Camera {
    public OpenCvWebcam camera;
    private boolean isInitialized = false;


    private Camera() {
    }
    public void init(HardwareMap hardwareMap, String CameraName) {
        int MonitorId = hardwareMap.appContext.getResources().getIdentifier("MonitorId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, CameraName), MonitorId);
        isInitialized = true;
    }

    public void startStreaming(int width, int height, OpenCvCameraRotation rotate) {
        if (isInitialized) {
            camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    camera.startStreaming(width, height, rotate);
                }

                @Override
                public void onError(int errorCode) {

                }
            });
        }
    }

    public void stopStreaming() {
        camera.stopStreaming();
    }
}

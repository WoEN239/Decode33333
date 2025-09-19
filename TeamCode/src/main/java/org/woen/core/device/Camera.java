package org.woen.core.device;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public abstract class Camera {
    protected OpenCvWebcam camera;
    protected boolean isInitialized = false;
    protected final String name;


    protected Camera(String name) {
        this.name = name;
    }
    public void initialize(HardwareMap hardwareMap) {
        int MonitorId = hardwareMap.appContext.getResources().getIdentifier("MonitorId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, name), MonitorId);
        isInitialized = true;
    }

    public void startStreaming(int width, int height, OpenCvCameraRotation rotate) {
        if (isInitialized) {
            camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    camera.startStreaming(width, height, rotate);
                    cameraSetup();
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
    protected abstract void cameraSetup();

    public boolean isInitialized() {
        return isInitialized;
    }
}

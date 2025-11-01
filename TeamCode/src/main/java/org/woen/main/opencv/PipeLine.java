//package org.woen.main.opencv;
//
//import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//public class PipeLine extends OpenCvPipeline {
//    private OpenCvWebcam cam;
//    public Mat newImg = new Mat();
//
//    @Override
//    public void init(Mat input) {
//
//    }
//
//    @Override
//    public Mat processFrame(Mat input) {
//        Imgproc.cvtColor(input, newImg, Imgproc.COLOR_RGB2YCrCb);
//        return input;
//    }
//}
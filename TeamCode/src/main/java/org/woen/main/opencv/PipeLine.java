//package org.woen.main.opencv;
//
//import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.openftc.easyopencv.OpenCvWebcam;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//public class PipeLine extends OpenCvPipeline {
//    private boolean viewportPaused = false;
//    private OpenCvWebcam cam;
//    public Mat newImg = new Mat();
//
////    @Override
////    public void init(Mat input) {
////    }
//
////    @Override
////    public Mat processFrame(Mat input) {
////        Imgproc.cvtColor(input, newImg, Imgproc.COLOR_RGB2YCrCb);
////        return input;
////    }
//
//    @Override
//    public Mat processFrame(Mat input)
//    {
//        Imgproc.rectangle(
//                input,
//                new Point(
//                        input.cols() / 4,
//                        input.rows() / 4),
//                new Point(
//                        input.cols()*(3f/4f),
//                        input.rows()*(3f/4f)),
//                new Scalar(0, 255, 0), 4);
//
//
//        return input;
//    }
//
//    @Override
//    public void onViewportTapped()
//    {
//        viewportPaused = !viewportPaused;
//
//        if(viewportPaused)
//        {
//            webcam.pauseViewport();
//        }
//        else
//        {
//            webcam.resumeViewport();
//        }
//    }
//}
//}
package video;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class VideoStream {

    public VideoCapture getCapture() {
        return capture;
    }

    private VideoCapture capture = new VideoCapture();

    public VideoStream() {
        this.capture.open(0);
    }

    public Mat getFrame()
    {
        Mat frame = new Mat();
        // check if the capture is open
        if (this.capture.isOpened())
        {
            // read the current frame
            this.capture.read(frame);
            // if the frame is not empty, process it
            return  frame;
        }
        return null;
    }
}

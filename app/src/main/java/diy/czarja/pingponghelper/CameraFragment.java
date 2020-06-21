package diy.czarja.pingponghelper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.bytedeco.opencv.opencv_core.Mat;

public class CameraFragment extends Fragment implements CvCameraPreview.CvCameraViewListener {
    private CvCameraPreview cameraView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        cameraView = (CvCameraPreview) view.findViewById(R.id.camera_view);
        cameraView.setCvCameraViewListener(this);
        super.onViewCreated(view, savedInstanceState);
        //Log.d("mView", String.valueOf(cameraView.getMeasuredHeight()) + String.valueOf(cameraView.getMeasuredHeight()));
        ;
//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(CameraFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
//        absoluteFaceSize = (int) (width * 0.32f);
        Log.d("CameraView", String.valueOf(width) + String.valueOf(height));
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(Mat rgbaMat) {
        //Log.d("CameraView", String.valueOf(rgbaMat.rows()) + String.valueOf(rgbaMat.cols()));
//        if (faceDetector != null) {
//            Mat grayMat = new Mat(rgbaMat.rows(), rgbaMat.cols());
//
//            cvtColor(rgbaMat, grayMat, CV_BGR2GRAY);
//
//            RectVector faces = new RectVector();
//            faceDetector.detectMultiScale(grayMat, faces, 1.25f, 3, 1,
//                    new Size(absoluteFaceSize, absoluteFaceSize),
//                    new Size(4 * absoluteFaceSize, 4 * absoluteFaceSize));
//            if (faces.size() == 1) {
//                int x = faces.get(0).x();
//                int y = faces.get(0).y();
//                int w = faces.get(0).width();
//                int h = faces.get(0).height();
//                rectangle(rgbaMat, new Point(x, y), new Point(x + w, y + h), opencv_core.Scalar.GREEN, 2, LINE_8, 0);
//            }
//
//            grayMat.release();
//        }
        return rgbaMat;
    }
}
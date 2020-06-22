package diy.czarja.pingponghelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import org.bytedeco.opencv.opencv_core.Mat;

public class displayFragment extends Fragment implements CvCameraPreview.CvCameraViewListener, VideoPreview.VideoViewListener {
    private CvCameraPreview cameraView;
    private VideoPreview videoView;
    private boolean isVideoInput = false;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        isVideoInput = sharedPreferences.getBoolean("video_switch", false);
        if (isVideoInput) {
            return inflater.inflate(R.layout.fragment_video, container, false);
        } else{
            return inflater.inflate(R.layout.fragment_camera, container, false);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if(isVideoInput) {
            videoView = (VideoPreview) view.findViewById(R.id.video_view);
            videoView.setVideoViewListener(this);
        } else {
            cameraView = (CvCameraPreview) view.findViewById(R.id.camera_view);
            cameraView.setCvCameraViewListener(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
//        absoluteFaceSize = (int) (width * 0.32f);
        //Log.d("CameraView", String.valueOf(width) + String.valueOf(height));
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(Mat rgbaMat) {
//      在这里对图像进行处理
        return rgbaMat;
    }
}
package diy.czarja.pingponghelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameFilter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;

import java.io.File;
/*
* 这段代码企图实现一个和上面的CvCameraPreview相同的类，用来逐帧处理视频并输出
* 将视频按帧传递给OnCameraFrame接口
* @authored by Czrja
 */

public class VideoPreview extends SurfaceView implements SurfaceHolder.Callback {
    private VideoPreview.VideoViewListener listener;
    private Bitmap frameBitmap;
    private String LOGTAG = "VideoPreview";
    private FFmpegFrameFilter filter; // 保证视频大小、格式等
    File videoFile;
    private AndroidFrameConverter converterToBitmap = new AndroidFrameConverter();
    private OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();

    //Interface for frames processing
    public void setVideoViewListener(VideoPreview.VideoViewListener listener) {
        this.listener = listener;
    }

    //constructor
    public VideoPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // read video directory from settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String dir = sharedPreferences.getString("dir_text", "NotFound");
        Log.d(LOGTAG, dir);
        videoFile = new File(dir);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fetchFrames(videoFile);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void fetchFrames(File file) throws Exception {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        ff.setFormat("mp4");
        ff.start();
        Frame frame = null;
        Mat processedMat = null;
        int length = ff.getLengthInVideoFrames();
        Log.d(LOGTAG, "length=: " + length);
        initFilter(ff.getImageWidth(), ff.getImageHeight());
        filter.start();
        int i = 0;
        while (i < length) {
            i++;
            frame = ff.grabImage();
            filter.push(frame);
            frame = filter.pull();
            Mat mat = converterToMat.convert(frame);
            processedMat = listener.onCameraFrame(mat);
            frame = converterToMat.convert(processedMat);
            Bitmap bitmap = converterToBitmap.convert(frame);
            if(bitmap == null){
                break;
            }
            Canvas canvas = getHolder().lockCanvas();
            int width = canvas.getWidth();
            int height = bitmap.getHeight() * canvas.getWidth() / bitmap.getWidth();
            //canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(bitmap,
                    new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                    new Rect(0,
                            (canvas.getHeight() - height) / 2,
                            width,
                            (canvas.getHeight() - height) / 2 + height), null);
            getHolder().unlockCanvasAndPost(canvas);
        }
        filter.stop();
        ff.stop();
    }

    private void initFilter(int width, int height) {
        String transposeCode = "transpose=1";
        String formatCode = "format=pix_fmts=rgba";
        //String formatCode = "";
        filter = new FFmpegFrameFilter(transposeCode + "," + formatCode, width, height);
        //filter.setPixelFormat(avutil.AV_PIX_FMT_NV21);
        Log.i(LOGTAG, "filter initialize success with" + width + "x" + height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /* Do nothing. Wait until surfaceChanged delivered */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.d(LOG_TAG, "surfaceDestroyed");
//        synchronized (syncObject) {
//            surfaceExist = false;
//            checkCurrentState();
//        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        if (this.surfaceHolder.getSurface() == null) {
//            Log.e(LOG_TAG, "surfaceChanged(): surfaceHolder is null, nothing to do.");
//            return;
//        }
//
//        synchronized (syncObject) {
//            if (!surfaceExist) {
//                surfaceExist = true;
//                checkCurrentState();
//            } else {
//                /** Surface changed. We need to stop camera and restart with new parameters */
//                /* Pretend that old surface has been destroyed */
//                surfaceExist = false;
//                checkCurrentState();
//                /* Now use new surface. Say we have it now */
//                surfaceExist = true;
//                checkCurrentState();
//            }
//        }
    }

    public interface VideoViewListener {
        /**
         * This method is invoked when camera preview has started. After this method is invoked
         * the frames will start to be delivered to client via the onCameraFrame() callback.
         *
         * @param width  -  the width of the frames that will be delivered
         * @param height - the height of the frames that will be delivered
         */
        public void onCameraViewStarted(int width, int height);

        /**
         * This method is invoked when camera preview has been stopped for some reason.
         * No frames will be delivered via onCameraFrame() callback after this method is called.
         */
        public void onCameraViewStopped();

        /**
         * This method is invoked when delivery of the frame needs to be done.
         * The returned values - is a modified frame which needs to be displayed on the screen.
         * TODO: pass the parameters specifying the format of the frame (BPP, YUV or RGB and etc)
         */
        public Mat onCameraFrame(Mat mat);
    }
}

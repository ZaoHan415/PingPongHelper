package diy.czarja.pingponghelper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

//public class VideoPreview extends SurfaceView implements SurfaceHolder.Callback {
//    private SurfaceHolder surfaceHolder;
//    private final String LOG_TAG = "mVideoPreview";
//    private final Object syncObject = new Object();
//    private boolean surfaceExist;
//    protected boolean enabled = true;
//    private static final int STOPPED = 0;
//    private static final int STARTED = 1;
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        /* Do nothing. Wait until surfaceChanged delivered */
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
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
//    }
//
//    private void checkCurrentState() {
//        Log.d(LOG_TAG, "call checkCurrentState");
//        int targetState;
//
//        if (enabled && surfaceExist && getVisibility() == VISIBLE) {
//            targetState = STARTED;
//        } else {
//            targetState = STOPPED;
//        }
//
//        if (targetState != state) {
//            /* The state change detected. Need to exit the current state and enter target state */
//            processExitState(state);
//            state = targetState;
//            processEnterState(state);
//        }
//    }
//}

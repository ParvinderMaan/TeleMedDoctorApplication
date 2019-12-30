package com.telemed.doctor.videocall;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.util.CustomAlertTextView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;


public class VideoCallFragment extends Fragment {
    private final String LOG_TAG = VideoCallFragment.class.getSimpleName();
    //--------------------------------------------------------------------------------------------------
    private static String API_KEY = "46482822";
    private static String SESSION_ID = "2_MX40NjQ4MjgyMn5-MTU3NzI3OTgwNDc3MX5kclhUYmIzUXhPTUpycnNTNkhaSjZaT0R-fg";   // generate manually for now..
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjQ4MjgyMiZzaWc9Mzc1NjEyNjlhYTJmOTMzZTE4MGZiMWE2NTUyNzI4Yzk5MWYwNjUxZTpzZXNzaW9uX2lkPTJfTVg0ME5qUTRNamd5TW41LU1UVTNOekkzT1Rnd05EYzNNWDVrY2xoVVltSXpVWGhQVFVweWNuTlROa2hhU2paYVQwUi1mZyZjcmVhdGVfdGltZT0xNTc3Mjc5ODUwJm5vbmNlPTAuOTg3MzAwMDcxMjk2MTcwMSZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTc3ODg0NjUwJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";         // generate manually for now..
    //--------------------------------------------------------------------------------------------------
    private static final int REQUEST_CODE_VIDEO_PERM = 124;
    //--------------------------------------------------------------------------------------------------
    private ImageButton ibtnGallery, ibtnDocument, ibtnCallControl, ibtnMuteControl;
    private AppCompatButton btnDeviceSetting;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private LinearLayout llPermission,llBottom;
    private RelativeLayout rlHeader;
    private VideoCallViewModel mViewModel;
    private HomeFragmentSelectedListener mFragmentListener;
    private GestureDetector mGestureDetector;
    //--------------------------------------------------------------------------------------------------
    private Session mSession;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    //--------------------------------------------------------------------------------------------------
    public static VideoCallFragment newInstance() {
        return new VideoCallFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_video_call, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VideoCallViewModel.class);
        initView(v);
        initListener(v);
        initObserver();
        initTokbox();
       //-------------------------------------------------------------------------------------------
        mViewModel.setProgress(false); // default
        mViewModel.setDeviceSettingVisited(false);// default
        mViewModel.setPermissionLayoutVisibility(false); // default
        mViewModel.setMainLayoutVisible(false); // default
        mViewModel.setTopBottomLayoutVisible(true); // default
        ibtnCallControl.setTag(0);
        //------------------------------------------------------------------------------------------
        if (!isRuntimePermGranted()) {
            mViewModel.setAllPermGranted(false);
            requestPermissions(new String[]{CAMERA, RECORD_AUDIO}, REQUEST_CODE_VIDEO_PERM);
        } else {
            mViewModel.setAllPermGranted(true);
            mViewModel.setMainLayoutVisible(true);
        }
        //------------------------------------------------------------------------------------------
     // getViewLifecycleOwner().getLifecycle().addObserver(new MyObserver());


    }

    private boolean isRuntimePermGranted() {
        int reqOne = ContextCompat.checkSelfPermission(requireActivity(), CAMERA);
        int reqTwo = ContextCompat.checkSelfPermission(requireActivity(), RECORD_AUDIO);
        return (reqOne & reqTwo) == PackageManager.PERMISSION_GRANTED;
    }


    /* Fragment lifecycle methods */
    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();

        if (mViewModel.getDeviceSettingVisitedStatusValue() && !mViewModel.getPermGrantedStatusValue()) {   // check perm
            if(isRuntimePermGranted()){
                mViewModel.setPermissionLayoutVisibility(false);
                mViewModel.setMainLayoutVisible(true);
                mViewModel.setAllPermGranted(true);
            }else {
                mViewModel.setPermissionLayoutVisibility(true);
                mViewModel.setMainLayoutVisible(false);
                mViewModel.setAllPermGranted(false);
            }
        }

        if (mSession != null) {
            mSession.onResume();
        }


    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "onPause");
        super.onPause();

        if (mSession != null) {
            mSession.onPause();
        }
    }

    private void initView(View v) {
//--------------------------------------------------------------------------------------------------
        mPublisherViewContainer = v.findViewById(R.id.publisher_container);
        mSubscriberViewContainer = v.findViewById(R.id.subscriber_container);
//--------------------------------------------------------------------------------------------------
        ibtnGallery = v.findViewById(R.id.ibtn_gallery);
        ibtnDocument = v.findViewById(R.id.ibtn_document);
        ibtnCallControl = v.findViewById(R.id.ibtn_call_control);
        ibtnMuteControl = v.findViewById(R.id.ibtn_mute_control);
        rlHeader= v.findViewById(R.id.rl_header);
        llBottom = v.findViewById(R.id.ll_bottom);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        llPermission = v.findViewById(R.id.ll_permission);
        btnDeviceSetting = v.findViewById(R.id.btn_device_setting);
        progressBar = v.findViewById(R.id.progress_bar);
//--------------------------------------------------------------------------------------------------
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener(View v) {
        ibtnDocument.setOnClickListener(mOnClickListener);
        ibtnCallControl.setOnClickListener(mOnClickListener);
        ibtnGallery.setOnClickListener(mOnClickListener);
        ibtnMuteControl.setOnClickListener(mOnClickListener);
        btnDeviceSetting.setOnClickListener(mOnClickListener);

        v.setOnTouchListener((v1, event) -> mGestureDetector.onTouchEvent(event));

        mGestureDetector = new GestureDetector(requireActivity().getApplicationContext(), new VideoCallGestureDetector());

    }

    @Override
    public void onDestroyView() {
        ibtnDocument.setOnClickListener(null);
        ibtnCallControl.setOnClickListener(null);
        ibtnGallery.setOnClickListener(null);
        ibtnMuteControl.setOnClickListener(null);
        btnDeviceSetting.setOnClickListener(null);
        super.onDestroyView();
    }


    //--------------------------------------------------------------------------------------------------
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.ibtn_gallery:
//                    if (mFragmentListener != null)
//                        mFragmentListener.showFragment("PatientGalleryFragment");
                      if(mFragmentListener!=null)
                          mFragmentListener.startActivity("SecondaryActivity", "PatientGalleryFragment");
                    break;
                case R.id.ibtn_document:
//                    if (mFragmentListener != null)
//                        mFragmentListener.showFragment("DoctorDocumentFragment");
                    if(mFragmentListener!=null)
                        mFragmentListener.startActivity("SecondaryActivity", "DoctorDocumentFragment");
                    break;
                case R.id.ibtn_call_control:
                    if (mSession == null) return;

                    // by default NO_CALL
                    if (((Integer) ibtnCallControl.getTag()) == 0 && mViewModel.getPermGrantedStatusValue()) {
                        mSession.connect(TOKEN);
                        ibtnCallControl.setTag(1);
                        ibtnCallControl.setBackgroundResource(R.drawable.ic_end_call);
                    } else {
                        mSession.disconnect();
                        ibtnCallControl.setTag(0);
                        ibtnCallControl.setBackgroundResource(R.drawable.ic_start_call);
                    }


//                    if(mFragmentListener !=null)
//                        mFragmentListener.showFragment("AppointmentSummaryFragment");

                    break;
                case R.id.ibtn_mute_control:

                    if (mPublisher == null) return;

                    // by default UN_MUTE
                    if (mPublisher.getPublishAudio()) {
                        mPublisher.setPublishAudio(false);
                        ibtnMuteControl.setBackgroundResource(R.drawable.ic_mute);
                    } else {
                        mPublisher.setPublishAudio(true);
                        ibtnMuteControl.setBackgroundResource(R.drawable.ic_unmute);
                    }

                    break;

                case R.id.btn_device_setting:
                    if (mFragmentListener != null) {
                        mViewModel.setDeviceSettingVisited(true);
                        mFragmentListener.startActivity("DeviceSettingActivity", null); // show Application Setting
                    }
                    break;
            }
        }
    };
    //--------------------------------------------------------------------------------------------------
    private Session.SessionListener mSessionListener = new Session.SessionListener() {
        @Override
        public void onConnected(Session session) {
            Log.i(LOG_TAG, "Session Connected");

            mPublisher = new Publisher.Builder(getActivity()).build();
            mPublisher.setPublisherListener(mPublisherListener);
            mPublisherViewContainer.addView(mPublisher.getView());

            if (mPublisher.getView() instanceof GLSurfaceView) {
                ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
            }

            mSession.publish(mPublisher);
        }

        @Override
        public void onDisconnected(Session session) {
            Log.i(LOG_TAG, "Session Disconnected");

        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {
            Log.i(LOG_TAG, "Stream Received");

            if (mSubscriber == null) {
                mSubscriber = new Subscriber.Builder(getActivity(), stream).build();
                mSession.subscribe(mSubscriber);
                mSubscriberViewContainer.addView(mSubscriber.getView());


            }
        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.i(LOG_TAG, "Stream Dropped");

            if (mSubscriber != null) {
                mSubscriber = null;
                mSubscriberViewContainer.removeAllViews();
            }
        }

        @Override
        public void onError(Session session, OpentokError opentokError) {
            Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());

        }
    };
    //--------------------------------------------------------------------------------------------------
    private PublisherKit.PublisherListener mPublisherListener = new PublisherKit.PublisherListener() {
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
            Log.i(LOG_TAG, "Publisher onStreamCreated");
        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
            Log.i(LOG_TAG, "Publisher onStreamDestroyed");
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {
            Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
        }
    };
//--------------------------------------------------------------------------------------------------

    private Session.ConnectionListener mConnectionListener = new Session.ConnectionListener() {
        @Override
        public void onConnectionCreated(Session session, Connection connection) {


        }

        @Override
        public void onConnectionDestroyed(Session session, Connection connection) {


        }
    };
//--------------------------------------------------------------------------------------------------

    private Session.ReconnectionListener mReconnectionListener = new Session.ReconnectionListener() {
        @Override
        public void onReconnecting(Session session) {

        }

        @Override
        public void onReconnected(Session session) {

        }
    };

//--------------------------------------------------------------------------------------------------

    private Session.StreamPropertiesListener mStreamPropertiesListener = new Session.StreamPropertiesListener() {
        @Override
        public void onStreamHasAudioChanged(Session session, Stream stream, boolean b) {

        }

        @Override
        public void onStreamHasVideoChanged(Session session, Stream stream, boolean b) {

        }

        @Override
        public void onStreamVideoDimensionsChanged(Session session, Stream stream, int i, int i1) {

        }

        @Override
        public void onStreamVideoTypeChanged(Session session, Stream stream, Stream.StreamVideoType streamVideoType) {

        }
    };

//--------------------------------------------------------------------------------------------------


    //--------------------------------------------------------------------------------------------------
    public class MyObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void connectListener() {
            //..........................
            Toast.makeText(getActivity(), "onResume..." + "Connect Listener", Toast.LENGTH_SHORT).show();

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void disconnectListener() {
            //..........................
            Toast.makeText(getActivity(), "onPause ..." + "Disconnect Listener", Toast.LENGTH_SHORT).show();

        }
    }

    private void initTokbox() {
        // initialize and connect to the session
        mSession = new Session.Builder(getActivity(), API_KEY, SESSION_ID).build();
        mSession.setSessionListener(mSessionListener);
        mSession.setConnectionListener(mConnectionListener);
        mSession.setReconnectionListener(mReconnectionListener);
        mSession.setStreamPropertiesListener(mStreamPropertiesListener);
        //            mSession.connect(TOKEN);

    }


    private void initObserver() {

        mViewModel.getPermGrantedStatus()
                .observe(getViewLifecycleOwner(), status -> {
                      if(status){ }
                });
    //----------------------------------------------------------------------------------------------
        mViewModel.getPermissionLayoutVisible().observe(getViewLifecycleOwner(),status -> {
            llPermission.setVisibility(status ? View.VISIBLE : View.GONE);
//            if(status){
//                ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(llPermission,View.ALPHA,0.0f,1.0f);
//                objectAnimator.setDuration(3000);
//                objectAnimator.addListener(mShowAnimatorListener);
//                objectAnimator.start();
//            }else {
//                ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(llPermission,View.ALPHA,1.0f,0.0f);
//                objectAnimator.setDuration(3000);
//                objectAnimator.addListener(mHideAnimatorListener);
//                objectAnimator.start();
//
//            }

        });
    //----------------------------------------------------------------------------------------------
        mViewModel.getMainLayoutVisibility().observe(getViewLifecycleOwner(),status ->{
            rlHeader.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            llBottom.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            mPublisherViewContainer.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            mSubscriberViewContainer.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        });
    //----------------------------------------------------------------------------------------------

        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));
    //----------------------------------------------------------------------------------------------

        mViewModel.getTopBottomLayoutVisibility().observe(getViewLifecycleOwner(), status -> {
            rlHeader.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            llBottom.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_VIDEO_PERM && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {  // all permission OK...
            mViewModel.setAllPermGranted(true);
            mViewModel.setMainLayoutVisible(true);
            mViewModel.setPermissionLayoutVisibility(false);

        } else {  // atleast one is MISSING....
            mViewModel.setAllPermGranted(false);
            mViewModel.setPermissionLayoutVisibility(true);
            mViewModel.setMainLayoutVisible(false);
        }

    }


    private Animator.AnimatorListener mShowAnimatorListener=new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            llPermission.setVisibility(View.VISIBLE); }
        @Override
        public void onAnimationEnd(Animator animation) { }
        @Override
        public void onAnimationCancel(Animator animation) { }
        @Override
        public void onAnimationRepeat(Animator animation) { }
    };

    private Animator.AnimatorListener mHideAnimatorListener=new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) { }
        @Override
        public void onAnimationEnd(Animator animation) {
            llPermission.setVisibility(View.GONE); }
        @Override
        public void onAnimationCancel(Animator animation) { }
        @Override
        public void onAnimationRepeat(Animator animation) { }
    };


 // make it weak reference !!!
    private class VideoCallGestureDetector implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Gesture ", " onDown");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if(mViewModel.getMainLayoutVisibilityValue()) {
                if(mViewModel.getTopBottomLayoutVisibilityVal()){
                    mViewModel.setTopBottomLayoutVisible(false);
                }else {
                    mViewModel.setTopBottomLayoutVisible(true);
                }

                return true;
            }

            return false;

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapUp");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Gesture ", " onShowPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("Gesture ", " onDoubleTap");
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
          //  Log.d("Gesture ", " onDoubleTapEvent");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
           // Log.d("Gesture ", " onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.d("Gesture ", " onScroll");
            if (e1.getY() < e2.getY()) {
                Log.d("Gesture ", " Scroll Down");
            }
            if (e1.getY() > e2.getY()) {
                Log.d("Gesture ", " Scroll Up");
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() < e2.getX()) {
                Log.d("Gesture ", "Left to Right swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
            }
            if (e1.getX() > e2.getX()) {
                Log.d("Gesture ", "Right to Left swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
            }
            if (e1.getY() < e2.getY()) {
                Log.d("Gesture ", "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            if (e1.getY() > e2.getY()) {
                Log.d("Gesture ", "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            return false;


        }
    }


}

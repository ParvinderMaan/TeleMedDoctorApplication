package com.telemed.doctor.videocall;


import android.Manifest;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;



public class VideoCallFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private final String LOG_TAG = VideoCallFragment.class.getSimpleName();
//--------------------------------------------------------------------------------------------------
    private static String API_KEY = "46482822";
    private static String SESSION_ID = "2_MX40NjQ4MjgyMn5-MTU3NzI3OTgwNDc3MX5kclhUYmIzUXhPTUpycnNTNkhaSjZaT0R-fg";   // generate manually for now..
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjQ4MjgyMiZzaWc9Mzc1NjEyNjlhYTJmOTMzZTE4MGZiMWE2NTUyNzI4Yzk5MWYwNjUxZTpzZXNzaW9uX2lkPTJfTVg0ME5qUTRNamd5TW41LU1UVTNOekkzT1Rnd05EYzNNWDVrY2xoVVltSXpVWGhQVFVweWNuTlROa2hhU2paYVQwUi1mZyZjcmVhdGVfdGltZT0xNTc3Mjc5ODUwJm5vbmNlPTAuOTg3MzAwMDcxMjk2MTcwMSZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTc3ODg0NjUwJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";         // generate manually for now..
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
//--------------------------------------------------------------------------------------------------
    private ImageButton ibtnGallery, ibtnDocument, ibtnCallEnd, ibtnMuteControl;
    private CustomAlertTextView tvAlertView;
    private HomeFragmentSelectedListener mFragmentListener;
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
        return inflater.inflate(R.layout.fragment_video_call, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        requestPermissions();      // if not granted !!!

    }
    /* Fragment lifecycle methods */
    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();

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
        ibtnGallery=v.findViewById(R.id.ibtn_gallery);
        ibtnDocument=v.findViewById(R.id.ibtn_document);
        ibtnCallEnd=v.findViewById(R.id.ibtn_call_end);
        ibtnMuteControl=v.findViewById(R.id.ibtn_mute_control);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
//--------------------------------------------------------------------------------------------------


    }

    private void initListener() {
        ibtnDocument.setOnClickListener(mOnClickListener);
        ibtnCallEnd.setOnClickListener(mOnClickListener);
        ibtnGallery.setOnClickListener(mOnClickListener);
        ibtnMuteControl.setOnClickListener(mOnClickListener);
    }

    @Override
    public void onDestroyView() {
        ibtnDocument.setOnClickListener(null);
        ibtnCallEnd.setOnClickListener(null);
        ibtnGallery.setOnClickListener(null);
        ibtnMuteControl.setOnClickListener(null);
        super.onDestroyView();

    }




    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS };
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            // initialize and connect to the session
            mSession = new Session.Builder(getActivity(), API_KEY, SESSION_ID).build();
            mSession.setSessionListener(mSessionListener);
            mSession.connect(TOKEN);

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(LOG_TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(LOG_TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setRationale(getString(R.string.rationale_ask_again))
                    .setPositiveButton("Setting")
                    .setNegativeButton("Cancel")
                    .setRequestCode(RC_SETTINGS_SCREEN_PERM)
                    .build()
                    .show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


//--------------------------------------------------------------------------------------------------
    private View.OnClickListener mOnClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.ibtn_gallery:
                    if(mFragmentListener !=null)
                        mFragmentListener.showFragment("PatientGalleryFragment");
                    break;
                case R.id.ibtn_document:
                    if(mFragmentListener !=null)
                        mFragmentListener.showFragment("DoctorDocumentFragment");
                    break;
                case R.id.ibtn_call_end:
                    if (mSession != null) {
                        mSession.disconnect();
                    }

                    if(mFragmentListener !=null)
                        mFragmentListener.showFragment("AppointmentSummaryFragment");
                    break;
                case R.id.ibtn_mute_control:

                    Toast.makeText(getActivity(), "wait for the moment", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
//--------------------------------------------------------------------------------------------------
    private Session.SessionListener mSessionListener=new Session.SessionListener() {
        @Override
        public void onConnected(Session session) {
            Log.i(LOG_TAG, "Session Connected");

            mPublisher = new Publisher.Builder(getActivity()).build();
            mPublisher.setPublisherListener(mPublisherListener);
            mPublisherViewContainer.addView(mPublisher.getView());

            if (mPublisher.getView() instanceof GLSurfaceView){
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
    private PublisherKit.PublisherListener mPublisherListener=new PublisherKit.PublisherListener() {
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





}

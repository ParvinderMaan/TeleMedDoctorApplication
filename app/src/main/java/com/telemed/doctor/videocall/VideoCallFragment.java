package com.telemed.doctor.videocall;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;
import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.consult.model.Appointment;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.network.WebUrl;
import com.telemed.doctor.util.CustomAlertTextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;


public class VideoCallFragment extends Fragment {
    private final String TAG = VideoCallFragment.class.getSimpleName();
    //--------------------------------------------------------------------------------------------------
    private static String API_KEY = "46482822";
    private static String SESSION_ID = "1_MX40NjQ4MjgyMn5-MTU3Nzk0Mjg1NDg3Nn5iUWRMaHpHQ3JZSXdUWUxRTGZ4cXI1c3F-fg";   // generate manually for now..
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjQ4MjgyMiZzaWc9MWE2OWVjYzFjMGVhNGUzMTQ3MmM2OTA1NWJlMzAzM2FiYTkzNGM0ZTpzZXNzaW9uX2lkPTFfTVg0ME5qUTRNamd5TW41LU1UVTNOemswTWpnMU5EZzNObjVpVVdSTWFIcEhRM0paU1hkVVdVeFJUR1o0Y1hJMWMzRi1mZyZjcmVhdGVfdGltZT0xNTc3OTQyOTA2Jm5vbmNlPTAuNTY0MjE4NzQyMzcwMTg3JnJvbGU9cHVibGlzaGVyJmV4cGlyZV90aW1lPTE1ODA1MzQ5MDUmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0=";         // generate manually for now..
    //--------------------------------------------------------------------------------------------------
    private static final int REQUEST_CODE_VIDEO_PERM = 124;
    //--------------------------------------------------------------------------------------------------
    private ImageButton ibtnGallery, ibtnDocument, ibtnCallControl, ibtnMuteControl;
    private CircleImageView civSubscriberPic;
    private TextView tvSubscriberName,tvUserWaitingPrompt;
    private TextView tvCallDuration;
    private AppCompatButton btnDeviceSetting;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private LinearLayout llPermission, llBottom;
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
    private VideoCallGestureDetector mVideoCallGestureDetector;
    //--------------------------------------------------------------------------------------------------
    private String mUsrProfilePic, mUsrName;

    public static VideoCallFragment newInstance(Object payload) {
        VideoCallFragment fragment = new VideoCallFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (Appointment) payload); // SignUpIResponse.Data
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // collect our intent
        if (getArguments() != null) {
            Appointment objInfo = getArguments().getParcelable("KEY_");
            if (objInfo != null) API_KEY = String.valueOf(objInfo.getApiKey());
            if (objInfo != null) SESSION_ID = objInfo.getSessionId();
            if (objInfo != null) TOKEN = objInfo.getToken();
            if (objInfo != null) mUsrName = objInfo.getFirstName() + " " + objInfo.getLastName();
            if (objInfo != null) mUsrProfilePic = objInfo.getProfilePic();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
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
        initDefaultAttribute();
        //-------------------------------------------------------------------------------------------
        tvSubscriberName.setText(mUsrName != null ? mUsrName : "");
        if (mUsrProfilePic != null && !mUsrProfilePic.isEmpty()) {
            Picasso.get().load(WebUrl.IMAGE_URL + mUsrProfilePic)
                    .placeholder(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
                    .fit()
                    .centerCrop()
                    .into(civSubscriberPic);
        }
        //-------------------------------------------------------------------------------------------
        if (!isRuntimePermGranted()) {
            mViewModel.setAllPermGranted(false);
            requestPermissions(new String[]{CAMERA, RECORD_AUDIO}, REQUEST_CODE_VIDEO_PERM);
        } else {
            mViewModel.setAllPermGranted(true);
            mViewModel.setMainLayoutVisible(true);
            mOnClickListener.onClick(ibtnCallControl);
        }
        //------------------------------------------------------------------------------------------
        // getViewLifecycleOwner().getLifecycle().addObserver(new MyObserver());

}

    private void initDefaultAttribute() {
        mViewModel.setProgress(false); // default
        mViewModel.setDeviceSettingVisited(false);// default
        mViewModel.setPermissionLayoutVisibility(false); // default
        mViewModel.setMainLayoutVisible(false); // default
        mViewModel.setTopBottomLayoutVisible(true); // default
        mViewModel.setCallWaitingStatus(false); // default (although call has not connected yet!)
        ibtnCallControl.setTag(0); // by default
    }

    private boolean isRuntimePermGranted() {
        int reqOne = ContextCompat.checkSelfPermission(requireActivity(), CAMERA);
        int reqTwo = ContextCompat.checkSelfPermission(requireActivity(), RECORD_AUDIO);
        return (reqOne & reqTwo) == PackageManager.PERMISSION_GRANTED;
    }


    /* Fragment lifecycle methods */
    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

        if (mViewModel.getDeviceSettingVisitedStatusValue() && !mViewModel.getPermGrantedStatusValue()) {   // check perm
            if (isRuntimePermGranted()) {
                mViewModel.setPermissionLayoutVisibility(false);
                mViewModel.setMainLayoutVisible(true);
                mViewModel.setAllPermGranted(true);
            } else {
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
        Log.d(TAG, "onPause");
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
        rlHeader = v.findViewById(R.id.rl_header);
        llBottom = v.findViewById(R.id.ll_bottom);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        llPermission = v.findViewById(R.id.ll_top);
        tvCallDuration = v.findViewById(R.id.tv_call_duration);
        btnDeviceSetting = v.findViewById(R.id.btn_device_setting);
        progressBar = v.findViewById(R.id.progress_bar);
        tvSubscriberName = v.findViewById(R.id.tv_subscriber_name);
        civSubscriberPic = v.findViewById(R.id.civ_subscriber_pic);
        tvUserWaitingPrompt = v.findViewById(R.id.tv_user_waiting);

//--------------------------------------------------------------------------------------------------

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener(View v) {
        ibtnDocument.setOnClickListener(mOnClickListener);
        ibtnCallControl.setOnClickListener(mOnClickListener);
        ibtnGallery.setOnClickListener(mOnClickListener);
        ibtnMuteControl.setOnClickListener(mOnClickListener);
        btnDeviceSetting.setOnClickListener(mOnClickListener);
        mPublisherViewContainer.setOnClickListener(mOnClickListener);  // changed
        v.setOnTouchListener((v1, event) -> mGestureDetector.onTouchEvent(event));
        mVideoCallGestureDetector = new VideoCallGestureDetector() {
            @Override
            boolean onSingleTap(MotionEvent e) {

                if (mViewModel.getMainLayoutVisibilityValue()) {
                    if (mViewModel.getTopBottomLayoutVisibilityVal()) {
                        mViewModel.setTopBottomLayoutVisible(false);
                    } else {
                        mViewModel.setTopBottomLayoutVisible(true);
                    }
                    return true;
                }


                return false;
            }
        };
        mGestureDetector = new GestureDetector(requireActivity().getApplicationContext(), mVideoCallGestureDetector);

    }

    @Override
    public void onDestroyView() {
        ibtnDocument.setOnClickListener(null);
        ibtnCallControl.setOnClickListener(null);
        ibtnGallery.setOnClickListener(null);
        ibtnMuteControl.setOnClickListener(null);
        btnDeviceSetting.setOnClickListener(null);
        mGestureDetector = null;
        super.onDestroyView();
    }


    //--------------------------------------------------------------------------------------------------
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.ibtn_gallery:
                    ObjectAnimator scaleAnimX = ObjectAnimator.ofFloat(ibtnGallery, "scaleX", 0.2f, 1f);
                    scaleAnimX.setDuration(300);
                    scaleAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    scaleAnimX.start();
                    ObjectAnimator scaleAnimY = ObjectAnimator.ofFloat(ibtnGallery, "scaleY", 0.2f, 1f);
                    scaleAnimY.setDuration(300);
                    scaleAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(scaleAnimX).with(scaleAnimY);
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            ibtnGallery.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ibtnGallery.setEnabled(true);
                            if (mFragmentListener != null)
                                mFragmentListener.startActivity("SecondaryActivity", "PatientGalleryFragment");
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    animatorSet.start();

                    break;
                case R.id.ibtn_document:
                    ObjectAnimator scaleAnimXXX = ObjectAnimator.ofFloat(ibtnDocument, "scaleX", 0.1f, 1f);
                    scaleAnimXXX.setDuration(300);
                    scaleAnimXXX.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    scaleAnimXXX.start();
                    ObjectAnimator scaleAnimYYY = ObjectAnimator.ofFloat(ibtnDocument, "scaleY", 0.1f, 1f);
                    scaleAnimYYY.setDuration(300);
                    scaleAnimYYY.setInterpolator(OVERSHOOT_INTERPOLATOR);

                    AnimatorSet animatorSettt = new AnimatorSet();
                    animatorSettt.play(scaleAnimXXX).with(scaleAnimYYY);
                    animatorSettt.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            ibtnDocument.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ibtnDocument.setEnabled(true);
                            if (mFragmentListener != null)
                                mFragmentListener.startActivity("SecondaryActivity", "DoctorDocumentFragment");
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    animatorSettt.start();

                    break;
                case R.id.ibtn_call_control:
                    if (mSession == null) return;

                    ObjectAnimator scaleAnimO = ObjectAnimator.ofFloat(ibtnCallControl, "scaleX", 0.1f, 1f);
                    scaleAnimO.setDuration(300);
                    scaleAnimO.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    scaleAnimO.start();
                    ObjectAnimator scaleAnimOO = ObjectAnimator.ofFloat(ibtnCallControl, "scaleY", 0.1f, 1f);
                    scaleAnimOO.setDuration(300);
                    scaleAnimOO.setInterpolator(OVERSHOOT_INTERPOLATOR);

                    AnimatorSet animatorSetO = new AnimatorSet();
                    animatorSetO.play(scaleAnimO).with(scaleAnimOO);
                    animatorSetO.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            ibtnCallControl.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ibtnCallControl.setEnabled(true);
                            // by default NO_CALL
                            if (((Integer) ibtnCallControl.getTag()) == 0 && mViewModel.getPermGrantedStatusValue()) {
                                mViewModel.setProgress(true);
                                mSession.connect(TOKEN);
                                ibtnCallControl.setTag(1);
                                ibtnCallControl.setImageResource(R.drawable.ic_end_call);
                            } else {
                                mViewModel.setProgress(false);
                                mSession.disconnect();
                                ibtnCallControl.setTag(0);
                                ibtnCallControl.setImageResource(R.drawable.ic_start_call);
                                if (mFragmentListener != null) {
                                    mFragmentListener.popTopMostFragment();
                                    mFragmentListener.startActivity("SecondaryActivity", "AppointmentSummaryFragment");
                                }


                            }

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    animatorSetO.start();

//                    if(mFragmentListener !=null)
//                        mFragmentListener.showFragment("AppointmentSummaryFragment");

                    break;
                case R.id.ibtn_mute_control:
                    if (mPublisher == null) return;
                    ObjectAnimator scaleAnimXX = ObjectAnimator.ofFloat(ibtnMuteControl, "scaleX", 0.2f, 1f);
                    scaleAnimXX.setDuration(300);
                    scaleAnimXX.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    scaleAnimXX.start();
                    ObjectAnimator scaleAnimYY = ObjectAnimator.ofFloat(ibtnMuteControl, "scaleY", 0.2f, 1f);
                    scaleAnimYY.setDuration(300);
                    scaleAnimYY.setInterpolator(OVERSHOOT_INTERPOLATOR);

                    AnimatorSet animatorSett = new AnimatorSet();
                    animatorSett.play(scaleAnimXX).with(scaleAnimYY);
                    animatorSett.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            ibtnMuteControl.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ibtnMuteControl.setEnabled(true);
                            // by default UN_MUTE
                            if (mPublisher.getPublishAudio()) {
                                mPublisher.setPublishAudio(false);
                                ibtnMuteControl.setImageResource(R.drawable.ic_mute);
                            } else {
                                mPublisher.setPublishAudio(true);
                                ibtnMuteControl.setImageResource(R.drawable.ic_unmute);
                            }

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animatorSett.start();

                    break;
                case R.id.publisher_container:
                    if (mPublisher != null)
                        mPublisher.cycleCamera();

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
            Log.i(TAG, "Session Connected");
            tvAlertView.showTopAlert("Session Connected");
            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            mViewModel.setCallWaitingStatus(true);
            mViewModel.setProgress(false);
            mTimerManager = new TimerManager(1800000, 1000);
            mTimerManager.setWeakReference(VideoCallFragment.this);
            mTimerManager.start();


    //--------------------------------------------------------------------------------------
//          mPublisher = new Publisher.Builder(getActivity()).build(); changed by PM.
//            mPublisher.setPublisherListener(mPublisherListener);
//            mPublisherViewContainer.addView(mPublisher.getView());
//
//            if (mPublisher.getView() instanceof GLSurfaceView) {
//                ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
//            }
   //-----------------------------------------------------------------------------------------
            mSession.publish(mPublisher);

  //        mSubscriberViewContainer.setBackgroundResource(R.drawable.img_call_waiting);
            shrinkPublisherView();
        }

        @Override
        public void onDisconnected(Session session) {
            Log.i(TAG, "Session Disconnected");
            tvAlertView.showTopAlert("Session Disconnected");

            mTimerManager.cancel();

            tvCallDuration.setText("0:00");
//          mSession.unsubscribe(mSubscriber);
//          mSession.unpublish(mPublisher);
            mSubscriberViewContainer.removeAllViews();
            mPublisherViewContainer.removeAllViews();
            mPublisherViewContainer.addView(mPublisher.getView());
        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {
            Log.i(TAG, "Stream Received");

            if (mSubscriber == null) {
                mSubscriber = new Subscriber.Builder(requireActivity(), stream).build();
                mSubscriber.setSubscriberListener(mSubscriberListener);

                mSession.subscribe(mSubscriber);
                if (mSubscriber.getView() instanceof GLSurfaceView) {
                    ((GLSurfaceView) mSubscriber.getView()).setZOrderOnTop(false);
                }
                mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

                mSubscriberViewContainer.addView(mSubscriber.getView());

            }
               mViewModel.setCallWaitingStatus(false);
            //  mSubscriberViewContainer.setBackgroundResource(0);
        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.i(TAG, "Stream Dropped");

            if (mSubscriber != null) {
                mSubscriber = null;
                mSubscriberViewContainer.removeAllViews();
                mViewModel.setCallWaitingStatus(true);
            }

            // mSubscriberViewContainer.setBackgroundResource(R.drawable.img_call_waiting);
        }

        @Override
        public void onError(Session session, OpentokError opentokError) {
            mViewModel.setProgress(false);
            tvAlertView.showTopAlert(opentokError.getMessage());
            Log.e(TAG, "Session error: " + opentokError.getMessage());

        }
    };
    //--------------------------------------------------------------------------------------------------
    private PublisherKit.PublisherListener mPublisherListener = new PublisherKit.PublisherListener() {
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
            Log.i(TAG, "Publisher onStreamCreated");
        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
            Log.i(TAG, "Publisher onStreamDestroyed");
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {
            Log.e(TAG, "Publisher error: " + opentokError.getMessage());
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
            mViewModel.setProgress(true);
        }

        @Override
        public void onReconnected(Session session) {
            mViewModel.setProgress(false);

        }
    };

//--------------------------------------------------------------------------------------------------

    private Session.StreamPropertiesListener mStreamPropertiesListener = new Session.StreamPropertiesListener() {
        @Override
        public void onStreamHasAudioChanged(Session session, Stream stream, boolean hasAudio) {

            if (hasAudio) {
                ibtnMuteControl.setImageResource(R.drawable.ic_unmute);
            } else {
                ibtnMuteControl.setImageResource(R.drawable.ic_mute);
            }

        }

        @Override
        public void onStreamHasVideoChanged(Session session, Stream stream, boolean hasVideo) {
            if (hasVideo) {

            }
        }

        @Override
        public void onStreamVideoDimensionsChanged(Session session, Stream stream, int width, int height) {

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
            Toast.makeText(requireActivity(), "onResume..." + "Connect Listener", Toast.LENGTH_SHORT).show();

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void disconnectListener() {
            //..........................
            Toast.makeText(requireActivity(), "onPause ..." + "Disconnect Listener", Toast.LENGTH_SHORT).show();

        }
    }

    private void initTokbox() {
        // initialize and connect to the session
        mSession = new Session.Builder(requireActivity(), API_KEY, SESSION_ID).build();
        mSession.setSessionListener(mSessionListener);
        mSession.setConnectionListener(mConnectionListener);
        mSession.setReconnectionListener(mReconnectionListener);
        mSession.setStreamPropertiesListener(mStreamPropertiesListener);
        //    mSession.connect(TOKEN);

        mPublisher = new Publisher.Builder(requireActivity()).build();
        mPublisher.setPublisherListener(mPublisherListener);
        mPublisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
        mPublisherViewContainer.addView(mPublisher.getView());
//        if (mPublisher.getView() instanceof GLSurfaceView) {
//            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(false);
//        }

        mPublisher.startPreview();

    }


    private void initObserver() {

//        mViewModel.getPermGrantedStatus()
//                .observe(getViewLifecycleOwner(), status -> {
//                    if (status) {}
//                });
        //----------------------------------------------------------------------------------------------
        mViewModel.getPermissionLayoutVisible().observe(getViewLifecycleOwner(), status -> {
            llPermission.setVisibility(status ? View.VISIBLE : View.GONE);
        });
        //----------------------------------------------------------------------------------------------
        mViewModel.getMainLayoutVisibility().observe(getViewLifecycleOwner(), status -> {
            rlHeader.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            llBottom.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            mPublisherViewContainer.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            mSubscriberViewContainer.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        });
        //----------------------------------------------------------------------------------------------
        mViewModel.getCallWaitingStatus().observe(getViewLifecycleOwner(), status->{
            tvUserWaitingPrompt.setVisibility(status ? View.VISIBLE : View.GONE);

        });

        //----------------------------------------------------------------------------------------------

        mViewModel.getProgress().observe(getViewLifecycleOwner(), isLoading ->
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));
        //----------------------------------------------------------------------------------------------
        mViewModel.getTopBottomLayoutVisibility().observe(getViewLifecycleOwner(), status -> {
//            rlHeader.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
//            llBottom.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
            if (status) { // show
                ObjectAnimator objectAnimY = ObjectAnimator.ofFloat(rlHeader, "y", 0.0f);
                objectAnimY.setDuration(200);
                objectAnimY.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mVideoCallGestureDetector.setGestureEnable(false);
                        rlHeader.setVisibility(View.VISIBLE);
                        llBottom.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mVideoCallGestureDetector.setGestureEnable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimY.start();
            } else { // hide
                ObjectAnimator objectAnimY = ObjectAnimator.ofFloat(rlHeader, "y", (float) -rlHeader.getHeight());
                objectAnimY.setDuration(200);
                objectAnimY.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mVideoCallGestureDetector.setGestureEnable(false);
                        llBottom.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mVideoCallGestureDetector.setGestureEnable(true);
                        rlHeader.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimY.start();
            }

        });
        //----------------------------------------------------------------------------------------------

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_VIDEO_PERM && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {  // all permission OK...
            mViewModel.setAllPermGranted(true);
            mViewModel.setMainLayoutVisible(true);
            mViewModel.setPermissionLayoutVisibility(false);
            mOnClickListener.onClick(ibtnCallControl);

        } else {  // atleast one is MISSING....
            mViewModel.setAllPermGranted(false);
            mViewModel.setPermissionLayoutVisibility(true);
            mViewModel.setMainLayoutVisible(false);

//            TimerManager c = new TimerManager();
//            System.out.println();
        }

    }
  /*
<!--android:layout_gravity="bottom|end"-->
<!--android:layout_marginBottom="@dimen/_16sdp"-->
<!--android:background="@color/colorBlue"-->
<!--android:padding="@dimen/_2sdp" />-->
*/

    public void shrinkPublisherView(){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)getResources().getDimension(R.dimen._90sdp),
                (int)getResources().getDimension(R.dimen._120sdp));
        params.addRule(RelativeLayout.ABOVE,llBottom.getId());
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
//      params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.rightMargin= (int) getResources().getDimension(R.dimen._10sdp);
        params.leftMargin= (int) getResources().getDimension(R.dimen._10sdp);
//      params.bottomMargin=(int) getResources().getDimension(R.dimen._10sdp);
        mPublisherViewContainer.setPadding((int)getResources().getDimension(R.dimen._2sdp),(int)getResources().getDimension(R.dimen._2sdp),
                (int)getResources().getDimension(R.dimen._2sdp),(int)getResources().getDimension(R.dimen._2sdp));
         mPublisherViewContainer.setBackgroundColor(getResources().getColor(R.color.colorBlue));
         mPublisherViewContainer.setLayoutParams(params);

    }


    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);


    private static class TimerManager extends CountDownTimer {
        private WeakReference<VideoCallFragment> mTimerReference;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimerManager(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        void setWeakReference(VideoCallFragment anotherClass) {
            mTimerReference = new WeakReference<>(anotherClass);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            int min = (int) (millisUntilFinished / (60 * 1000));
            int sec = (int) (millisUntilFinished / 1000 % 60);

            String show = String.format(Locale.getDefault(),"%02d", min) + ":" + String.format(Locale.getDefault(),"%02d", sec);
            ((VideoCallFragment)mTimerReference.get()).tvCallDuration.setText(show);
        }

        @Override
        public void onFinish() {
            ((VideoCallFragment)mTimerReference.get()).tvCallDuration.setText("00:00");
        }





    }

    private TimerManager mTimerManager;



    private SubscriberKit.SubscriberListener mSubscriberListener = new SubscriberKit.SubscriberListener() {
        @Override
        public void onConnected(SubscriberKit subscriberKit) {
            Log.e(TAG,"Subscriber onConnected");

            shrinkPublisherView();

        }

        @Override
        public void onDisconnected(SubscriberKit subscriberKit) {
            Log.e(TAG, "Subscriber onDisconnected");
        }

        @Override
        public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {

        }
    };
}
/*


========================================================
2019-12-30 19:26:47.072 27096-27096/com.telemed.doctor I/VideoCallFragment: Session Connected
2019-12-30 19:26:48.510 27096-27096/com.telemed.doctor I/VideoCallFragment: Publisher onStreamCreated

========================================================
2019-12-30 19:27:20.979 27096-27096/com.telemed.doctor I/VideoCallFragment: Publisher onStreamDestroyed
2019-12-30 19:27:20.980 27096-27096/com.telemed.doctor I/VideoCallFragment: Session Disconnected

========================================================
 */
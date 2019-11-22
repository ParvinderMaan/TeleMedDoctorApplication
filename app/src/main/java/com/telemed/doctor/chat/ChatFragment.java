package com.telemed.doctor.chat;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.TimeUtil;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {


    private ImageButton ibtnMenu;
    private PopupMenu mPopupMenu;
    private RecyclerView rvChat;
    private LinearLayoutManager mLLayoutManager;
    private ChatAdapter mChatAdapter;
    private HomeFragmentSelectedListener mFragmentListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChatAdapter.setUser("1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        ibtnMenu=v.findViewById(R.id.ibtn_menu);
        ibtnMenu.setOnClickListener(v1 -> {
            mPopupMenu.show();
        });


        initPopUpMenu();
        initRecyclerView(v);
        loadDummyData();

    }

    private void initPopUpMenu() {
        mPopupMenu = new PopupMenu(getActivity(), ibtnMenu);
        mPopupMenu.inflate(R.menu.menu_chat);
        mPopupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_gallery:
                    if(mFragmentListener !=null) mFragmentListener.showFragment("PatientGalleryFragment");

                    break;
                case R.id.menu_medical_record:
                    if(mFragmentListener !=null) mFragmentListener.showFragment("MedicalRecordFragment");

                    break;
                case R.id.menu_delete:
                      makeToast("deleting .....");
                    break;
            }
            return true;
        });

    }

    private void initRecyclerView(View v) {
        rvChat = v.findViewById(R.id.rv_chat);
        mLLayoutManager = new LinearLayoutManager(getActivity());
        mLLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLLayoutManager.setReverseLayout(true);
        mLLayoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(mLLayoutManager);
        mChatAdapter=new ChatAdapter();
        rvChat.setAdapter(mChatAdapter);




        //setting up our OnScrollListener
//      rvChat.addOnScrollListener(mScrollListener);

    }

    private void loadDummyData() {

        ChatModel chatModelOne=new ChatModel();
        chatModelOne.setChatId("1");
        chatModelOne.setMessage("Hi How are u ?");
        chatModelOne.setKey("11");
        chatModelOne.setMediaUrl("");
        chatModelOne.setMessageId("1");
        chatModelOne.setTimeStamp(TimeUtil.now);
        chatModelOne.setMessageType("");
        chatModelOne.setSelected(true);
        chatModelOne.setSenderId("1");

        ChatModel chatModelTwo=new ChatModel();
        chatModelTwo.setChatId("2");
        chatModelTwo.setMessage("I hope you are good !");
        chatModelTwo.setKey("12");
        chatModelTwo.setMediaUrl("");
        chatModelTwo.setMessageId("2");
        chatModelTwo.setTimeStamp(TimeUtil.now);
        chatModelTwo.setMessageType("");
        chatModelTwo.setSelected(true);
        chatModelTwo.setSenderId("2");

        ChatModel chatModelThree=new ChatModel();
        chatModelThree.setChatId("3");
        chatModelThree.setMessage("I hope you are good !");
        chatModelThree.setKey("13");
        chatModelThree.setMediaUrl("");
        chatModelThree.setMessageId("3");
        chatModelThree.setTimeStamp(TimeUtil.now);
        chatModelThree.setMessageType("");
        chatModelThree.setSelected(true);
        chatModelThree.setSenderId("2");


        ChatModel chatModelFour=new ChatModel();
        chatModelFour.setChatId("4");
        chatModelFour.setMessage("I like it");
        chatModelFour.setKey("14");
        chatModelFour.setMediaUrl("");
        chatModelFour.setMessageId("4");
        chatModelFour.setTimeStamp(TimeUtil.now);
        chatModelFour.setMessageType("");
        chatModelFour.setSelected(true);
        chatModelFour.setSenderId("1");



        mChatAdapter.add(chatModelOne);
        mChatAdapter.add(chatModelTwo);
        mChatAdapter.add(chatModelThree);
        mChatAdapter.add(chatModelFour);




    }


}

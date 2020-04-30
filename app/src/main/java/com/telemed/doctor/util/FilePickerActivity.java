package com.telemed.doctor.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseActivity;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.DividerListItemDecoration;
import com.vincent.filepicker.FolderListHelper;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.adapter.FolderListAdapter;
import com.vincent.filepicker.adapter.NormalFilePickAdapter;
import com.vincent.filepicker.adapter.OnSelectStateListener;
import com.vincent.filepicker.filter.FileFilter;
import com.vincent.filepicker.filter.callback.FilterResultCallback;
import com.vincent.filepicker.filter.entity.Directory;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.util.ArrayList;
import java.util.List;

public class FilePickerActivity extends BaseActivity {
    public static final int DEFAULT_MAX_NUMBER = 9;
    public static final String SUFFIX = "Suffix";
    protected boolean isNeedFolderList;
    FolderListHelper mFolderHelper = new FolderListHelper();
    private ArrayList<NormalFile> mSelectedList = new ArrayList<>();
    private List<Directory<NormalFile>> mAll;
    private String[] mSuffix;
    private int mMaxNumber;
    private RecyclerView mRecyclerView;
    private NormalFilePickAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView tvEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.FragmentThemeOne);
        hideStatusBar();
        setContentView(R.layout.activity_file_picker);

        mMaxNumber = getIntent().getIntExtra(Constant.MAX_NUMBER, DEFAULT_MAX_NUMBER);
        mSuffix = getIntent().getStringArrayExtra(SUFFIX);



        initView();
        loadData();

    }



    private void loadData() {
        FileFilter.getFiles(this, new FilterResultCallback<NormalFile>() {
            @Override
            public void onResult(List<Directory<NormalFile>> directories) {
                // Refresh folder list
                if (isNeedFolderList) {
                    ArrayList<Directory> list = new ArrayList<>();
                    Directory all = new Directory();
                    all.setName(getResources().getString(R.string.vw_all));
                    list.add(all);
                    list.addAll(directories);
                    mFolderHelper.fillData(list);

                }

                mAll = directories;
                refreshData(directories);
            }
        }, mSuffix);
    }

    private void refreshData(List<Directory<NormalFile>> directories) {
        mProgressBar.setVisibility(View.GONE);
        List<NormalFile> list = new ArrayList<>();
        for (Directory<NormalFile> directory : directories) {
            list.addAll(directory.getFiles());
        }

        for (NormalFile file : mSelectedList) {
            int index = list.indexOf(file);
            if (index != -1) {
                list.get(index).setSelected(true);
            }
        }
        mAdapter.refresh(list);

        if(list.isEmpty()){
           tvEmptyView.setVisibility(View.VISIBLE);
        }else {
            tvEmptyView.setVisibility(View.GONE);
        }

    }

    private void initView() {
        tvEmptyView = findViewById(R.id.tv_empty_view);
        mProgressBar = findViewById(R.id.pb_file_pick);
        mRecyclerView = findViewById(R.id.rv_file_pick);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this,
                LinearLayoutManager.VERTICAL, R.drawable.vw_divider_rv_file));
        mAdapter = new NormalFilePickAdapter(this, mMaxNumber);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnSelectStateListener(new OnSelectStateListener<NormalFile>() {
            @Override
            public void OnSelectStateChanged(boolean state, NormalFile file) {
                if (state) {
                    mSelectedList.add(file);
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(Constant.RESULT_PICK_FILE, mSelectedList);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.pb_file_pick);


        ImageButton ibtnClose = findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      finish();
            }
        });

    }



}

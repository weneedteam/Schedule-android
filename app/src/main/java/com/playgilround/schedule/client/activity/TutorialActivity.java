package com.playgilround.schedule.client.activity;

import android.widget.Button;

import com.playgilround.schedule.client.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 19-01-31
 * Tutorial Activity
 */
public class TutorialActivity extends AppCompatActivity {

    @BindView(R.id.recycler_image)
    RecyclerView mRecyclerView;

    @BindView(R.id.pager_indicator)
    IndefinitePagerIndicator indefinite;
    
    @BindView(R.id.btn_next)
    Button nextBtn;
}

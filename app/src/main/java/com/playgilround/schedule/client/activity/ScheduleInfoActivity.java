package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.playgilround.schedule.client.R;

/**
 * 18-12-27
 * Calendar 에서 날짜 클릭 시 스케줄 정보가 표시되는 액티비티
 */
public class ScheduleInfoActivity extends Activity implements View.OnClickListener {

    private Context context;

    private String date;
    private Button cancel;
    private TextView tvDate;
    private Activity activity;

    static final String TAG = ScheduleInfoActivity.class.getSimpleName();

    public static final int ADD_SCHEDULE = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_calendar);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        Log.d(TAG, "date -> "  + date);
        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(l -> finish());

        tvDate = findViewById(R.id.tv_date);
        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";
        tvDate.setText(strDate);

        findViewById(R.id.ivAddBtn).setOnClickListener(this);
    }
    /*public ScheduleInfoActivity(Activity activity, Context context, String date) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.date = date;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_calendar);

        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(l -> dismiss());

        tvDate = findViewById(R.id.tv_date);

        Log.d(TAG, "date -> " + date);
        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";
        tvDate.setText(strDate);

        findViewById(R.id.ivAddBtn).setOnClickListener(this);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddBtn:
                Intent intent = new Intent(this, AddScheduleActivity.class);
                intent.putExtra("date", date);
                startActivityForResult(intent, ADD_SCHEDULE);
//                AddScheduleActivity addFrag = new AddScheduleActivity();
//                FragmentManager fm = activity.getFragmentManager();
//
//                Bundle bundle = new Bundle(1);
//                bundle.putString("date", date);
//
//                addFrag.setArguments(bundle);
//                addFrag.show(fm, "TAG");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                switch (requestCode) {
                    case ADD_SCHEDULE:
                        //스케줄 입력이 완료됬을 때
                        Log.d(TAG, "Success SCHEDULE");
                        break;
                }
    }

}

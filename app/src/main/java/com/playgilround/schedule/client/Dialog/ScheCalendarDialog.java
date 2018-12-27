package com.playgilround.schedule.client.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.playgilround.schedule.client.R;

/**
 * 18-12-27
 * Calendar 에서 날짜 클릭 시 스케줄 정보가 표시되는 다이얼로그
 */
public class ScheCalendarDialog extends Dialog {

    private Context context;

    private String date;
    private Button cancel;
    private TextView tvDate;

    static final String TAG = ScheCalendarDialog.class.getSimpleName();

    public ScheCalendarDialog(Context context, String date) {
        super(context);
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
    }

}

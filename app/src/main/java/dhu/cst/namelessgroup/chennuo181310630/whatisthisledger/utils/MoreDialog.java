package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.AboutActivity;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.DayweekmonthActivity;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.HistoryActivity;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.MonthChartActivity;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.SettingActivity;

public class MoreDialog extends Dialog implements View.OnClickListener {
    Button aboutBtn,settingBtn,historyBtn,infoBtn,dwmBtn;
    ImageView errorIv;
    public MoreDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);
        initView();
    }

    private void initView() {
        aboutBtn=findViewById(R.id.dialog_more_btn_about);
        settingBtn=findViewById(R.id.dialog_more_btn_setting);
        historyBtn=findViewById(R.id.dialog_more_btn_record);
        infoBtn=findViewById(R.id.dialog_more_btn_info);
        errorIv=findViewById(R.id.dialog_more_iv_error);
        dwmBtn=findViewById(R.id.dialog_more_btn_dayweekmonth);
        aboutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        errorIv.setOnClickListener(this);
        dwmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.dialog_more_btn_about:
                intent.setClass(getContext(), AboutActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_btn_setting:
                intent.setClass(getContext(), SettingActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_btn_record:
                intent.setClass(getContext(), HistoryActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_btn_dayweekmonth:
                intent.setClass(getContext(), DayweekmonthActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_btn_info:
                intent.setClass(getContext(), MonthChartActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_iv_error:
                break;
        }
        cancel();
    }
//    设置Dialog尺寸与屏幕尺寸一致
    public void setDialogSize(){
//        获取当前窗口对象(对话框)
        Window window = getWindow();
//        获取窗口参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width=(int)(d.getWidth());
        wlp.gravity= Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }

}

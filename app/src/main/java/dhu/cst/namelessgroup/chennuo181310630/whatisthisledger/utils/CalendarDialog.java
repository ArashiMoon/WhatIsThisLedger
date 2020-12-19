package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.CalendarAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;

public class CalendarDialog extends Dialog implements View.OnClickListener {
    ImageView errorIv;
    GridView gv;
    LinearLayout hsvLayout;


    List<TextView>hsvViewList;
    List<Integer>yearList;

    int selectPos=-1;   //被选中的年份位置
    int selectMonth=-1; //被选中的月份位置
    private CalendarAdapter adapter;

    public interface OnRefreshListener{
        public void onRefresh(int selPos,int year,int month);
    }
    OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public CalendarDialog(@NonNull Context context,int selectPos,int selectMonth) {
        super(context);
        this.selectPos=selectPos;
        this.selectMonth=selectMonth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        gv=findViewById(R.id.dialog_calendar_gv);
        errorIv=findViewById(R.id.dialog_calendar_iv);
        hsvLayout=findViewById(R.id.dialog_calendar_linearlayout);
        errorIv.setOnClickListener(this);
//        向HorizontalScrollView中添加View
        addViewToLayout();
//        设置GridView显示
        initGridView();
//        设置GridView中每个item的点击事件
        setGVListener();
    }

    private void setGVListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos=position;
                adapter.notifyDataSetInvalidated(); //数据源未更新，仅仅更新了位置
//                获取到被选中的年份和月份
                int year=adapter.year;
                int month=position+1;
                onRefreshListener.onRefresh(selectPos,year,month);
                cancel();
            }
        });
    }

    private void initGridView() {
        int selYear = yearList.get(selectPos);
        adapter = new CalendarAdapter(getContext(), selYear);
        if (selectMonth==-1) {
            int month = Calendar.getInstance().get(Calendar.MONTH); //注意，这里没+1，因为考虑到adapter里面用到的也是位置
            adapter.selectPos=month;
        }else{
            adapter.selectPos=selectMonth-1;
        }
        gv.setAdapter(adapter);
    }

    //    向HorizontalScrollView中添加View
    private void addViewToLayout() {
        hsvViewList=new ArrayList<>();                  //管理加入hsv中LinearLayout的TextView
        yearList= DBManager.getYearListFromAccount();   //获取数据库中年份的个数
//        如果没有年份，就添加一个今年
        if (yearList.size()==0){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            yearList.add(year);
        }
//        遍历年份来添加TextView
        for (int i = 0; i < yearList.size(); i++) {
            int year=yearList.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_dialogcalendar_hsv, null);
            hsvLayout.addView(view);
            TextView hsvTv = view.findViewById(R.id.item_dialogcalendar_hsv_tv);
            hsvTv.setText(year+"");
            hsvViewList.add(hsvTv);
        }
        if (selectPos==-1) {
            selectPos=hsvViewList.size()-1; //默认被选中的是最后一个年份
        }
        changeTvbg(selectPos);  //控制年份的样式
        setHSVClickListener();  //设置点击事件监听
    }

//    给HSV中的TextView设置点击事件监听
    private void setHSVClickListener() {
        for (int i = 0; i < hsvViewList.size(); i++) {
            TextView textView = hsvViewList.get(i);
            final int pos=i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPos=pos;
                    changeTvbg(pos);
//                    获取被选中的年份，同时下面的GridView显示数据源也会发生变化
                    int year = yearList.get(selectPos);
                    adapter.setYear(year);
                }
            });
        }
    }

//    改变各年份的样式
    private void changeTvbg(int selectPos) {
        for (int i = 0; i < hsvViewList.size(); i++) {
            TextView textView = hsvViewList.get(i);
            textView.setBackgroundResource(R.drawable.dialog_btn_bg);
            textView.setTextColor(Color.BLACK);
        }
        TextView selectView=hsvViewList.get(selectPos);
        selectView.setBackgroundResource(R.drawable.main_editbtn_bg);
        selectView.setTextColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_calendar_iv:
                cancel();
                break;
        }
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
        wlp.gravity= Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}

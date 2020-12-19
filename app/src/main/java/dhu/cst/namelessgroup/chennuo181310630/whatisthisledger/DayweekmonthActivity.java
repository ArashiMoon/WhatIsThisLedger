package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.DayweekmonthVPAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_dayweekmonth.DayFragment;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_dayweekmonth.MonthFragment;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_dayweekmonth.WeekFragment;

public class DayweekmonthActivity extends AppCompatActivity {
    Button dayBtn,weekBtn,monthBtn;
    ViewPager dwmVp;
    private int year;
    private int month;
    private int dayOfMonth;
    private int dayOfWeek;
    List<Fragment>fraglist;
    private DayFragment dayFragment;
    private WeekFragment weekFragment;
    private MonthFragment monthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayweekmonth);
        initView();
        initTime();
        initFrag();
        setVPSelectListener();
    }

    private void setVPSelectListener() {
        dwmVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setButtonStyle(position);
            }
        });
    }

    private void initFrag() {
        fraglist=new ArrayList<>();
//        添加Fragment
        dayFragment = new DayFragment();
        weekFragment = new WeekFragment();
        monthFragment = new MonthFragment();
//        添加数据
        Bundle bundle=new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        bundle.putInt("dayOfMonth",dayOfMonth);
        bundle.putInt("dayOfWeek",dayOfWeek);
        dayFragment.setArguments(bundle);
        weekFragment.setArguments(bundle);
        monthFragment.setArguments(bundle);
//    添加Fragment至数据源
        fraglist.add(dayFragment);
        fraglist.add(weekFragment);
        fraglist.add(monthFragment);
//        使用适配器
        DayweekmonthVPAdapter dayweekmonthVPAdapter = new DayweekmonthVPAdapter(getSupportFragmentManager(), fraglist);
//        将Fragment加载到Activity
        dwmVp.setAdapter(dayweekmonthVPAdapter);

    }

    //    初始化时间
    //    初始化时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;   //在DAY_OF_WEEK获取到的值中，1-7分别表示周日一二三四五六，减1以符合中国思想
        if(dayOfWeek==0)dayOfWeek=7;                        //让他变成1-7对应周一二三四五六日
    }

    //    初始化控件
    private void initView() {
        dayBtn=findViewById(R.id.dayweekmonth_day);
        weekBtn=findViewById(R.id.dayweekmonth_week);
        monthBtn=findViewById(R.id.dayweekmonth_month);
        dwmVp=findViewById(R.id.dayweekmonth_vp);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dayweekmonth_iv_back:
                finish();
                break;
            case R.id.dayweekmonth_day:
                setButtonStyle(0);
                dwmVp.setCurrentItem(0);
                break;
            case R.id.dayweekmonth_week:
                setButtonStyle(1);
                dwmVp.setCurrentItem(1);
                break;
            case R.id.dayweekmonth_month:
                setButtonStyle(2);
                dwmVp.setCurrentItem(2);
                break;
        }
    }

    private void setButtonStyle(int i) {
        if (i==0){
            dayBtn.setBackgroundResource(R.drawable.main_editbtn_bg);
            dayBtn.setTextColor(Color.WHITE);
            weekBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            weekBtn.setTextColor(Color.BLACK);
            monthBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            monthBtn.setTextColor(Color.BLACK);
        }else if(i==1){
            weekBtn.setBackgroundResource(R.drawable.main_editbtn_bg);
            weekBtn.setTextColor(Color.WHITE);
            dayBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            dayBtn.setTextColor(Color.BLACK);
            monthBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            monthBtn.setTextColor(Color.BLACK);
        }else if(i==2){
            monthBtn.setBackgroundResource(R.drawable.main_editbtn_bg);
            monthBtn.setTextColor(Color.WHITE);
            dayBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            dayBtn.setTextColor(Color.BLACK);
            weekBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            weekBtn.setTextColor(Color.BLACK);
        }
    }
}
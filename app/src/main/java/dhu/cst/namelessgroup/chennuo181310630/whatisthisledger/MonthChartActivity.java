package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.ChartVPAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_chart.IncomeChartFragment;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_chart.OutcomeChartFragment;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.CalendarDialog;

public class MonthChartActivity extends AppCompatActivity {
    Button inBtn,outBtn;
    TextView dateTv,inTv,outTv;
    ViewPager chartVp;
    private int year;
    private int month;
    int selectPos=-1, selectMonth=-1;
    List<Fragment>chartFragList;
    private ChartVPAdapter adapter;
    private IncomeChartFragment incomeChartFragment;
    private OutcomeChartFragment outcomeChartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_chart);
        initView();
        initTime();
        initStatistics(year,month);
        initFrag();
        setVPSelectListener();
    }

    private void setVPSelectListener() {
        chartVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setButtonStyle(position);
            }
        });
    }

    private void initFrag() {
        chartFragList=new ArrayList<>();
//        添加Fragment对象，in和out两个
        incomeChartFragment = new IncomeChartFragment();
        outcomeChartFragment = new OutcomeChartFragment();
//        添加数据到Fragment中
        Bundle bundle = new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        incomeChartFragment.setArguments(bundle);
        outcomeChartFragment.setArguments(bundle);
//        将Fragment添加到数据源中
        chartFragList.add(outcomeChartFragment);
        chartFragList.add(incomeChartFragment);
//        使用适配器
        adapter = new ChartVPAdapter(getSupportFragmentManager(), chartFragList);
//        将Fragment加载到Activity中
        chartVp.setAdapter(adapter);
    }

    //    初始化收支情况数据
    private void initStatistics(int year, int month) {
        float inSumMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1);   //本月收入
        float outSumMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);  //本月支出
        int inItemCountOneMonth = DBManager.getItemCountOneMonth(year, month, 1);   //收入记录数量
        int outItemCountOneMonth = DBManager.getItemCountOneMonth(year, month, 0);  //支出记录数量
        dateTv.setText(year+"年"+month+"月账单");
        inTv.setText("共"+inItemCountOneMonth+"笔收入，￥"+inSumMoneyOneMonth);
        outTv.setText("共"+outItemCountOneMonth+"笔支出，￥"+outSumMoneyOneMonth);

    }

    //    初始化时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
    }


    //    初始化控件
    private void initView() {
        inBtn=findViewById(R.id.chart_btn_in);
        outBtn=findViewById(R.id.chart_btn_out);
        dateTv=findViewById(R.id.chart_tv_date);
        inTv=findViewById(R.id.chart_tv_in);
        outTv=findViewById(R.id.chart_tv_out);
        chartVp=findViewById(R.id.chart_vp);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chart_iv_back:
                finish();
                break;
            case R.id.chart_iv_rili:
                showCalendarDialog();
                break;
            case R.id.chart_btn_in:
                setButtonStyle(1);
                chartVp.setCurrentItem(1);
                break;
            case R.id.chart_btn_out:
                setButtonStyle(0);
                chartVp.setCurrentItem(0);
                break;

        }
    }

//    显示日历对话框
    private void showCalendarDialog() {
        CalendarDialog dialog = new CalendarDialog(this, selectPos, selectMonth);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
            @Override
            public void onRefresh(int selPos, int year, int month) {
                MonthChartActivity.this.selectPos=selPos;
                MonthChartActivity.this.selectMonth=month;
                initStatistics(year,month);
                incomeChartFragment.setDate(year,month);
                outcomeChartFragment.setDate(year,month);
            }
        });
    }

    //    按钮样式改变 收入==1，支出==0
    private void setButtonStyle(int kind){
        if (kind==0) {
            outBtn.setBackgroundResource(R.drawable.main_editbtn_bg);
            outBtn.setTextColor(Color.WHITE);
            inBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            inBtn.setTextColor(Color.BLACK);
        }else if(kind==1){
            inBtn.setBackgroundResource(R.drawable.main_editbtn_bg);
            inBtn.setTextColor(Color.WHITE);
            outBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            outBtn.setTextColor(Color.BLACK);
        }
    }
}
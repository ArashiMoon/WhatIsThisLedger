package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.ChartItemAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.ChartItemBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;

public abstract class BaseChartFragment extends Fragment {
    ListView chartLv;
    int year;
    int month;
    List<ChartItemBean> mDatas;
    private ChartItemAdapter itemAdapter;
//    头布局相关控件
    BarChart barChart;  //图表
    TextView chartTv;   //默认显示
    int last;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_income_chart, container, false);
        chartLv=view.findViewById(R.id.frag_chart_lv);
//        获取Activity传递的数据
        Bundle bundle=getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
//        设置数据源
        mDatas=new ArrayList<>();
//        设置适配器
        itemAdapter = new ChartItemAdapter(getContext(), mDatas);
        chartLv.setAdapter(itemAdapter);
//        添加头布局
        addLVHeaderView();
        return view;
    }
//    柱状图布局设置
    protected void addLVHeaderView(){
//        将布局转换成View对象
        View headerView=getLayoutInflater().inflate(R.layout.item_chartfrag_top,null);
//        将View添加到ListView的头布局上
        chartLv.addHeaderView(headerView);
//        查找头布局中包含的控件
        barChart=headerView.findViewById(R.id.item_chartfrag_chart);
        chartTv=headerView.findViewById(R.id.item_chartfrag_top_tv);
//        设置柱状图不显示描述信息
        barChart.getDescription().setEnabled(false);
//        设置柱状图内边距
        barChart.setExtraOffsets(20,20,20,20);
//        设置坐标轴
        setAxis(year,month);
//        设置数据
        setAxisData(year,month);
    }
//    在子类里具体实现
    protected abstract void setAxisData(int year, int month);
//    设置柱状图坐标轴，方法必须重写
    protected void setAxis(final int year, final int month) {
//        设置X轴
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //设置X轴显示在下方
        xAxis.setDrawGridLines(true);   //设置显示绘制该轴的网格线
//        设置X轴标签的个数
        last=0;
        if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
            if (month == 2) {
                last=29;
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                last=31;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                last=30;
            }
        } else {
            if (month == 2) {
                last=28;
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                last=31;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                last=30;
            }
        }
        xAxis.setLabelCount(last);
        xAxis.setTextSize(12f);     //x轴标签大小
//        设置X轴显示值的格式
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int val = (int) value;
                if (val == 0) {
                    return month + "-1";
                }
                if (val == 14) {
                    return month + "-15";
                }
                if(val==last-1){
                    return month+"-"+last;
                }
                return "";
            }
        });
        xAxis.setYOffset(10);//设置标签对X轴的偏移量
//        y轴在子类的设置
        setYAxis(year,month);

    }

//    设置y轴，因为最高的坐标不确定，因此在子类中设置
    protected abstract void setYAxis(int year,int month);


    public void loadData(int year,int month,int kind) {
        List<ChartItemBean> list = DBManager.getChartListFromAccountByMonth(year, month, kind);
        mDatas.clear();
        mDatas.addAll(list);
        itemAdapter.notifyDataSetChanged();
    }

    public void setDate(int year,int month){
        this.year=year;
        this.month=month;
//        清空图表数据
        barChart.clear();
        barChart.invalidate();
        setAxis(year,month);
        setAxisData(year, month);
    }

}

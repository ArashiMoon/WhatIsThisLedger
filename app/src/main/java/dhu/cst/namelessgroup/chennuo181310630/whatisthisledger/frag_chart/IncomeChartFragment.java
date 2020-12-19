package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_chart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.ChartItemAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.BarChartItemBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.ChartItemBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;

public class IncomeChartFragment extends BaseChartFragment {

    int kind=1;
    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,kind);
    }

    @Override
    protected void setAxisData(int year, int month) {
        List<IBarDataSet> sets=new ArrayList<>();//柱状图数据集合
//        获取这个月每天的支出总金额
        List<BarChartItemBean> list = DBManager.getSumMoneyOneDayInMonth(year, month, kind);
        if (list.size()==0) {
            barChart.setVisibility(View.GONE);
            chartTv.setVisibility(View.VISIBLE);
        }else{
            barChart.setVisibility(View.VISIBLE);
            chartTv.setVisibility(View.GONE);
//            每一个柱子的集合
            List<BarEntry>barEntries=new ArrayList<>();
//            以后改成最大值为月的日数！！！这里的i虽然固定了，以后要改！！！
            for (int i=0;i<last;i++){
                BarEntry entry = new BarEntry(i, 0.0f);
                barEntries.add(entry);
            }

            for (int i=0;i<list.size();i++){
                BarChartItemBean barChartItemBean = list.get(i);
                int day = barChartItemBean.getDay();
//                根据天数获取x轴的位置
                int xIndex=day-1;
                BarEntry barEntry = barEntries.get(xIndex);
                barEntry.setY(barChartItemBean.getSummoney());
            }
            BarDataSet barDataSet = new BarDataSet(barEntries, "");
            barDataSet.setValueTextColor(Color.BLACK); // 值的颜色
            barDataSet.setValueTextSize(15f); // 值的大小
            barDataSet.setColor(Color.parseColor("#006400")); // 柱子的颜色
            // 设置柱子上数据显示的格式
            barDataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    if (value==0) {
                        return "";
                    }
                    return value + "";
                }
            });
            sets.add(barDataSet);

            BarData barData = new BarData(sets);
            barData.setBarWidth(0.2f); // 设置柱子的宽度
            barChart.setData(barData);
        }

    }

    @Override
    protected void setYAxis(int year, int month) {
//        获取本月收入最高的一天的金额，将其设定为y轴最大值
        float maxMoneyOneDayInMonth = DBManager.getMaxMoneyOneDayInMonth(year, month, kind);
        float max=(float)Math.ceil(maxMoneyOneDayInMonth);
//        设置y轴
        // 设置y轴，y轴有两条，分别为左和右
        YAxis yAxis_right = barChart.getAxisRight();
        yAxis_right.setAxisMaximum(max);  // 设置y轴的最大值
        yAxis_right.setAxisMinimum(0f);  // 设置y轴的最小值
        yAxis_right.setEnabled(false);  // 不显示右边的y轴
        YAxis yAxis_left = barChart.getAxisLeft();
        yAxis_left.setAxisMaximum(max);
        yAxis_left.setAxisMinimum(0f);
        yAxis_left.setTextSize(15f); // 设置y轴的标签大小
        yAxis_left.setEnabled(false);
//        设置不显示图例
        Legend legend=barChart.getLegend();
        legend.setEnabled(false);

    }


    @Override
    public void setDate(int year, int month) {
        super.setDate(year, month);
        loadData(year,month,kind);
    }
}
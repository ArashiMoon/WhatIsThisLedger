package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_dayweekmonth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.ChartItemAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.ChartItemBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;

public class DayFragment extends BaseDWMFragment {
    public void loadData(int year,int month,int dayOfMonth,int dayOfWeek) {
        List<ChartItemBean>list1= DBManager.getChartListFromAccountByDay(year,month,dayOfMonth,0);
        List<ChartItemBean>list2= DBManager.getChartListFromAccountByDay(year,month,dayOfMonth,1);
        mDatas.clear();
        mDatas.addAll(list1);
        mDatas.addAll(list2);
        adapter.notifyDataSetChanged();
    }
}
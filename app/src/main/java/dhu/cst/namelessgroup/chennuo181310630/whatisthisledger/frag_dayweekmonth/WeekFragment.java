package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_dayweekmonth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.ChartItemBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;

public class WeekFragment extends BaseDWMFragment {

    public void loadData(int year,int month,int dayOfMonth,int dayOfWeek) {
        List<ChartItemBean> list1= DBManager.getChartListFromAccountByWeek(year,month,dayOfWeek,dayOfMonth,0);
        List<ChartItemBean> list2= DBManager.getChartListFromAccountByWeek(year,month,dayOfWeek,dayOfMonth,1);
        mDatas.clear();
        mDatas.addAll(list1);
        mDatas.addAll(list2);
        adapter.notifyDataSetChanged();
    }
}
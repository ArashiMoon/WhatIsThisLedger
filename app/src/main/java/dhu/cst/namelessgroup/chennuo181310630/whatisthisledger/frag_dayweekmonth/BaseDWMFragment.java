package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_dayweekmonth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.ChartItemAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.ChartItemBean;

abstract public class BaseDWMFragment extends Fragment {
    ListView pertLv;
    int year;
    int month;
    int dayOfMonth;
    int dayOfWeek;
    List<ChartItemBean> mDatas; //数据源
    ChartItemAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_day,container,false);
        pertLv=view.findViewById(R.id.frag_pert_lv);
//        获取Activity传递的数据
        Bundle bundle = getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        dayOfMonth = bundle.getInt("dayOfMonth");
        dayOfWeek = bundle.getInt("dayOfWeek");
//        设置数据源
        mDatas=new ArrayList<>();
//        设置适配器
        adapter = new ChartItemAdapter(getContext(), mDatas);  //复用
        pertLv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,dayOfMonth,dayOfWeek);
    }

    abstract void loadData(int year, int month, int dayOfMonth, int dayOfWeek) ;

}

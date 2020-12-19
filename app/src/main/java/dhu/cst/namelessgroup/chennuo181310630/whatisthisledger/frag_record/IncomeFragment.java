package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_record;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.TypeBean;

public class IncomeFragment extends BaseRecordFragment {
    //重写loadDataToGV实现子类不同效果
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.in_qita_ls);
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.in_qita_ls);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.insertItemToAccount(accountBean);
    }
}